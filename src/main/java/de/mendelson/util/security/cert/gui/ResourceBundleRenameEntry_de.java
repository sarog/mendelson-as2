//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleRenameEntry_de.java 3     1.07.19 12:05 Heller $ 
package de.mendelson.util.security.cert.gui;

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
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class ResourceBundleRenameEntry_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"label.newalias", "Neuer Alias:"},
        {"label.keypairpass", "Schl�ssel Password:"},
        {"title", "Bestehenden Eintrag ({0}) umbenennen"},
        {"alias.exists.title", "Umbennen des Alias schlug fehl" },
        {"alias.exists.message", "Der Alias \"{0}\" exisitert bereits im unterliegenden Keystore." },
    };

}
