//$Header: /as2/de/mendelson/comm/as2/sendorder/SendOrderAccessDB.java 14    21.08.20 13:22 Heller $
package de.mendelson.comm.as2.sendorder;

import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Accesses the queue for the internal send orders
 *
 * @author S.Heller
 * @version $Revision: 14 $
 */
public class SendOrderAccessDB {

    /**
     * Connection to the database
     */
    private Connection runtimeConnection;
    private Connection configConnection;
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);

    /**
     * HSQLDB supports Java_Objects, PostgreSQL does not support it. Means there
     * are different access methods required for Object access
     */
    private boolean databaseSupportsJavaObjects = true;

    /**
     * Creates new message I/O log and connects to localhost
     *
     * @param host host to connect to
     */
    public SendOrderAccessDB(Connection configConnection, Connection runtimeConnection) {
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.analyzeDatabaseMetadata(configConnection);
    }

    private void analyzeDatabaseMetadata(Connection connection) {
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet result = null;
            try {
                result = data.getTypeInfo();
                while (result.next()) {
                    if (result.getString("TYPE_NAME").equalsIgnoreCase("bytea")) {
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
    private Object readObjectStoredAsJavaObject(ResultSet result, String columnName) throws Exception {
        if (this.databaseSupportsJavaObjects) {
            Object object = result.getObject(columnName);
            if (!result.wasNull()) {
                return (object);
            } else {
                return (null);
            }
        } else {
            byte[] bytes = result.getBytes(columnName);
            if (!result.wasNull()) {
                ObjectInputStream in = null;
                try {
                    in = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    Object object = in.readObject();
                    return (object);
                } finally {
                    in.close();
                }
            }
        }
        return (null);
    }

    /**
     * Sets text data as parameter to a stored procedure. The handling depends
     * if the database supports java objects
     *
     */
    private void setObjectParameterAsJavaObject(PreparedStatement statement, int index, Object obj) throws Exception {
        if (this.databaseSupportsJavaObjects) {
            if (obj == null) {
                statement.setNull(index, Types.JAVA_OBJECT);
            } else {
                statement.setObject(index, obj);
            }
        } else {
            if (obj == null) {
                statement.setNull(index, Types.BINARY);
            } else {
                ObjectOutputStream out = null;
                ByteArrayOutputStream memOut = new ByteArrayOutputStream();
                try {
                    out = new ObjectOutputStream(memOut);
                    out.writeObject(obj);
                } finally {
                    out.close();
                }
                statement.setBytes(index, memOut.toByteArray());
            }
        }
    }

    public void delete(int dbId) {
        if (dbId == -1) {
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement("DELETE FROM sendorder WHERE id=?");
            statement.setInt(1, dbId);
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("SendOrderAccessDB.delete: " + e.getMessage());
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
     * Reschedules an existing order
     */
    public void rescheduleOrder(SendOrder order, long nextExecutionTime) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE sendorder SET nextexecutiontime=?,sendorder=?,orderstate=? WHERE id=?");
            statement.setLong(1, nextExecutionTime);
            this.setObjectParameterAsJavaObject(statement, 2, order);
            statement.setInt(3, SendOrder.STATE_WAITING);
            //condition
            statement.setInt(4, order.getDbId());
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("SendOrderAccessDB.rescheduleOrder: " + e.getMessage());
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

    public void add(SendOrder order) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "INSERT INTO sendorder(scheduletime,nextexecutiontime,sendorder,orderstate)VALUES(?,?,?,?)");
            statement.setLong(1, System.currentTimeMillis());
            //execute as soon as possible
            statement.setLong(2, System.currentTimeMillis());
            this.setObjectParameterAsJavaObject(statement, 3, order);
            statement.setInt(4, SendOrder.STATE_WAITING);
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("SendOrderAccessDB.add: " + e.getMessage());
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
     * On a server start all the available transaction should be reset to the
     * wait state
     */
    public void resetAllToWaiting() {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE sendorder SET orderstate=?");
            statement.setInt(1, SendOrder.STATE_WAITING);
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("SendOrderAccessDB.resetAllToWait: " + e.getMessage());
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
     * Sets a new state to a send order
     */
    private void setState(int id, int orderState) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE sendorder SET orderstate=? WHERE id=?");
            statement.setInt(1, orderState);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("SendOrderAccessDB.setState: " + e.getMessage());
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
     * Returns the next n scheduled orders or an empty list if none exists
     */
    public List<SendOrder> getNext(int maxCount) {
        List<SendOrder> sendOrderList = new ArrayList<SendOrder>();
        PreparedStatement statement = null;
        ResultSet result = null;
        int count = 0;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "SELECT * FROM sendorder WHERE orderstate=? AND nextexecutiontime <=? ORDER BY nextexecutiontime");
            statement.setInt(1, SendOrder.STATE_WAITING);
            statement.setLong(2, System.currentTimeMillis());
            result = statement.executeQuery();
            while (result.next() && count < maxCount) {
                Object orderObject = null;
                try {
                    orderObject = this.readObjectStoredAsJavaObject(result, "sendorder");
                } catch (Throwable invalidClassExeption) {
                    //nop
                }
                SendOrder order = null;
                if (orderObject != null) {
                    if (orderObject instanceof SendOrder) {
                        //this happens if you read the serialized object from HSQLDB
                        order = (SendOrder) orderObject;
                        int id = result.getInt("id");
                        order.setDbId(id);
                        //do not pick it up until it is processed
                        this.setState(id, SendOrder.STATE_PROCESSING);
                        sendOrderList.add(order);
                        count++;
                    } else if (orderObject instanceof byte[]) {
                        //this happens if you read the serialized object from mySQL
                        ByteArrayInputStream memIn = new ByteArrayInputStream((byte[]) orderObject);
                        ObjectInput in = new ObjectInputStream(memIn);
                        SendOrder sendOrderObj = (SendOrder) in.readObject();
                        int id = result.getInt("id");
                        sendOrderObj.setDbId(id);
                        //do not pick it up until it is processed
                        this.setState(id, SendOrder.STATE_PROCESSING);
                        sendOrderList.add(sendOrderObj);
                        count++;
                    }
                } else {
                    //delete the entry from the database, its from an older version or an invalid entry
                    int id = result.getInt("id");
                    this.delete(id);
                    break;
                }
            }
        } catch (Exception e) {
            this.logger.severe("SendOrderAccessDB.getNext: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("SendOrderAccessDB.getNext: " + e.getMessage());
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
        return (sendOrderList);
    }
}
