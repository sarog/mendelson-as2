//$Header: /as2/de/mendelson/comm/as2/message/store/ResourceBundleMessageStoreHandler_fr.java 6     7.12.18 9:45 Heller $
package de.mendelson.comm.as2.message.store;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 6 $
 */
public class ResourceBundleMessageStoreHandler_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"message.error.stored", "Contenu du message stock� vers \"{0}\"." },
        {"message.error.raw.stored", "Message sortant brut stock� vers \"{0}\"." },
        {"dir.createerror", "Cr�ation impossible du r�pertoire \"{0}\"." },
        {"comm.success", "Succ�s de la communication AS2, le contenu {0} a �t� d�plac� vers \"{1}\"." },
        {"outboundstatus.written", "Fichier d''�tat sortant �crit \"{0}\"."},
    };
    
}
