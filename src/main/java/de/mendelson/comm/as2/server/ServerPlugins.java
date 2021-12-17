//$Header: /mec_as2/de/mendelson/comm/as2/server/ServerPlugins.java 2     17.12.20 9:41 Heller $
package de.mendelson.comm.as2.server;

import java.io.Serializable;
import java.util.logging.Logger;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores information about the activation state of the plugins
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ServerPlugins implements Serializable {

    public static final long serialVersionUID = 1L;
    public static final String PLUGIN_POSTGRESQL = "dummy";

    public ServerPlugins() {
    }

    public void displayActivationState(Logger logger) {
    }

    public void setStartPlugins(boolean dummy) {
    }

    public void setActivated(final String dummy, boolean dummy1) {
    }

    public boolean isActivated(final String dummy) {
        return (false);
    }

    public String getVersion(final String dummy) {
        return ("--");
    }

    public String getStartedPluginsAsString() {
        return ("--");
    }

}
