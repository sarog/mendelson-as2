//$Header: /as2/de/mendelson/comm/as2/sendorder/SendOrderReceiver.java 35    26.08.21 15:21 Heller $
package de.mendelson.comm.as2.sendorder;

import de.mendelson.comm.as2.clientserver.message.RefreshClientMessageOverviewList;
import de.mendelson.comm.as2.message.AS2MDNInfo;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEvent;
import de.mendelson.comm.as2.message.store.MessageStoreHandler;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.send.HttpConnectionParameter;
import de.mendelson.comm.as2.send.MessageHttpUploader;
import de.mendelson.comm.as2.send.NoConnectionException;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Receiver class that reads the enqueued send orders and starts the AS2 message
 * send process for each message
 *
 * @author S.Heller
 * @version $Revision: 35 $
 */
public class SendOrderReceiver {

    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private MecResourceBundle rb;
    private Connection configConnection;
    private Connection runtimeConnection;
    //transactional connection to the database just to read the data from the poll queue - no auto commit
    private Connection runtimeConnectionPollNoCommit;
    private SendOrderAccessDB sendOrderAccess;
    /**
     * Needed for refresh
     */
    private ClientServer clientserver = null;
    /**
     * Server preferences
     */
    private PreferencesAS2 preferences = new PreferencesAS2();
    /**
     * Handles messages storage
     */
    private MessageStoreHandler messageStoreHandler;
    private MessageAccessDB messageAccess;
    private IDBDriverManager dbDriverManager;
    private SendOrderReceiverThread sendOrderReceiverThread = null;
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    public SendOrderReceiver(Connection configConnection, Connection runtimeConnection,
            ClientServer clientserver, IDBDriverManager dbDriverManager) throws Exception {
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSendOrderReceiver.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.dbDriverManager = dbDriverManager;
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.runtimeConnectionPollNoCommit = dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME);
        this.runtimeConnectionPollNoCommit.setAutoCommit(false);
        this.sendOrderAccess = new SendOrderAccessDB(
                dbDriverManager, this.configConnection, this.runtimeConnection);
        this.messageAccess = new MessageAccessDB(this.dbDriverManager, this.configConnection, this.runtimeConnection);
        this.messageStoreHandler = new MessageStoreHandler(dbDriverManager, this.configConnection, this.runtimeConnection);
        this.clientserver = clientserver;
    }

    public void execute() {
        this.sendOrderReceiverThread = new SendOrderReceiverThread();
        this.scheduledExecutor.scheduleWithFixedDelay(this.sendOrderReceiverThread, 1, 5, TimeUnit.SECONDS);
    }

    public class SendOrderReceiverThread implements Runnable {

        private ThreadPoolExecutor threadExecutor;
        private long lastConfigCheckTime = System.currentTimeMillis();
        //stores the time a warning ist last displayed that all outbound connections are used
        private long lastWarningMaxOutboundConnectionsReachedTime = System.currentTimeMillis();
        private int maxOutboundConnections = 0;

        public SendOrderReceiverThread() {
            this.maxOutboundConnections = preferences.getInt(PreferencesAS2.MAX_OUTBOUND_CONNECTIONS);
        if (maxOutboundConnections == 0) {
                logger.config(rb.getResourceString("as2.send.disabled"));
        }
        //Max number of outbound connections. All other connection attempts are scheduled in a queue
        //Queue to store the (not active) threads
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        //This is a fixed thread executor - it will not use the queue
        //Parameter for pool executor: corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue        
            this.threadExecutor
                = new ThreadPoolExecutor(Math.max(maxOutboundConnections, 1), Math.max(maxOutboundConnections, 1),
                        0L, TimeUnit.MILLISECONDS, queue);
        }

        @Override
        public void run() {
            //this try is necessary because this thread must never stop. If it stops no more messages
            //and MDN are send!
            try {
                //check for a configuration change - if the user changed the number of outbound connections
                //that has to be computed. This check will just happen from time to time
                if (System.currentTimeMillis() - this.lastConfigCheckTime > TimeUnit.SECONDS.toMillis(10)) {
                    int activeConnections = this.threadExecutor.getActiveCount();
                    //check if the user has changed the outbound connection settings
                    int maxOutboundConnectionsNew = preferences.getInt(PreferencesAS2.MAX_OUTBOUND_CONNECTIONS);
                    if (maxOutboundConnectionsNew != this.maxOutboundConnections) {
                        try {
                            this.maxOutboundConnections = maxOutboundConnectionsNew;
                            if (this.maxOutboundConnections > 0) {
                                if (this.threadExecutor.getCorePoolSize() != this.maxOutboundConnections) {
                                    this.threadExecutor.setCorePoolSize(this.maxOutboundConnections);
                                    if (this.threadExecutor.getMaximumPoolSize() != this.maxOutboundConnections) {
                                        this.threadExecutor.setMaximumPoolSize(this.maxOutboundConnections);
                                    }
                                }
                                logger.config(rb.getResourceString("as2.send.newmaxconnections", String.valueOf(this.maxOutboundConnections)));
                            } else {
                                logger.config(rb.getResourceString("as2.send.disabled"));
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                    if (activeConnections > this.maxOutboundConnections) {
                        logger.config(rb.getResourceString("send.connectionsstillopen",
                                new Object[]{
                                    String.valueOf(this.maxOutboundConnections),
                                    String.valueOf(activeConnections)
                                }));
                    }
                    this.lastConfigCheckTime = System.currentTimeMillis();
                }
                //check if new outbound connection are currently possible
                int possibleNewConnections = this.maxOutboundConnections - this.threadExecutor.getActiveCount();
                if (possibleNewConnections > 0) {
                    //Get max number of outbound send orders and pass them to the thread executor
                    List<SendOrder> waitingOrders = sendOrderAccess.getNext(
                            possibleNewConnections, dbDriverManager, runtimeConnectionPollNoCommit);
                    for (SendOrder order : waitingOrders) {
                        final SendOrder finalOrder = order;
                        final int finalMaxOutboundConnectionCount = maxOutboundConnections;
                        final ThreadPoolExecutor finalFixedThreadExecutor = threadExecutor;
                        Runnable connectionRunner = new Runnable() {
                            @Override
                            public void run() {
                                final int finalActiveConnectionCount = finalFixedThreadExecutor.getActiveCount();
                                processOrder(finalOrder, finalMaxOutboundConnectionCount, finalActiveConnectionCount);
                            }
                        };
                        threadExecutor.execute(connectionRunner);
                    }
                } else {
                    if (System.currentTimeMillis() - this.lastWarningMaxOutboundConnectionsReachedTime > TimeUnit.MINUTES.toMillis(1)) {
                        logger.warning(rb.getResourceString("warning.nomore.outbound.connections.available",
                                new Object[]{
                                    String.valueOf(maxOutboundConnections),}));
                        this.lastWarningMaxOutboundConnectionsReachedTime = System.currentTimeMillis();
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                SystemEventManagerImplAS2.systemFailure(e);
        }
    }

    /**
         * Processes a single send order
     */
    private void processOrder(SendOrder order,
            int maxOutboundConnectionsCount,
            int activeConnectionsCount) {
        try {
            boolean processingAllowed = true;
            //before performing the send there has to be checked if the send process is still valid. The orders
            //are queued, between scheduling and processing the orders the transmission time could expire
            //or the user could cancel it
            if (order.getMessage().isMDN()) {
                //if the MDN state is on failure then the related transmission is on failure state, too - 
                //checking this makes no sense here
                AS2MDNInfo mdnInfo = (AS2MDNInfo) order.getMessage().getAS2Info();
                AS2MessageInfo relatedMessageInfo = messageAccess.getLastMessageEntry(mdnInfo.getRelatedMessageId());
                if (relatedMessageInfo == null) {
                    processingAllowed = false;
                }
            } else {
                AS2MessageInfo messageInfo = (AS2MessageInfo) order.getMessage().getAS2Info();
                if (messageInfo.getMessageType() == AS2Message.MESSAGETYPE_AS2) {
                    //update the message info from the database
                        messageInfo = messageAccess.getLastMessageEntry(messageInfo.getMessageId());
                    if (messageInfo == null || messageInfo.getState() == AS2Message.STATE_STOPPED) {
                        processingAllowed = false;
                    }
                } else if (messageInfo.getMessageType() == AS2Message.MESSAGETYPE_CEM) {
                    processingAllowed = true;
                }
            }
            if (processingAllowed) {
                //display some log information that the outbound connection is prepared
                if (order.getMessage().isMDN()) {
                    AS2MDNInfo mdnInfo = (AS2MDNInfo) order.getMessage().getAS2Info();
                        AS2MessageInfo relatedMessageInfo = messageAccess.getLastMessageEntry(mdnInfo.getRelatedMessageId());                        
                        String asyncMDNURL = relatedMessageInfo.getAsyncMDNURL();
                    logger.log(Level.INFO, rb.getResourceString("outbound.connection.prepare.mdn",
                            new Object[]{
                                    asyncMDNURL,
                                String.valueOf(activeConnectionsCount),
                                String.valueOf(maxOutboundConnectionsCount),}), mdnInfo);
                } else {
                    //its a AS2 message that has been sent
                    AS2MessageInfo messageInfo = (AS2MessageInfo) order.getMessage().getAS2Info();
                        messageAccess.initializeOrUpdateMessage(messageInfo);
                        logger.log(Level.INFO, rb.getResourceString("outbound.connection.prepare.message",
                            new Object[]{
                                order.getReceiver().getURL(),
                                String.valueOf(activeConnectionsCount),
                                String.valueOf(maxOutboundConnectionsCount),}), messageInfo);
                }
                MessageHttpUploader messageUploader = new MessageHttpUploader();
                    if (!preferences.getBoolean(PreferencesAS2.CEM)) {
                    messageUploader.setEDIINTFeatures("multiple-attachments");
                } else {
                    messageUploader.setEDIINTFeatures("multiple-attachments, CEM");
                }
                    messageUploader.setLogger(logger);
                    messageUploader.setAbstractServer(clientserver);
                    messageUploader.setDBConnection(dbDriverManager, configConnection, runtimeConnection);
                //configure the connection parameters
                HttpConnectionParameter connectionParameter = new HttpConnectionParameter();
                    connectionParameter.setConnectionTimeoutMillis(preferences.getInt(PreferencesAS2.HTTP_SEND_TIMEOUT));
                connectionParameter.setHttpProtocolVersion(order.getReceiver().getHttpProtocolVersion());
                connectionParameter.setProxy(messageUploader.createProxyObjectFromPreferences());
                connectionParameter.setUseExpectContinue(true);
                Properties requestHeader = messageUploader.upload(connectionParameter, order.getMessage(), order.getSender(), order.getReceiver());
                //set error or finish state, remember that this send order could be
                //also an MDN if async MDN is requested
                if (order.getMessage().isMDN()) {
                    AS2MDNInfo mdnInfo = (AS2MDNInfo) order.getMessage().getAS2Info();
                    if (mdnInfo.getState() == AS2Message.STATE_FINISHED) {
                        AS2MessageInfo relatedMessageInfo = messageAccess.getLastMessageEntry(mdnInfo.getRelatedMessageId());
                            messageStoreHandler.movePayloadToInbox(relatedMessageInfo.getMessageType(), mdnInfo.getRelatedMessageId(),
                                order.getSender(), order.getReceiver());
                        //execute a shell command after send SUCCESS
                            ProcessingEvent.enqueueEventIfRequired(dbDriverManager, configConnection, runtimeConnection,
                                relatedMessageInfo, null);
                    }
                    //set the transaction state to the MDN state
                    messageAccess.setMessageState(mdnInfo.getRelatedMessageId(), mdnInfo.getState());
                } else {
                    //its a AS2 message that has been sent
                    AS2MessageInfo messageInfo = (AS2MessageInfo) order.getMessage().getAS2Info();
                    messageAccess.setMessageSendDate(messageInfo);
                    messageAccess.updateFilenames(messageInfo);
                    if (!messageInfo.requestsSyncMDN()) {
                        long endTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(preferences.getInt(PreferencesAS2.ASYNC_MDN_TIMEOUT));
                        DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,
                                DateFormat.MEDIUM);
                        logger.log(Level.INFO, rb.getResourceString("async.mdn.wait",
                                new Object[]{
                                   format.format(endTime)
                                }), messageInfo);
                    }
                }
            }
                //Either it is processed now or the entry in the queue was no longer valid - delete it in both cases
                sendOrderAccess.delete(order.getDbId());
                //send push messages to all clients that the number/state of transaction has been changed
                clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
        } catch (NoConnectionException e) {
            int retryCount = order.incRetryCount();
                int maxRetryCount = preferences.getInt(PreferencesAS2.MAX_CONNECTION_RETRY_COUNT);
            //to many retries: cancel the transaction
            if (retryCount > maxRetryCount) {
                    if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
                    logger.log(Level.WARNING, e.getMessage(), order.getMessage().getAS2Info());
                }
                logger.log(Level.SEVERE, rb.getResourceString("max.retry.reached",
                            String.valueOf(maxRetryCount)), order.getMessage().getAS2Info());
                    sendOrderAccess.delete(order.getDbId());
                this.processUploadError(order);
            } else {
                    if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
                    logger.log(Level.WARNING, e.getMessage(), order.getMessage().getAS2Info());
                }
                logger.log(Level.WARNING, rb.getResourceString("retry",
                        new Object[]{
                                String.valueOf(preferences.getInt(PreferencesAS2.CONNECTION_RETRY_WAIT_TIME_IN_S)),
                            String.valueOf(retryCount),
                            String.valueOf(maxRetryCount)
                        }), order.getMessage().getAS2Info());
                this.sendOrderToRetry(order);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage(), order.getMessage().getAS2Info());
            this.processUploadError(order);
        }
    }

    /**
     * Update the order in the queue - with a new nextexecution time
     */
    private void sendOrderToRetry(SendOrder order) {
        SendOrderSender sender = null;
            sender = new SendOrderSender(dbDriverManager, configConnection, runtimeConnection);
            sender.resend(order, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(
                    preferences.getInt(PreferencesAS2.CONNECTION_RETRY_WAIT_TIME_IN_S)));
    }

    /**
         * The upload process of the data failed. Set the message state, execute
         * the command, ..
     */
    private void processUploadError(SendOrder order) {
        try {
            //stores
                messageStoreHandler.storeSentErrorMessage(
                    order.getMessage(), order.getSender(), order.getReceiver());
            if (!order.getMessage().isMDN()) {
                //message upload failure
                messageAccess.setMessageState(order.getMessage().getAS2Info().getMessageId(),
                        AS2Message.STATE_STOPPED);
                //its important to set the state in the message info, too. An event exec is not performed
                //for pending messages
                order.getMessage().getAS2Info().setState(AS2Message.STATE_STOPPED);
                messageAccess.updateFilenames((AS2MessageInfo) order.getMessage().getAS2Info());
                    ProcessingEvent.enqueueEventIfRequired(dbDriverManager, configConnection, runtimeConnection,
                        (AS2MessageInfo) order.getMessage().getAS2Info(), null);
                //write status file
                    messageStoreHandler.writeOutboundStatusFile((AS2MessageInfo) order.getMessage().getAS2Info());
            } else {
                //MDN send failure, e.g. wrong URL for async MDN in message
                messageAccess.setMessageState(((AS2MDNInfo) order.getMessage().getAS2Info()).getRelatedMessageId(),
                        AS2Message.STATE_STOPPED);
            }
                clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
        } catch (Exception ee) {
            ee.printStackTrace();
            logger.log(Level.SEVERE, "SendOrderReceiver.processUploadError(): " + ee.getMessage(),
                    order.getMessage().getAS2Info());
                messageAccess.setMessageState(order.getMessage().getAS2Info().getMessageId(), AS2Message.STATE_STOPPED);
            }
        }
    }

}
