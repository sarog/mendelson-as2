//$Header: /as2/de/mendelson/util/systemevents/ResourceBundleSystemEventManager_fr.java 4     9.06.20 10:11 Heller $
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
public class ResourceBundleSystemEventManager_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"label.body.clientip", "Adresse IP: {0}"},
        {"label.body.processid", "Num�ro de processus: {0}"},
        {"label.body.clientos", "Syst�me d''exploitation: {0}"},
        {"label.body.clientversion", "Version du client: {0}"},
        {"label.body.details", "D�tails: {0}"},
        {"label.subject.login.success", "Connexion de l''utilisateur r�ussie [{0}]"},
        {"label.subject.login.failed", "�chec de la connexion de l''utilisateur [{0}]"},
        {"label.subject.logoff", "D�connexion de l''utilisateur [{0}]"},
        {"label.body.tlsprotocol", "Protocole TLS: {0}" },
        {"label.body.tlsciphersuite", "Chiffre TLS: {0}" },        
    };
}
