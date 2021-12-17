//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPKCS12_de.java 3     4/06/18 1:35p Heller $ 
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
public class ResourceBundleImportKeyPKCS12_de extends MecResourceBundle {

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
        {"button.cancel", "Abbruch"},
        {"button.browse", "Durchsuchen"},
        {"keystore.contains.nokeys", "Diese Keystore beinhaltet keine privaten Schl�ssel."},
        {"label.importkey", "Import Keystore Datei (PKCS#12):"},
        {"label.keypass", "Passwort des Keystores:"},
        {"title", "Schl�ssel aus Keystore importieren (PKCS#12 Format)"},
        {"filechooser.key.import", "Bitte w�hlen Sie eine PKCS#12 Keystore Datei f�r den Import"},
        {"multiple.keys.message", "Bitte w�hlen Sie den zu importierenden Schl�ssel"},
        {"multiple.keys.title", "Mehrere Schl�ssel enthalten"},
        {"key.import.success.message", "Der Schl�ssel wurde erfolgreich importiert."},
        {"key.import.success.title", "Erfolg"},
        {"key.import.error.message", "Es trat ein Fehler w�hrend des Importprozesses auf.\n{0}"},
        {"key.import.error.title", "Fehler"},};

}
