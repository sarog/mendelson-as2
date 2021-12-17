//$Header: /mec_as2/de/mendelson/comm/as2/database/DBDriverManagerPostgreSQL.java 2     17.12.20 9:52 Heller $
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
 * Class needed to access the database
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class DBDriverManagerPostgreSQL implements IDBDriverManager, ISQLQueryModifier {

    public static final boolean DEBUG = false;

    @Override
    public void setupConnectionPool() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void shutdownConnectionPool() throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean createDatabase(int DB_TYPE) throws Exception {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Connection getConnectionWithoutErrorHandling(int DB_TYPE, String host) throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String modifyQuery(String query) {
        throw new UnsupportedOperationException("Not supported.");
    }

}
