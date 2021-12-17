//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2ServerProcessing_fr.java 12    7.12.18 9:51 Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 12 $
 */
public class ResourceBundleAS2ServerProcessing_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"send.failed", "Send a �chou�"},
        {"unable.to.process", "Impossible de traiter sur serveur : {0}"},
        {"server.shutdown", "L''utilisateur {0} demande l''arr�t du serveur."},
        {"sync.mdn.sent", "MDN synchrone envoy� comme r�ponse au message {0}." },
        {"info.mdn.inboundfiles", "Pour le MDN a re�u il n'a pas �t� possible de d�terminer le message AS2 r�f�renc�.\n[Commentaires re�us MDN: {0}]\n[Commentaires re�us MDN (Header): {1}]"},
        {"message.resend.oldtransaction", "Cette transaction a �t� envoy�e � nouveau manuellement avec le nouveau num�ro de transaction [{0}]." },
        {"message.resend.newtransaction", "Cette transaction est un renvoi de la transaction [{0}]." },    
        {"message.resend.title", "Envoi manuel des donn�es dans la nouvelle transaction" },    
    };
}
