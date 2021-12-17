//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderReceiver_fr.java 7     12.03.19 13:36 Heller $
package de.mendelson.comm.as2.sendorder;
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
 * @version $Revision: 7 $
 */
public class ResourceBundleSendOrderReceiver_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"async.mdn.wait", "Attente du MDN asynchrone jusqu''� {0}." },
        {"max.retry.reached", "Le maximum a �t� atteint r�essayer ({0}), la transmission annul�e." },
        {"retry", "Va r�essayer d''envoyer transmission apr�s {0}s, r�essayez {1}/{2}." },
        {"as2.send.disabled", "** Le syst�me ne sera pas envoyer de message AS2/MDN parce que le nombre de connexions sortantes parall�les est mis � 0. S''il vous pla�t modifier ces param�tres dans la bo�te de dialogue de configuration du serveur pour permettre l''envoi de nouveau **" },        
        {"outbound.connection.prepare.mdn", "Pr�parer la connexion MDN sortante vers \"{0}\", connexions actives: {1}/{2}." },
        {"outbound.connection.prepare.message", "Pr�parer la connexion AS2 message sortante vers \"{0}\", Connexions actives: {1}/{2}." },
        {"send.connectionsstillopen", "Vous avez r�duit le nombre de connexions sortantes � {0}, mais actuellement, il y a encore {1} connexions sortantes." },
        {"warning.nomore.outbound.connections.available", "Le nombre maximum de connexions sortantes ({0}) a �t� atteint. Il n''est plus possible de cr�er des connexions sortantes suppl�mentaires. Veuillez modifier cette valeur dans la configuration de serveur si vous le souhaitez toujours." },
    };
    
}
