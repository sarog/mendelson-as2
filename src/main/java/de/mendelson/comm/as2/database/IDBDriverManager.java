//$Header: /as2/de/mendelson/comm/as2/database/IDBDriverManager.java 1     20.08.20 14:04 Heller $
package de.mendelson.comm.as2.database;

import java.sql.Connection;
import java.sql.SQLException;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Interface for all supported database drivers
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public interface IDBDriverManager {

    public static int DB_DEPRICATED = 1;
    public static int DB_CONFIG = 2;
    public static int DB_RUNTIME = 3;
    
    /**
     * Setup the driver manager, initialize the connection pool
     *
     */
    public void setupConnectionPool();

    /**
     * shutdown the connection pool
     */
    public void shutdownConnectionPool() throws SQLException;

    /**
     * Creates a new locale database
     *
     * @return true if it was created successfully
     * @param DB_TYPE of the database that should be created, as defined in this
     * class
     */
    public boolean createDatabase(final int DB_TYPE) throws Exception;

    /**
     * Returns a connection to the database
     *
     * @param hostName Name of the database server to connect to. Use
     * "localhost" to connect to your local host
     * @param DB_TYPE of the database that should be created, as defined in this
     * class
     */
    public Connection getConnectionWithoutErrorHandling(final int DB_TYPE, String host)
            throws SQLException;
}
