//$Header: /as2/de/mendelson/comm/as2/partner/PartnerAccessDB.java 70    14.12.20 13:30 Heller $
package de.mendelson.comm.as2.partner;

import de.mendelson.comm.as2.cert.CertificateAccessDB;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
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
 * Implementation of a server log for the mendelson as2 server database
 *
 * @author S.Heller
 * @version $Revision: 70 $
 */
public class PartnerAccessDB {

    /**
     * Logger to log information to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    /**
     * Connection to the database
     */
    private Connection configConnection;
    private Connection runtimeConnection;
    /**
     * Access the certificates
     */
    private CertificateAccessDB certificateAccess;
    private PartnerEventAccessDB eventAccess;
    /**
     * HSQLDB supports Java_Objects, PostgreSQL does not support it. Means there
     * are different access methods required for Object access
     */
    private boolean databaseSupportsJavaObjects = true;

    /**
     * Returns the full partner data
     */
    public static final int DATA_COMPLETENESS_FULL = 100;
    /**
     * Return incomplete partner requests - for faster UI client-server requests
     */
    public static final int DATA_COMPLETENESS_NAMES_AS2ID_TYPE = 101;

    /**
     * Creates new message I/O log and connects to localhost
     *
     * @param host host to connect to
     */
    public PartnerAccessDB(Connection configConnection, Connection runtimeConnection) {
        this.configConnection = configConnection;
        this.analyzeDatabaseMetadata(configConnection);
        this.certificateAccess = new CertificateAccessDB(configConnection, runtimeConnection);
        this.eventAccess = new PartnerEventAccessDB(configConnection, runtimeConnection);
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
    private String readTextStoredAsJavaObject(ResultSet result, String columnName) throws Exception {
        if (this.databaseSupportsJavaObjects) {
            Object object = result.getObject(columnName);
            if (!result.wasNull()) {
                if (object instanceof String) {
                    return (((String) object));
                } else if (object instanceof byte[]) {
                    return (new String((byte[]) object));
                }
            }
        } else {
            byte[] bytes = result.getBytes(columnName);
            if (result.wasNull()) {
                return (null);
            }
            return (new String(bytes, StandardCharsets.UTF_8));
        }
        return (null);
    }

    /**
     * Sets text data as parameter to a stored procedure. The handling depends
     * if the database supports java objects
     *
     */
    private void setTextParameterAsJavaObject(PreparedStatement statement, int index, String text) throws SQLException {
        if (this.databaseSupportsJavaObjects) {
            if (text == null) {
                statement.setNull(index, Types.JAVA_OBJECT);
            } else {
                statement.setObject(index, text);
            }
        } else {
            if (text == null) {
                statement.setNull(index, Types.BINARY);
            } else {
                statement.setBytes(index, text.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * Requires a query to select partners from the DB
     * @param dataCompleteness Allows to get partner object with lesser information
     */
    private List<Partner> getPartnerByQuery(String query, int dataCompleteness) {
        List<Partner> partnerList = new ArrayList<Partner>();
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = this.configConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                Partner partner = new Partner();
                partner.setAS2Identification(result.getString("as2ident"));
                partner.setName(result.getString("partnername"));
                partner.setDBId(result.getInt("id"));
                partner.setLocalStation(result.getInt("islocal") == 1);
                //All partner data is requested - deliver it
                if (dataCompleteness == DATA_COMPLETENESS_FULL) {
                    partner.setSignType(result.getInt("sign"));
                    partner.setEncryptionType(result.getInt("encrypt"));
                    partner.setEmail(result.getString("email"));
                    partner.setURL(result.getString("url"));
                    partner.setMdnURL(result.getString("mdnurl"));
                    partner.setSubject(result.getString("msgsubject"));
                    partner.setContentType(result.getString("contenttype"));
                    partner.setSyncMDN(result.getInt("syncmdn") == 1);
                    partner.setPollIgnoreListString(result.getString("pollignorelist"));
                    partner.setPollInterval(result.getInt("pollinterval"));
                    partner.setCompressionType(result.getInt("msgcompression"));
                    partner.setSignedMDN(result.getInt("signedmdn") == 1);
                    partner.setKeepOriginalFilenameOnReceipt(result.getInt("keeporiginalfilenameonreceipt") == 1);
                    HTTPAuthentication authentication = partner.getAuthentication();
                    authentication.setUser(result.getString("httpauthuser"));
                    authentication.setPassword(result.getString("httpauthpass"));
                    authentication.setEnabled(result.getInt("usehttpauth") == 1);
                    HTTPAuthentication asyncAuthentication = partner.getAuthenticationAsyncMDN();
                    asyncAuthentication.setUser(result.getString("httpauthuserasnymdn"));
                    asyncAuthentication.setPassword(result.getString("httpauthpassasnymdn"));
                    asyncAuthentication.setEnabled(result.getInt("usehttpauthasyncmdn") == 1);
                    partner.setComment(this.readTextStoredAsJavaObject(result, "partnercomment"));
                    partner.setContactAS2(this.readTextStoredAsJavaObject(result, "partnercontact"));
                    partner.setContactCompany(this.readTextStoredAsJavaObject(result, "partneraddress"));
                    partner.setNotifyReceive(result.getInt("notifyreceive"));
                    partner.setNotifySend(result.getInt("notifysend"));
                    partner.setNotifySendReceive(result.getInt("notifysendreceive"));
                    partner.setNotifyReceiveEnabled(result.getInt("notifyreceiveenabled") == 1);
                    partner.setNotifySendEnabled(result.getInt("notifysendenabled") == 1);
                    partner.setNotifySendReceiveEnabled(result.getInt("notifysendreceiveenabled") == 1);
                    partner.setContentTransferEncoding(result.getInt("contenttransferencoding"));
                    partner.setHttpProtocolVersion(result.getString("httpversion"));
                    partner.setMaxPollFiles(result.getInt("maxpollfiles"));
                    partner.setUseAlgorithmIdentifierProtectionAttribute(result.getInt("algidentprotatt") == 1);
                    partner.setEnableDirPoll(result.getInt("enabledirpoll") == 1);
                    //ensure to have a valid partner DB id before loading the releated data
                    this.certificateAccess.loadPartnerCertificateInformation(partner);
                    this.loadHttpHeader(partner);
                    this.eventAccess.loadPartnerEvents(partner);
                }
                partnerList.add(partner);
            }
            Collections.sort(partnerList);
            return (partnerList);
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.severe("PartnerAccessDB.getPartnerByQuery: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
            return (null);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
    }

    /**
     * Returns all partner stored in the DB, even the local station
     */
    public List<Partner> getAllPartner(int dataCompleteness) {
        return (this.getPartnerByQuery("SELECT * FROM partner", dataCompleteness));
    }
    
    /**
     * Returns all partner stored in the DB with all information, even the local station
     */
    public List<Partner> getPartner() {
        return (this.getAllPartner(DATA_COMPLETENESS_FULL));
    }

    /**
     * Returns all local stations stored in the DB
     */
    public List<Partner> getLocalStations(int dataCompleteness) {
        return (this.getPartnerByQuery("SELECT * FROM partner WHERE islocal=1", dataCompleteness));
    }

    /**
     * Returns all local stations stored in the DB
     */
    public List<Partner> getLocalStations() {
        return (this.getLocalStations(DATA_COMPLETENESS_FULL));
    }
    
    /**
     * Returns all partner stored in the DB, even the local station
     */
    public List<Partner> getNonLocalStations(int dataCompleteness) {
        return (this.getPartnerByQuery("SELECT * FROM partner WHERE islocal<>1", dataCompleteness));
    }

    /**
     * Returns all partner stored in the DB, even the local station
     */
    public List<Partner> getNonLocalStations() {
        return (this.getNonLocalStations(DATA_COMPLETENESS_FULL));
    }
    
    /**
     * Updates a single partner in the db
     */
    /**
     * Inserts a new partner into the database
     */
    public void updatePartner(Partner partner) {
        PreparedStatement statement = null;
        try {
            statement = this.configConnection.prepareStatement(
                    "UPDATE partner SET "
                    + "as2ident=?,partnername=?,islocal=?,sign=?,encrypt=?,email=?,url=?,"
                    + "mdnurl=?,msgsubject=?,contenttype=?,syncmdn=?,pollignorelist=?,"
                    + "pollinterval=?,msgcompression=?,signedmdn=?,"
                    + "usehttpauth=?,httpauthuser=?,httpauthpass=?,"
                    + "usehttpauthasyncmdn=?,httpauthuserasnymdn=?,httpauthpassasnymdn=?,"
                    + "keeporiginalfilenameonreceipt=?,partnercomment=?,notifysend=?,"
                    + "notifyreceive=?,notifysendreceive=?,notifysendenabled=?,"
                    + "notifyreceiveenabled=?,notifysendreceiveenabled=?,"
                    + "contenttransferencoding=?,httpversion=?,"
                    + "maxpollfiles=?,partnercontact=?,partneraddress=?,algidentprotatt=?,"
                    + "enabledirpoll=?"
                    + "WHERE id=?");
            statement.setString(1, partner.getAS2Identification());
            statement.setString(2, partner.getName());
            statement.setInt(3, partner.isLocalStation() ? 1 : 0);
            statement.setInt(4, partner.getSignType());
            statement.setInt(5, partner.getEncryptionType());
            statement.setString(6, partner.getEmail());
            statement.setString(7, partner.getURL());
            statement.setString(8, partner.getMdnURL());
            statement.setString(9, partner.getSubject());
            statement.setString(10, partner.getContentType());
            statement.setInt(11, partner.isSyncMDN() ? 1 : 0);
            statement.setString(12, partner.getPollIgnoreListAsString());
            statement.setInt(13, partner.getPollInterval());
            statement.setInt(14, partner.getCompressionType());
            statement.setInt(15, partner.isSignedMDN() ? 1 : 0);
            statement.setInt(16, partner.getAuthentication().isEnabled() ? 1 : 0);
            statement.setString(17, partner.getAuthentication().getUser());
            statement.setString(18, partner.getAuthentication().getPassword());
            statement.setInt(19, partner.getAuthenticationAsyncMDN().isEnabled() ? 1 : 0);
            statement.setString(20, partner.getAuthenticationAsyncMDN().getUser());
            statement.setString(21, partner.getAuthenticationAsyncMDN().getPassword());
            statement.setInt(22, partner.getKeepOriginalFilenameOnReceipt() ? 1 : 0);
            this.setTextParameterAsJavaObject(statement, 23, partner.getComment());
            statement.setInt(24, partner.getNotifySend());
            statement.setInt(25, partner.getNotifyReceive());
            statement.setInt(26, partner.getNotifySendReceive());
            statement.setInt(27, partner.isNotifySendEnabled() ? 1 : 0);
            statement.setInt(28, partner.isNotifyReceiveEnabled() ? 1 : 0);
            statement.setInt(29, partner.isNotifySendReceiveEnabled() ? 1 : 0);
            statement.setInt(30, partner.getContentTransferEncoding());
            statement.setString(31, partner.getHttpProtocolVersion());
            statement.setInt(32, partner.getMaxPollFiles());
            this.setTextParameterAsJavaObject(statement, 33, partner.getContactAS2());
            this.setTextParameterAsJavaObject(statement, 34, partner.getContactCompany());
            statement.setInt(35, partner.getUseAlgorithmIdentifierProtectionAttribute() ? 1 : 0);
            statement.setInt(36, partner.isEnableDirPoll() ? 1 : 0);
            //where statement
            statement.setInt(37, partner.getDBId());
            statement.execute();
        } catch (SQLException e) {
            this.logger.severe("updatePartner: " + e.getMessage());
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
        this.storeHttpHeader(partner);
        this.certificateAccess.storePartnerCertificateInformationList(partner);
        this.eventAccess.storePartnerEvents(partner);
    }

    /**
     * Deletes a single partner from the database
     */
    public void deletePartner(Partner partner) {
        this.deleteHttpHeader(partner);
        this.certificateAccess.deletePartnerCertificateInformationList(partner);
        this.eventAccess.deletePartnerEvents(partner);
        PartnerSystemAccessDB partnerSystemAccess = new PartnerSystemAccessDB(this.configConnection, this.runtimeConnection);
        partnerSystemAccess.deletePartnerSystem(partner);
        PreparedStatement statement = null;
        try {
            statement = this.configConnection.prepareStatement("DELETE FROM partner WHERE id=?");
            statement.setInt(1, partner.getDBId());
            statement.execute();
        } catch (SQLException e) {
            this.logger.severe("PartnerAccessDB.deletePartner: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("PartnerAccessDB.deletePartner: " + e.getMessage());
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
    }

    /**
     * Inserts a new partner into the database
     */
    public Partner insertPartner(Partner partner) {
        PreparedStatement statement = null;
        try {
            statement = this.configConnection.prepareStatement(
                    "INSERT INTO partner("
                    + "as2ident,partnername,islocal,sign,encrypt,email,url,mdnurl,"
                    + "msgsubject,contenttype,syncmdn,pollignorelist,pollinterval,"
                    + "msgcompression,signedmdn,"
                    + "usehttpauth,httpauthuser,httpauthpass,usehttpauthasyncmdn,"
                    + "httpauthuserasnymdn,httpauthpassasnymdn,keeporiginalfilenameonreceipt,"
                    + "partnercomment,notifysend,notifyreceive,notifysendreceive,"
                    + "notifysendenabled,notifyreceiveenabled,notifysendreceiveenabled,"
                    + "contenttransferencoding,httpversion,"
                    + "maxpollfiles,partnercontact,partneraddress,algidentprotatt,enabledirpoll"
                    + ")VALUES("
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, partner.getAS2Identification());
            statement.setString(2, partner.getName());
            statement.setInt(3, partner.isLocalStation() ? 1 : 0);
            statement.setInt(4, partner.getSignType());
            statement.setInt(5, partner.getEncryptionType());
            statement.setString(6, partner.getEmail());
            statement.setString(7, partner.getURL());
            statement.setString(8, partner.getMdnURL());
            statement.setString(9, partner.getSubject());
            statement.setString(10, partner.getContentType());
            statement.setInt(11, partner.isSyncMDN() ? 1 : 0);
            statement.setString(12, partner.getPollIgnoreListAsString());
            statement.setInt(13, partner.getPollInterval());
            statement.setInt(14, partner.getCompressionType());
            statement.setInt(15, partner.isSignedMDN() ? 1 : 0);
            statement.setInt(16, partner.getAuthentication().isEnabled() ? 1 : 0);
            statement.setString(17, partner.getAuthentication().getUser());
            statement.setString(18, partner.getAuthentication().getPassword());
            statement.setInt(19, partner.getAuthenticationAsyncMDN().isEnabled() ? 1 : 0);
            statement.setString(20, partner.getAuthenticationAsyncMDN().getUser());
            statement.setString(21, partner.getAuthenticationAsyncMDN().getPassword());
            statement.setInt(22, partner.getKeepOriginalFilenameOnReceipt() ? 1 : 0);
            this.setTextParameterAsJavaObject(statement, 23, partner.getComment());
            statement.setInt(24, partner.getNotifySend());
            statement.setInt(25, partner.getNotifyReceive());
            statement.setInt(26, partner.getNotifySendReceive());
            statement.setInt(27, partner.isNotifySendEnabled() ? 1 : 0);
            statement.setInt(28, partner.isNotifyReceiveEnabled() ? 1 : 0);
            statement.setInt(29, partner.isNotifySendReceiveEnabled() ? 1 : 0);
            statement.setInt(30, partner.getContentTransferEncoding());
            statement.setString(31, partner.getHttpProtocolVersion());
            statement.setInt(32, partner.getMaxPollFiles());
            this.setTextParameterAsJavaObject(statement, 33, partner.getContactAS2());
            this.setTextParameterAsJavaObject(statement, 34, partner.getContactCompany());
            statement.setInt(35, partner.getUseAlgorithmIdentifierProtectionAttribute() ? 1 : 0);
            statement.setInt(36, partner.isEnableDirPoll() ? 1 : 0);
            statement.execute();
        } catch (SQLException e) {
            this.logger.severe("PartnerAccessDB.insertPartner: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("PartnerAccessDB.insertPartner: " + e.getMessage());
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
        //get the assigned db id
        Partner storedPartner = this.getPartner(partner.getAS2Identification());
        if (storedPartner != null) {
            partner.setDBId(storedPartner.getDBId());
            this.storeHttpHeader(partner);
            this.certificateAccess.storePartnerCertificateInformationList(partner);
            this.eventAccess.storePartnerEvents(partner);
        }
        return (storedPartner);
    }

    /**
     * Loads a specified partner from the DB
     *
     * @return null if the partner does not exist
     */
    public Partner getPartner(String as2ident) {
        return( this.getPartnerByAS2Id(as2ident, DATA_COMPLETENESS_FULL));
    }

    /**
     * Loads a specified partner from the DB
     *
     * @return null if the partner does not exist
     */
    public Partner getPartnerByAS2Id(String as2ident, int dataCompleteness) {
        String query = "SELECT * FROM partner WHERE as2ident='" + as2ident + "'";
        List<Partner> partner = this.getPartnerByQuery(query, dataCompleteness);
        if (partner == null || partner.isEmpty()) {
            return (null);
        }
        return (partner.get(0));
    }
    
    
    /**
     * Loads a specified partner from the DB
     *
     * @return null if the partner does not exist
     */
    public Partner getPartner(int dbId) {
        String query = "SELECT * FROM partner WHERE id=" + dbId;
        List<Partner> partner = this.getPartnerByQuery(query, DATA_COMPLETENESS_FULL);
        if (partner == null || partner.isEmpty()) {
            return (null);
        }
        return (partner.get(0));
    }

    /*
     * loads the partner specific http headers from the db and assigns it to the
     * passed partner
     */
    private void loadHttpHeader(Partner partner) {
        int partnerId = partner.getDBId();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = this.configConnection.prepareStatement("SELECT * FROM httpheader WHERE partnerid=?");
            statement.setInt(1, partnerId);
            result = statement.executeQuery();
            while (result.next()) {
                PartnerHttpHeader header = new PartnerHttpHeader();
                header.setKey(result.getString("headerkey"));
                header.setValue(result.getString("headervalue"));
                partner.addHttpHeader(header);
            }
        } catch (SQLException e) {
            this.logger.severe("PartnerAccessDB.loadHttpHeader: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } catch (Exception e) {
            this.logger.severe("PartnerAccessDB.loadHttpHeader: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("PartnerAccessDB.loadHttpHeader: " + e.getMessage());
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("PartnerAccessDB.loadHttpHeader: " + e.getMessage());
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
    }

    /**
     * Deletes a single partners http header from the database
     */
    private void deleteHttpHeader(Partner partner) {
        PreparedStatement statement = null;
        try {
            statement = this.configConnection.prepareStatement("DELETE FROM httpheader WHERE partnerid=?");
            statement.setInt(1, partner.getDBId());
            statement.execute();
        } catch (SQLException e) {
            this.logger.severe("PartnerAccessDB.deleteHttpHeader: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
        } catch (Exception e) {
            this.logger.severe("PartnerAccessDB.deleteHttpHeader: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
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
     * Updates a single partners http header in the db
     */
    private void storeHttpHeader(Partner partner) {
        this.deleteHttpHeader(partner);
        //clear unused headers in the partner object
        partner.deleteEmptyHttpHeader();
        List<PartnerHttpHeader> headerList = partner.getHttpHeader();
        for (PartnerHttpHeader header : headerList) {
            PreparedStatement statement = null;
            try {
                statement = this.configConnection.prepareStatement("INSERT INTO httpheader(partnerid,headerkey,headervalue)VALUES(?,?,?)");
                statement.setInt(1, partner.getDBId());
                statement.setString(2, header.getKey());
                statement.setString(3, header.getValue());
                statement.execute();
            } catch (SQLException e) {
                this.logger.severe("PartnerAccessDB.storeHttpHeader: " + e.getMessage());
                SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY, statement);
            } catch (Exception e) {
                this.logger.severe("PartnerAccessDB.storeHttpHeader: " + e.getMessage());
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
}
