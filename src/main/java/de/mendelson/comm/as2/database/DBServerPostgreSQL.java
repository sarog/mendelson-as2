//$Header: /mec_as2/de/mendelson/comm/as2/database/DBServerPostgreSQL.java 3     17.12.20 14:32 Heller $
package de.mendelson.comm.as2.database;

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
 * @version $Revision: 3 $
 * @since build 70
 */
public class DBServerPostgreSQL implements IDBServer {

    public DBServerPostgreSQL(Object obj1, Object obj2) throws Exception {
    }

    @Override
    public DBServerInformation getDBServerInformation() {
        throw new UnsupportedOperationException("Not supported in community edition.");
    }

    @Override
    public void ensureServerIsRunning() throws Exception {
        throw new UnsupportedOperationException("Not supported in community edition.");
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("Not supported in community edition.");
    }

}
