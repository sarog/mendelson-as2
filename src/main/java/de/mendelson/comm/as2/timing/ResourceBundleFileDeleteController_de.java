//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleFileDeleteController_de.java 4     29.10.18 10:28 Heller $
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
 * @version $Revision: 4 $
 */
public class ResourceBundleFileDeleteController_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"autodelete", "{0}: Die Datei wurde automatisch vom Systempflegeprozess gelöscht." },
        {"delete.title", "Löschen von Dateien durch Systempflege" },
        {"delete.title.tempfiles", "Temporäre Dateien" },
        {"delete.title._rawincoming", "Eingegangene Dateien aus _rawincoming" },
        {"success", "ERFOLG" },
        {"failure", "FEHLER" },
        {"no.entries", "{0}: Keine Einträge gefunden" },
    };
    
}