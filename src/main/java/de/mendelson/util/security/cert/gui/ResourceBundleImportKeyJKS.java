//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyJKS.java 3     22.09.21 17:45 Heller $ 
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
public class ResourceBundleImportKeyJKS extends MecResourceBundle {

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
        {"button.cancel", "Cancel"},
        {"button.browse", "Browse"},
        {"keystore.contains.nokeys", "This keystore does not contain private keys."},
        {"label.importkey", "Import file:"},
        {"label.importkey.hint", "Keystore file in JKS format"},
        {"label.keypass", "Password:"},
        {"label.keypass.hint", "Keystore/key password"},
        {"title", "Import keys from keystore(JKS format)"},
        {"filechooser.key.import", "Please select the JKS keystore file for the import"},
        {"multiple.keys.message", "Please select the key to import"},
        {"multiple.keys.title", "Keystore contains multiple keys"},
        {"key.import.success.message", "The key has been imported successfully."},
        {"key.import.success.title", "Success"},
        {"key.import.error.message", "There occured an error during the import process.\n{0}"},
        {"key.import.error.title", "Error"},
        {"enter.keypassword", "Enter key password for \"{0}\""},};

}
