//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ProcessingEventAccessDB.java 4     4.09.20 13:16 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.security.Base64;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.hsqldb.types.Types;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Access the event queue for the partner related event processing (post processing)
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ProcessingEventAccessDB {

    /**
     * Logger to log information to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    /**
     * Connection to the database
     */
    private Connection runtimeConnection = null;
    private Connection configConnection = null;

    /**
     * Creates new message I/O log and connects to localhost
     *
     * @param host host to connect to
     */
    public ProcessingEventAccessDB(Connection configConnection, Connection runtimeConnection) {
        this.runtimeConnection = runtimeConnection;
        this.configConnection = configConnection;
    }

    /**
     * Returns the next event that should be executed in the processing queue of
     * NULL if none exists
     * If an event is returned it is deleted in the queue
     */
    public ProcessingEvent getNextEventToExecute() {
        ResultSet result = null;
        PreparedStatement statementSelect = null;
        PreparedStatement statementDelete = null;
        PreparedStatement debugStatement = null;
        ProcessingEvent event = null;
        try {
            statementSelect = this.runtimeConnection.prepareStatement("SELECT * FROM processingeventqueue WHERE initdate < ? ORDER BY initdate ASC");
            statementSelect.setLong(1, System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(15));
            debugStatement = statementSelect;
            result = statementSelect.executeQuery();
            if (result.next()) {
                int eventType = result.getInt("eventtype");                
                int processType = result.getInt("processtype");    
                long initDate = result.getLong("initdate");
                List<String> parameter = this.deserializeList(result.getString("parameterlist"));
                String relatedMessageId = result.getString("messageid");
                String relatedMDNId = result.getString("mdnid");
                if( result.wasNull()){
                    relatedMDNId = null;
                }
                event = new ProcessingEvent(eventType, processType, relatedMessageId, relatedMDNId, parameter, initDate);
                statementDelete = this.runtimeConnection.prepareStatement("DELETE FROM processingeventqueue WHERE messageid=?");
                debugStatement = statementDelete;
                statementDelete.setString(1, relatedMessageId);
                statementDelete.executeUpdate();
            }
            return (event);
        } catch (Exception e) {
            this.logger.severe("ProcessingEventAccessDB.getNextEventToExecute: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, debugStatement);
            return (null);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
            if (statementSelect != null) {
                try {
                    statementSelect.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statementSelect);
                }
            }
            if (statementDelete != null) {
                try {
                    statementDelete.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statementDelete);
                }
            }
        }
    }

    /**
     * Adds an Event to the database
     */
    public void addEventToExecute(ProcessingEvent event) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "INSERT INTO processingeventqueue("
                    + "eventtype,processtype,initdate,parameterlist,messageid,mdnid)"
                    + "VALUES(?,?,?,?,?,?)");
            statement.setInt(1, event.getEventType());
            statement.setInt(2, event.getProcessType());
            statement.setLong(3, event.getInitDate());
            statement.setString(4, this.serializeList(event.getParameter()));
            statement.setString(5, event.getMessageId());
            if( event.getMDNId() == null){
                statement.setNull(6, Types.VARCHAR);
            }else{
                statement.setString(6, event.getMDNId());
            }
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("ProcessingEventAccessDB.addEventToExecute: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("ProcessingEventAccessDB.addEventToExecute: " + e.getMessage());
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
                }
            }
        }
    }

    /**Serializes a list to a single string
     * 
     * @param list
     * @return 
     */
    private String serializeList( List<String> list){
        StringBuilder builder = new StringBuilder();
        for( String entry:list){
            if( builder.length() > 0 ){
                builder.append( " ");
            }
            builder.append(Base64.encode(entry.getBytes(StandardCharsets.UTF_8)));
        }
        return( builder.toString());
    }
    
    /**Serializes a list to a single string
     * 
     * @param list
     * @return 
     */
    private List<String> deserializeList( String entry){
        List<String> list = new ArrayList<String>();
        if( entry == null ){
            return( list );
        }
        String[] entryArray = entry.split(" ");
        for( String singleEntry:entryArray){
            byte[] decodedBytes = Base64.decode(singleEntry);
            //base64 decoding failed...return empty parameters
            if( decodedBytes == null ){
                return( new ArrayList<String>());
            }
            list.add( new String(decodedBytes, StandardCharsets.UTF_8));
        }
        return( list );
    }
    
}
