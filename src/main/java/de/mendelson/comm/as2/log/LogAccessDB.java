//$Header: /as2/de/mendelson/comm/as2/log/LogAccessDB.java 32    21.08.20 13:22 Heller $
package de.mendelson.comm.as2.log;

import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Access to the AS2 log that stores log messages for every transaction
 *
 * @author S.Heller
 * @version $Revision: 32 $
 */
public class LogAccessDB {

    private int LEVEL_FINE = 3;
    private int LEVEL_SEVERE = 2;
    private int LEVEL_WARNING = 1;
    private int LEVEL_INFO = 0;
    /**
     * Logger to log information to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    /**
     * Connection to the database
     */
    private Connection runtimeConnection;
    private Connection configConnection;
    /**
     * Store the timestamps in the database in UTC to nake the database portable
     * and to prevent daylight saving problems
     */
    private Calendar calendarUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    /**HSQLDB supports Java_Objects, PostgreSQL does not support it. Means there are
     * different access methods required for Object access
     */
    private boolean databaseSupportsJavaObjects = true;

    /**
     * @param host host to connect to
     */
    public LogAccessDB(Connection configConnection, Connection runtimeConnection) {
        this.runtimeConnection = runtimeConnection;
        this.configConnection = configConnection;
        this.analyzeDatabaseMetadata(configConnection);
    }

    private void analyzeDatabaseMetadata(Connection connection) {
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet result = null;
            try {
                result = data.getTypeInfo();
                while (result.next()) {
                    if( result.getString( "TYPE_NAME").equalsIgnoreCase("bytea")){
                        databaseSupportsJavaObjects = false;
                    }
                }
            } finally {
                if (result != null) {
                    result.close();
                }
            }
        } catch (Exception e) {
            //ignore
        }
    }
    
    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    private String readTextStoredAsJavaObject(ResultSet result, String columnName) throws Exception {
        if( this.databaseSupportsJavaObjects){
            Object object = result.getObject(columnName);
            if (!result.wasNull()) {
                if (object instanceof String) {
                    return (((String) object));
                } else if (object instanceof byte[]) {
                    return (new String((byte[]) object));
                }
            }
        }else{
            byte[] bytes = result.getBytes(columnName);
            if (result.wasNull()) {
                return (null);
            }
            return (new String( bytes, StandardCharsets.UTF_8));
        }
        return (null);
    }

    /**Sets text data as parameter to a stored procedure. The handling depends if the database supports java objects
     * 
     */
    private void setTextParameterAsJavaObject(PreparedStatement statement, int index, String text) throws SQLException{        
        if( this.databaseSupportsJavaObjects ){
            if (text == null) {
                statement.setNull(index, Types.JAVA_OBJECT);
            } else {
                statement.setObject(index, text);
            }
        }else{
            if (text == null) {
                statement.setNull(index, Types.BINARY);
            } else {
                statement.setBytes(index, text.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
    
    private int convertLevel(Level level) {
        if (level.equals(Level.WARNING)) {
            return (this.LEVEL_WARNING);
        }
        if (level.equals(Level.SEVERE)) {
            return (this.LEVEL_SEVERE);
        }
        if (level.equals(Level.FINE)) {
            return (this.LEVEL_FINE);
        }
        return (this.LEVEL_INFO);
    }

    private Level convertLevel(int level) {
        if (level == this.LEVEL_WARNING) {
            return (Level.WARNING);
        }
        if (level == this.LEVEL_SEVERE) {
            return (Level.SEVERE);
        }
        if (level == this.LEVEL_FINE) {
            return (Level.FINE);
        }
        return (Level.INFO);
    }

    /**
     * Adds a log line to the db
     */
    public void log(Level level, long millis, String logMessage, String messageId) {
        if (logMessage == null) {
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "INSERT INTO messagelog(timestamputc,messageid,loglevel,details)VALUES(?,?,?,?)");
            statement.setTimestamp(1, new Timestamp(millis), this.calendarUTC);
            statement.setString(2, messageId);
            statement.setInt(3, this.convertLevel(level));
            this.setTextParameterAsJavaObject(statement, 4, logMessage);
            statement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            String errorMessage = "LogAccessDB.log "
                    + "(" + e.getClass().getSimpleName() + "): "
                    + " The system tries to store a log entry for the message id \"" + messageId
                    + "\", but this message seems not to exist in the system.\n"
                    + "The reason might be an unreferenced MDN or a bad inbound AS2 message structure.";
            this.logger.severe(errorMessage);
            SystemEvent event = new SystemEvent( SystemEvent.SEVERITY_ERROR, SystemEvent.ORIGIN_TRANSACTION, SystemEvent.TYPE_TRANSACTION_ANY );
            event.setBody(errorMessage + "\n\nLog message: \"" + logMessage + "\"");
            event.setSubject("Unreferenced MDN or bad message structure");
            SystemEventManagerImplAS2.newEvent(event);
        } catch (Exception e) {
            this.logger.severe("LogAccessDB.log: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
    }

    /**
     * Returns the whole log of a single instance
     */
    public List<LogEntry> getLog(String messageId) {
        List<LogEntry> list = new ArrayList<LogEntry>();
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement("SELECT * FROM messagelog WHERE messageid=? ORDER BY timestamputc");
            statement.setString(1, messageId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                LogEntry entry = new LogEntry();
                entry.setLevel(this.convertLevel(result.getInt("loglevel")));
                String detailsStr = this.readTextStoredAsJavaObject(result, "details");
                if( detailsStr != null ){
                    entry.setMessage(detailsStr);
                }
                entry.setMessageId(messageId);
                entry.setMillis(result.getTimestamp("timestamputc", this.calendarUTC).getTime());
                list.add(entry);
            }
        } catch (Exception e) {
            this.logger.severe("LogAccessDB.getLog: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
        return (list);
    }

    /**
     * Deletes all information from the table messagelog regarding the passed
     * message instance
     */
    public void deleteMessageLog(String messageId) {
        PreparedStatement statement = null;
        try {
            if (messageId != null) {
                statement = this.runtimeConnection.prepareStatement("DELETE FROM messagelog WHERE messageid=?");
                statement.setString(1, messageId);
            } else {
                statement = this.runtimeConnection.prepareStatement("DELETE FROM messagelog WHERE messageid IS NULL");
            }
            statement.execute();
        } catch (Exception e) {
            this.logger.severe("deleteMessageLog: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
    }
}
