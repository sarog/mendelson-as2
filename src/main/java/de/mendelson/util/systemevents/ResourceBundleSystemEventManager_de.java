//$Header: /as2/de/mendelson/util/systemevents/ResourceBundleSystemEventManager_de.java 4     9.06.20 10:11 Heller $
package de.mendelson.util.systemevents;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ResourceBundleSystemEventManager_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"label.body.clientip", "IP Adresse: {0}"},
        {"label.body.processid", "Prozessnummer im Betriebssystem des Clients: {0}"},
        {"label.body.clientos", "Betriebssystem des Clients: {0}"},
        {"label.body.clientversion", "Version des Clients: {0}"},
        {"label.body.details", "Details: {0}"},
        {"label.subject.login.success", "Benutzeranmeldung erfolgreich [{0}]"},
        {"label.subject.login.failed", "Benutzeranmeldung fehlgeschlagen [{0}]"},
        {"label.subject.logoff", "Benutzerabmeldung [{0}]"}, 
        {"label.body.tlsprotocol", "TLS Protokoll: {0}" },
        {"label.body.tlsciphersuite", "TLS Chiffre: {0}" },
    };
}
