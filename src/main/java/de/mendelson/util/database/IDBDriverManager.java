//$Header: /mendelson_business_integration/de/mendelson/util/database/IDBDriverManager.java 8     7.12.21 11:06 Heller $
package de.mendelson.util.database;

import java.sql.*;

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
 * @version $Revision: 8 $
 */
public interface IDBDriverManager {

    int DB_CONFIG     = 1;
    int DB_RUNTIME    = 2;
    int DB_DEPRECATED = 3;

    /**
     * Setup the driver manager, initialize the connection pool
     */
    void setupConnectionPool();

    /**
     * shutdown the connection pool
     */
    void shutdownConnectionPool() throws SQLException;

    /**
     * Creates a new locale database
     *
     * @param DB_TYPE of the database that should be created, as defined in this
     *                class
     * @return true if it was created successfully
     */
    boolean createDatabase(final int DB_TYPE) throws Exception;

    /**
     * Returns a connection to the database
     *
     * @param DB_TYPE of the database that should be created, as defined in this
     *                class
     */
    Connection getConnectionWithoutErrorHandling(final int DB_TYPE) throws SQLException;

    /**
     * Returns the SQL statement that is used to lock a table on database level
     * exclusive for a transaction
     *
     * @param tablenames The name of the tables to lock exclusive for all access
     *                  (read/write) operations
     * @return
     */
    void setTableLockExclusive(Statement statement, String[] tablenames) throws SQLException;

    /**
     * Returns the SQL statement that is used to lock a table on database level
     * for an INSERT or UPDATE operation. The lock level should be that high that
     * no other session could perform an update or insert operation to the same table(s)
     * meanwhile
     *
     * @param tablenames The name of the tables to lock exclusive for all access
     *                  (read/write) operations
     * @return
     */
    void setTableLockINSERTAndUPDATE(Statement statement, String[] tablenames) throws SQLException;

    /**
     * Returns the SQL statement that is used to lock a table on database level
     * for a DELETE operation
     *
     * @param tablenames The name of the tables to lock exclusive for all access
     *                  (read/write) operations
     * @return
     */
    void setTableLockDELETE(Statement statement, String[] tablenames) throws SQLException;

    /**
     * Starts a transaction. Implementations might do nothing as this concept is
     * database specific. HSQLB has no "BEGIN transaction" concept
     *
     * @param statement
     * @param transactionName
     */
    void startTransaction(Statement statement, String transactionName) throws SQLException;

    /**
     * Commits a transaction
     *
     * @param transactionName
     */
    void commitTransaction(Statement statement, String transactionName) throws SQLException;

    /**
     * Rollback a transaction
     */
    void rollbackTransaction(Statement statement) throws SQLException;

    /**
     * Sets text data as parameter to a stored procedure. The handling depends
     * if the database supports java objects. PostgreSQL for example could not
     * deal with the JDBC type JAVA_OBJECT
     */
    void setTextParameterAsJavaObject(PreparedStatement statement, int index, String text) throws SQLException;

    /**
     * Reads a binary object from the database and returns a String that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system.
     * PostgreSQL for example could not deal with the JDBC type JAVA_OBJECT
     */
    String readTextStoredAsJavaObject(ResultSet result, String columnName) throws Exception;

    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    Object readObjectStoredAsJavaObject(ResultSet result, String columnName) throws Exception;

    /**
     * Sets text data as parameter to a stored procedure. The handling depends
     * if the database supports java objects
     */
    void setObjectParameterAsJavaObject(PreparedStatement statement, int index, Object obj) throws Exception;

    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    byte[] readBytesStoredAsJavaObject(ResultSet result, String columnName) throws Exception;

    /**
     * Returns some connection pool information for debug purpose
     */
    String getPoolInformation(int DB_TYPE);
}
