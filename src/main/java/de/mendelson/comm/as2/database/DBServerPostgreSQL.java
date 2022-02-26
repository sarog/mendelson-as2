//$Header: /mec_as2/de/mendelson/comm/as2/database/DBServerPostgreSQL.java 2     2/02/22 13:06 Heller $
package de.mendelson.comm.as2.database;

import de.mendelson.util.database.IDBDriverManager;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Class to start a dedicated SQL database server
 *
 * @author S.Heller
 * @version $Revision: 2 $
 * @since build 70
 */
public class DBServerPostgreSQL implements IDBServer {

    public DBServerPostgreSQL(IDBDriverManager driverManager, DBServerInformation dbServerInformation) throws Exception {
    }

    /**
     * Returns the version of this class
     */
    public static String getVersion() {
        return ("0");
    }

    /**
     * Returns the product information of the database
     */
    @Override
    public DBServerInformation getDBServerInformation() {
        throw new IllegalAccessError();
    }

    @Override
    public void ensureServerIsRunning() throws Exception {
        throw new IllegalAccessError();
    }

    /**
     * Sends a shutdown signal to the DB
     */
    @Override
    public void shutdown() {
        throw new IllegalAccessError();
    }

}
