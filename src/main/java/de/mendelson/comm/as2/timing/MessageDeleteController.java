//$Header: /as2/de/mendelson/comm/as2/timing/MessageDeleteController.java 36    24.04.20 9:52 Heller $
package de.mendelson.comm.as2.timing;

import de.mendelson.comm.as2.clientserver.message.RefreshClientMessageOverviewList;
import de.mendelson.comm.as2.log.LogAccessDB;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.preferences.ResourceBundlePreferences;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Controls the timed deletion of AS2 entries from the log
 *
 * @author S.Heller
 * @version $Revision: 36 $
 */
public class MessageDeleteController {

    /**
     * Logger to log information to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private PreferencesAS2 preferences = new PreferencesAS2();
    private MessageDeleteThread deleteThread;
    private ClientServer clientserver = null;
    private MecResourceBundle rb = null;
    private MecResourceBundle rbTime = null;
    private Connection configConnection;
    private Connection runtimeConnection;

    public MessageDeleteController(ClientServer clientserver, Connection configConnection,
            Connection runtimeConnection) {
        this.clientserver = clientserver;
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleMessageDeleteController.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        try {
            this.rbTime = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePreferences.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Starts the embedded task that guards the log
     */
    public void startAutoDeleteControl() {
        this.deleteThread = new MessageDeleteThread(this.configConnection, this.runtimeConnection);
        Executors.newSingleThreadExecutor().submit(this.deleteThread);
    }

    /**
     * Deletes a message entry from the log. Clears all files
     */
    public void deleteMessageFromLog(AS2MessageInfo info, boolean broadcastRefresh, StringBuilder deleteLog) {
        LogAccessDB logAccess = new LogAccessDB(this.configConnection, this.runtimeConnection);
        logAccess.deleteMessageLog(info.getMessageId());
        MessageAccessDB messageAccess = new MessageAccessDB(this.configConnection, this.runtimeConnection);
        try {
            //delete all raw files from the disk
            List<String> rawfilenames = messageAccess.getRawFilenamesToDelete(info);
            if (rawfilenames != null) {
                for (String rawfilename : rawfilenames) {
                    try {
                        Files.delete(Paths.get(rawfilename));
                        deleteLog.append("[" + rb.getResourceString("delete.ok") + "]: ");
                    } catch (NoSuchFileException e) {
                        deleteLog.append("[" + rb.getResourceString("delete.skipped")
                                + "]: (" + e.getClass().getSimpleName() + ")  " + e.getMessage());
                    } catch (Exception e) {
                        deleteLog.append("[" + rb.getResourceString("delete.failed")
                                + "]: (" + e.getClass().getSimpleName() + ")  " + e.getMessage());
                        SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_FILE_OPERATION_ANY);
                        new File(rawfilename).deleteOnExit();
                    }
                    deleteLog.append("  ");
                    deleteLog.append(Paths.get(rawfilename).toAbsolutePath().toString());
                    deleteLog.append(System.lineSeparator());
                }
            }
            messageAccess.deleteMessage(info);
        } catch (Exception e) {
            this.logger.severe("deleteMessageFromLog: " + e.getMessage());
            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_PROCESSING_ANY);
        }
        if (broadcastRefresh && this.clientserver != null) {
            this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
        }
    }

    public class MessageDeleteThread implements Runnable {

        private boolean stopRequested = false;
        //wait this time between checks
        private final long WAIT_TIME_ONE_MINUTE = TimeUnit.MINUTES.toMillis(1);
        //DB connection
        private Connection configConnection;
        private Connection runtimeConnection;

        public MessageDeleteThread(Connection configConnection, Connection runtimeConnection) {
            this.configConnection = configConnection;
            this.runtimeConnection = runtimeConnection;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("Contol auto AS2 message delete");
            while (!stopRequested) {
                try {
                    try {
                        Thread.sleep(WAIT_TIME_ONE_MINUTE);
                    } catch (InterruptedException e) {
                        //nop
                    }
                    if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE)) {
                        MessageAccessDB messageAccess = null;
                        try {
                            messageAccess = new MessageAccessDB(this.configConnection, this.runtimeConnection);
                            long olderThan = System.currentTimeMillis()
                                    - TimeUnit.SECONDS.toMillis(
                                            preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)
                                            * preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S));
                            List<AS2MessageInfo> overviewList = messageAccess.getMessagesOlderThan(olderThan, -1);
                            if (overviewList != null) {
                                List<AS2MessageInfo> deletedTransactionList = new ArrayList<AS2MessageInfo>();
                                List<StringBuilder> deletedTransactionLog = new ArrayList<StringBuilder>();
                                for (AS2MessageInfo messageInfo : overviewList) {
                                    if (messageInfo.getState() == AS2Message.STATE_FINISHED || messageInfo.getState() == AS2Message.STATE_STOPPED) {
                                        if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE_LOG)) {
                                            String timeUnit = "";
                                            if (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S) == TimeUnit.DAYS.toSeconds(1)) {
                                                timeUnit = rbTime.getResourceString("maintenancemultiplier.day");
                                            } else if (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S) == TimeUnit.HOURS.toSeconds(1)) {
                                                timeUnit = rbTime.getResourceString("maintenancemultiplier.hour");
                                            } else if (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S) == TimeUnit.MINUTES.toSeconds(1)) {
                                                timeUnit = rbTime.getResourceString("maintenancemultiplier.minute");
                                            }
                                            logger.fine(rb.getResourceString("autodelete",
                                                    new Object[]{
                                                        messageInfo.getMessageId(),
                                                        String.valueOf(preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)),
                                                        timeUnit
                                                    }));
                                        }
                                        StringBuilder singleDeleteLog = new StringBuilder();
                                        deleteMessageFromLog(messageInfo, true, singleDeleteLog);
                                        deletedTransactionLog.add(singleDeleteLog);
                                        deletedTransactionList.add(messageInfo);
                                    }
                                }
                                //fire system event if automatic log deletes should be loged
                                if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE_LOG)) {
                                    this.fireSystemEventTransactionsDeletedByMaintenance(olderThan, deletedTransactionList,
                                            deletedTransactionLog);
                                }
                            }
                        } catch (Throwable e) {
                            logger.severe("MessageDeleteThread: [" + e.getClass().getSimpleName() + "]:" + e.getMessage());
                            SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_PROCESSING_ANY);
                        }
                    }
                } catch (Throwable e) {
                    //final try/catch - this thead must not stop!
                    logger.severe("MessageDeleteThread: [" + e.getClass().getSimpleName() + "]:" + e.getMessage());
                    SystemEventManagerImplAS2.systemFailure(e, SystemEvent.TYPE_PROCESSING_ANY);
                }
            }
        }

        /**
         * Fire a system event that the system maintenance process has deleted
         * transactions
         */
        private void fireSystemEventTransactionsDeletedByMaintenance(long olderThan, List<AS2MessageInfo> deletedTransactionList,
                List<StringBuilder> singleTransactionDeleteLog) {
            //Do not fire an event if there are no deleted transactions
            if( deletedTransactionList.isEmpty()){
                return;
            }
            DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            SystemEvent event = new SystemEvent(
                    SystemEvent.SEVERITY_INFO,
                    SystemEvent.ORIGIN_SYSTEM,
                    SystemEvent.TYPE_TRANSACTION_DELETE);
            event.setSubject(rb.getResourceString("transaction.deleted.system"));
            StringBuilder builder = new StringBuilder();
            builder.append(rb.getResourceString("transaction.delete.setting.olderthan", dateFormat.format(new Date(olderThan))));
            builder.append(System.lineSeparator()).append(System.lineSeparator());
            for (int i = 0; i < deletedTransactionList.size(); i++) {
                AS2MessageInfo singleInfo = deletedTransactionList.get(i);
                StringBuilder singleDeleteLog = singleTransactionDeleteLog.get(i);
                builder.append("---").append(System.lineSeparator());
                builder.append("[");
                builder.append(rb.getResourceString("transaction.deleted.transactiondate",
                        dateFormat.format(singleInfo.getInitDate())));
                builder.append("] (");
                builder.append(singleInfo.getSenderId());
                builder.append(" --> ");
                builder.append(singleInfo.getReceiverId());
                builder.append(") ");
                builder.append(singleInfo.getMessageId());
                builder.append(System.lineSeparator());
                builder.append(singleDeleteLog);
                builder.append(System.lineSeparator());
            }
            event.setBody(builder.toString());
            SystemEventManagerImplAS2.newEvent(event);
        }

    }
}
