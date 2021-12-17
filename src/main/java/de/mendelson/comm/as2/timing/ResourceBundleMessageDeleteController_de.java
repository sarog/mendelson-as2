//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleMessageDeleteController_de.java 7     18.02.19 11:09 Heller $
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
 * @version $Revision: 7 $
 */
public class ResourceBundleMessageDeleteController_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"autodelete", "{0}: Diese Nachricht ist �lter als {1} {2} und wurde automatisch vom Systempflegeprozess gel�scht." },    
        {"transaction.deleted.user", "Transaktionen gel�scht durch Benutzerinteraktion" },
        {"transaction.deleted.system", "Transaktionen gel�scht durch Systempflegeprozess" },
        {"transaction.deleted.transactiondate", "Transaktionsdatum: {0}" },
        {"transaction.delete.setting.olderthan", "Der Prozess ist konfiguriert, Transaktionen mit gr�nem Status zu l�schen, die �lter sind als {0}." },
        {"delete.ok", "L�SCHEN ERFOLGREICH" },
        {"delete.failed", "L�SCHEN FEHLGESCHLAGEN" }, 
        {"delete.skipped", "L�SCHEN �BERSPRUNGEN" },  
    };
    
}