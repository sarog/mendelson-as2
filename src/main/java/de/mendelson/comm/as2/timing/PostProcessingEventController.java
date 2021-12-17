//$Header: /as2/de/mendelson/comm/as2/timing/PostProcessingEventController.java 6     10.09.20 12:57 Heller $
package de.mendelson.comm.as2.timing;

import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.message.postprocessingevent.ExecuteMoveToDir;
import de.mendelson.comm.as2.message.postprocessingevent.ExecuteMoveToPartner;
import de.mendelson.comm.as2.message.postprocessingevent.ExecuteShellCommand;
import de.mendelson.comm.as2.message.postprocessingevent.IProcessingExecution;
import de.mendelson.comm.as2.message.postprocessingevent.PostprocessingException;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEvent;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEventAccessDB;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerEventInformation;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import de.mendelson.util.systemevents.notification.Notification;
import de.mendelson.util.systemevents.notification.NotificationImplAS2;
import java.sql.Connection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Controls the timed deletion of AS2 file entries from the file system
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class PostProcessingEventController {

    /**
     * Logger to log information to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private EventExecutionThread executeThread;
    private ClientServer clientserver = null;
    private MecResourceBundle rb = null;
    private Connection configConnection;
    private Connection runtimeConnection;
    private CertificateManager certificateManagerEncSign;
    private Notification notification;
    private MessageAccessDB messageAccess;

    public PostProcessingEventController(ClientServer clientserver, Connection configConnection,
            Connection runtimeConnection, CertificateManager certificateManagerEncSign) {
        this.clientserver = clientserver;
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.certificateManagerEncSign = certificateManagerEncSign;
        this.notification = new NotificationImplAS2(configConnection, runtimeConnection);
        this.messageAccess = new MessageAccessDB(configConnection, runtimeConnection);
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleFileDeleteController.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Starts the embedded task that guards the files to delete
     */
    public void startEventExecution() {
        this.executeThread = new EventExecutionThread(this.configConnection, this.runtimeConnection);
        Executors.newSingleThreadExecutor().submit(this.executeThread);
    }

    public class EventExecutionThread implements Runnable {

        private boolean stopRequested = false;
        //wait this time between checks
        private final long WAIT_TIME = TimeUnit.SECONDS.toMillis(10);
        //DB connection
        private Connection configConnection;
        private Connection runtimeConnection;
        private ProcessingEventAccessDB processingEventAccess;

        public EventExecutionThread(Connection configConnection, Connection runtimeConnection) {
            this.configConnection = configConnection;
            this.runtimeConnection = runtimeConnection;
            this.processingEventAccess = new ProcessingEventAccessDB(configConnection, runtimeConnection);
        }

        @Override
        public void run() {
            Thread.currentThread().setName("Contol post processing of events");
            while (!stopRequested) {
                try {
                    try {
                        Thread.sleep(WAIT_TIME);
                    } catch (InterruptedException e) {
                        //nop
                    }
                    boolean entryFound = true;
                    while (entryFound) {
                        entryFound = false;
                        ProcessingEvent event = this.processingEventAccess.getNextEventToExecute();
                        IProcessingExecution processExecution = null;
                        if (event != null && event.getProcessType() == PartnerEventInformation.PROCESS_EXECUTE_SHELL) {
                            processExecution = new ExecuteShellCommand(this.configConnection, this.runtimeConnection);
                            entryFound = true;
                        } else if (event != null && event.getProcessType() == PartnerEventInformation.PROCESS_MOVE_TO_DIR) {
                            processExecution = new ExecuteMoveToDir(this.configConnection, this.runtimeConnection);
                            entryFound = true;
                        } else if (event != null && event.getProcessType() == PartnerEventInformation.PROCESS_MOVE_TO_PARTNER) {
                            processExecution = new ExecuteMoveToPartner(this.configConnection, this.runtimeConnection,
                                    PostProcessingEventController.this.certificateManagerEncSign);
                            entryFound = true;
                        }
                        if (entryFound && processExecution != null) {
                            try {
                                processExecution.executeProcess(event);
                            } catch (Throwable e) {
                                String errorMessage = "[" + e.getClass().getSimpleName() + "] " + e.getMessage();
                                AS2MessageInfo messageInfo = messageAccess.getLastMessageEntry(event.getMessageId());
                                Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
                                logger.log(Level.WARNING, errorMessage, messageInfo);
                                SystemEventManagerImplAS2 eventManager = new SystemEventManagerImplAS2();
                                Partner sender = null;
                                Partner receiver = null;
                                if( e instanceof PostprocessingException){
                                    sender = ((PostprocessingException)e).getSender();
                                    receiver = ((PostprocessingException)e).getReceiver();
                                }
                                eventManager.newEventPostprocessingError( errorMessage,
                                        event.getMessageId(), sender, receiver,
                                        event.getProcessType(), event.getEventType());
                            }
                        }
                    }
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.systemFailure(e);
                }
            }
        }
    }

}
