//$Header: /as2/de/mendelson/comm/as2/database/DBDriverManagerHSQL.java 4     21.08.20 17:59 Heller $
package de.mendelson.comm.as2.database;

import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.DebuggableConnection;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Class needed to access the database
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class DBDriverManagerHSQL implements IDBDriverManager{

    private static BasicDataSource dataSourceConfig;
    private static BasicDataSource dataSourceRuntime;    
    public static final boolean DEBUG = false;
    public static final PreferencesAS2 preferences = new PreferencesAS2();
    private final static String DB_USER_NAME = "sa";
    public final static String DB_PASSWORD = "as2dbadmin";
    /**
     * Resourcebundle to localize messages of the DB server
     */
    private static MecResourceBundle rb;

    static {
        //register db driver
        try{
            Class.forName("org.hsqldb.jdbcDriver");
        }
        catch( Throwable e){
            throw new RuntimeException("Unable to register database driver for HSQL database - [" 
                    + e.getClass().getSimpleName() + "] " + e.getMessage());
        }
                
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleDBDriverManager.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Setup the driver manager, initialize the connection pool
     *
     */
    @Override
    public synchronized void setupConnectionPool() {
        String driverName = "org.hsqldb.jdbcDriver";
        String dbHost = "localhost";
        //in client-server environment this may be called from client and server in the same VM
        if (dataSourceConfig == null) {
            dataSourceConfig = new BasicDataSource();
            dataSourceConfig.setDriverClassName(driverName);
            dataSourceConfig.setUrl(getConnectionURI(dbHost, DB_CONFIG));
            dataSourceConfig.setUsername(DB_USER_NAME);
            dataSourceConfig.setPassword(DB_PASSWORD);
            dataSourceConfig.setDefaultAutoCommit(true);
            dataSourceConfig.setDefaultReadOnly(false);
            dataSourceConfig.setPoolPreparedStatements(false);
        }
        //in client-server environment this may be called from client and server in the same VM
        if (dataSourceRuntime == null) {
            dataSourceRuntime = new BasicDataSource();
            dataSourceRuntime.setDriverClassName(driverName);
            dataSourceRuntime.setUrl(getConnectionURI(dbHost, DB_RUNTIME));
            dataSourceRuntime.setUsername(DB_USER_NAME);
            dataSourceRuntime.setPassword(DB_PASSWORD);
            dataSourceRuntime.setDefaultAutoCommit(true);
            dataSourceRuntime.setDefaultReadOnly(false);
            dataSourceRuntime.setPoolPreparedStatements(false);
        }
    }

    /**
     * shutdown the connection pool
     */
    @Override
    public void shutdownConnectionPool() throws SQLException {
        if (dataSourceConfig != null) {
            BasicDataSource bdsConfig = (BasicDataSource) dataSourceConfig;
            bdsConfig.close();
        }
        if (dataSourceRuntime != null) {
            BasicDataSource bdsRuntime = (BasicDataSource) dataSourceRuntime;
            bdsRuntime.close();
        }
    }

    /**
     * Returns the URI to connect to
     */
    private String getConnectionURI(String host, final int DB_TYPE) {
        int port = DBDriverManagerHSQL.preferences.getInt(PreferencesAS2.SERVER_DB_PORT);
        return ("jdbc:hsqldb:hsql://" + host + ":" + port + "/" + this.getDBAlias(DB_TYPE));
    }

    /**
     * Returns the DB name, depending on the system wide profile name
     */
    public String getDBName(final int DB_TYPE) {
        String name = "AS2_DB";
        if (DB_TYPE == IDBDriverManager.DB_CONFIG) {
            name = name + "_CONFIG";
        } else if (DB_TYPE == IDBDriverManager.DB_RUNTIME) {
            name = name + "_RUNTIME";
        } else if (DB_TYPE != IDBDriverManager.DB_DEPRICATED) {
            throw new RuntimeException("Unknown DB type requested in DBDriverManager.");
        }
        return (name);
    }

    /**
     * Returns the DB name for a special profile
     */
    protected String getDBAlias(final int DB_TYPE) {
        String alias = "";
        if (DB_TYPE == DBDriverManagerHSQL.DB_CONFIG) {
            alias = alias + "config";
        } else if (DB_TYPE == DBDriverManagerHSQL.DB_RUNTIME) {
            alias = alias + "runtime";
        } else if (DB_TYPE != DB_DEPRICATED) {
            throw new RuntimeException("Unknown DB type requested in DBDriverManager.");
        }
        return (alias);
    }

    /**
     * Creates a new locale database
     *
     * @return true if it was created successfully
     * @param DB_TYPE of the database that should be created, as defined in this
     * class
     */
    @Override
    public boolean createDatabase(final int DB_TYPE) throws Exception {
        try {
            // It will be create automatically if it does not yet exist
            // the given files in the URL is the name of the database
            // "sa" is the user name and "" is the (empty) password            
            String createResource = null;
            int dbVersion = 0;
            if (DB_TYPE == DBDriverManagerHSQL.DB_CONFIG) {
                dbVersion = AS2ServerVersion.getRequiredDBVersionConfig();
                createResource = SQLScriptExecutor.SCRIPT_RESOURCE_CONFIG;
            } else if (DB_TYPE == DBDriverManagerHSQL.DB_RUNTIME) {
                dbVersion = AS2ServerVersion.getRequiredDBVersionRuntime();
                createResource = SQLScriptExecutor.SCRIPT_RESOURCE_RUNTIME;
            } else if (DB_TYPE != DBDriverManagerHSQL.DB_DEPRICATED) {
                throw new RuntimeException("Unknown DB type requested in DBDriverManager.");
            }
            Logger.getLogger(AS2Server.SERVER_LOGGER_NAME).info(rb.getResourceString("creating.database." + DB_TYPE));
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:hsqldb:" + this.getDBName(DB_TYPE),
                        "sa", "");
                Statement statement = null;
                try {
                    statement = connection.createStatement();
                    statement.execute("ALTER USER " + DB_USER_NAME.toUpperCase() + " SET PASSWORD '" + DB_PASSWORD + "'");
                } finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
                SQLScriptExecutor executor = new SQLScriptExecutor();
                executor.create(connection, createResource, dbVersion);
            } catch (Exception e) {
                throw new Exception(rb.getResourceString("database.creation.failed." + DB_TYPE)
                        + " [" + e.getMessage() + "]");
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
            Logger.getLogger(AS2Server.SERVER_LOGGER_NAME).info(rb.getResourceString("database.creation.success." + DB_TYPE));
            SystemEvent event = new SystemEvent(
                    SystemEvent.SEVERITY_INFO,
                    SystemEvent.ORIGIN_SYSTEM,
                    SystemEvent.TYPE_DATABASE_CREATION);
            event.setSubject(rb.getResourceString("database.creation.success." + DB_TYPE));
            SystemEventManagerImplAS2.newEvent(event);
        } catch (Throwable e) {
            Logger.getLogger(AS2Server.SERVER_LOGGER_NAME).severe(rb.getResourceString("database.creation.failed." + DB_TYPE)
                    + " [" + e.getMessage() + "]");
            SystemEvent event = new SystemEvent(
                    SystemEvent.SEVERITY_ERROR,
                    SystemEvent.ORIGIN_SYSTEM,
                    SystemEvent.TYPE_DATABASE_CREATION);
            event.setSubject(
                    rb.getResourceString("database.creation.failed." + DB_TYPE));
            String message = e.getMessage();
            if (message == null) {
                message = "[" + e.getClass().getSimpleName() + "]";
            }
            event.setBody(message);
            SystemEventManagerImplAS2.newEvent(event);
            throw e;
        }
        return (true);
    }

    /**
     * Connects to a local database called "MecDriverManager.DB_NAME"
     */
    protected Connection getLocalConnection(final int DB_TYPE) {
        return (getConnection(DB_TYPE, "127.0.0.1"));
    }

    /**
     * Returns a connection to the database
     */
    protected Connection getConnection(final int DB_TYPE, String hostName) {
        try {
            return (getConnectionWithoutErrorHandling(DB_TYPE, hostName));
        } catch (SQLException e) {
            Logger.getLogger(AS2Server.SERVER_LOGGER_NAME).severe(e.getMessage());
        }
        return (null);
    }

    /**
     * Returns a connection to the database
     *
     * @param hostName Name of the database server to connect to. Use
     * "localhost" to connect to your local host
     * @param DB_TYPE of the database that should be created, as defined in this
     * class
     */
    @Override
    public synchronized Connection getConnectionWithoutErrorHandling(final int DB_TYPE, String host)
            throws SQLException {
        Connection connection = null;
        if (DB_TYPE == DB_RUNTIME) {
            //no pooling
            if (dataSourceRuntime == null) {
                connection = DriverManager.getConnection(
                        getConnectionURI(host, DB_TYPE),
                        DB_USER_NAME, DB_PASSWORD);
            } else {
                int maxConnections = dataSourceRuntime.getMaxTotal();
                if (maxConnections < dataSourceRuntime.getNumActive() + 1) {
                    dataSourceRuntime.setMaxTotal(maxConnections + 1);
                }
                connection = dataSourceRuntime.getConnection();
            }
        } else if (DB_TYPE == DB_CONFIG) {
            //no pooling
            if (dataSourceConfig == null) {
                connection = DriverManager.getConnection(
                        getConnectionURI(host, DB_TYPE),
                        DB_USER_NAME, DB_PASSWORD);
            } else {
                int maxConnections = dataSourceConfig.getMaxTotal();
                if (maxConnections < dataSourceConfig.getNumActive() + 1) {
                    dataSourceConfig.setMaxTotal(maxConnections + 1);
                }
                connection = dataSourceConfig.getConnection();
            }
        } else if (DB_TYPE == DB_DEPRICATED) {
            //deprecated connection: no pooling
            connection = DriverManager.getConnection(
                    getConnectionURI(host, DB_TYPE),
                    DB_USER_NAME, DB_PASSWORD);
        } else {
            throw new RuntimeException("Requested invalid db type in getConnectionWithoutErrorHandling");
        }
        connection.setReadOnly(false);
        connection.setAutoCommit(true);
        return (new DebuggableConnection(connection));
    }
    
    /**Just used for data migration from the HSQLDB config database to an other one*/
    public Connection getConnectionFileBased(final int DB_TYPE) throws Exception{
        String configDBName = this.getDBName(DB_TYPE);
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + configDBName, 
                    DB_USER_NAME, DB_PASSWORD);
        return( connection );
    }
    
}
