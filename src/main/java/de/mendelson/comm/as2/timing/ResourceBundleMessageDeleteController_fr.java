//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleMessageDeleteController_fr.java 6     18.02.19 11:09 Heller $
package de.mendelson.comm.as2.timing;
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
public class ResourceBundleMessageDeleteController_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"autodelete", "{0}: Ce message est plus vieux que {1} {2} et a �t� supprim� par le processus de maintenance du syst�me." },    
        {"transaction.deleted.user", "Transactions supprim�es par l''interaction de l''utilisateur" },
        {"transaction.deleted.system", "Transactions supprim�es par le processus de gestion du syst�me" },
        {"transaction.deleted.transactiondate", "Date de la transaction: {0}" },
        {"transaction.delete.setting.olderthan", "Le processus est configur� pour supprimer les transactions de statut vert plus anciennes que {0}." },
        {"delete.ok", "SUPPRIMER AVEC SUCCES" },
        {"delete.failed", "SUPPRIMER ECHOUE" }, 
        {"delete.skipped", "SUPPRIMER SAUTE" },  
    };
    
}
