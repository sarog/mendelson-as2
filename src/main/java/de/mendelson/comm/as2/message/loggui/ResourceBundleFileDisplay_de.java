//$Header: /as2/de/mendelson/comm/as2/message/loggui/ResourceBundleFileDisplay_de.java 5     4/06/18 1:35p Heller $
package de.mendelson.comm.as2.message.loggui;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class ResourceBundleFileDisplay_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        
        {"no.file", "** KEINE DATEN VERFÜGBAR **" },
        {"file.notfound", "** DATEI {0} IST NICHT LÄNGER VERFÜGBAR **" },
        {"file.tolarge", "** {0}: DATEN DIESER GRÖSSE SIND NICHT ANZEIGBAR **" },
    };
    
}