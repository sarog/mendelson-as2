//$Header: /as2/de/mendelson/comm/as2/server/ClientServerSessionHandlerLocalhost.java 8     15.03.19 11:52 Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServerSessionHandler;
import de.mendelson.util.clientserver.messages.ServerLogMessage;
import de.mendelson.util.systemevents.SystemEventManager;
import java.net.InetSocketAddress;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mina.core.session.IoSession;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Session handler for the server implementation
 *
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class ClientServerSessionHandlerLocalhost extends ClientServerSessionHandler {

    private MecResourceBundle rb;
    private boolean allowAllClients = false;

    public ClientServerSessionHandlerLocalhost(Logger logger, String[] validClientIds, boolean allowAllClients, int maxClients,
            SystemEventManager eventManager) {
        super(logger, validClientIds, maxClients, eventManager);
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleClientServerSessionHandlerLocalhost.class.getName());
        } //load up  resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.allowAllClients = allowAllClients;
        System.out.println(this.rb.getResourceString("allowallclients." + String.valueOf(allowAllClients)));
    }

    @Override
    /**
     * The session has been opened: send a server info object
     */
    public void sessionOpened(IoSession session) {
        try {
            InetSocketAddress localAddress = (InetSocketAddress) session.getLocalAddress();
            InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
            if (!this.allowAllClients && !localAddress.getHostName().equalsIgnoreCase(remoteAddress.getHostName())) {
                ServerLogMessage message = new ServerLogMessage();
                message.setLevel(Level.SEVERE);
                message.setMessage(this.rb.getResourceString("only.localhost.clients"));
                session.write(message);
                boolean immediately = false;
                session.close(immediately);
            } else {
                super.sessionOpened(session);
            }
        } catch (Exception e) {
            session.close(true);
        }
    }
}
