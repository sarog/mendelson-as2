//$Header: /as2/de/mendelson/comm/as2/client/manualsend/ResourceBundleManualSend_de.java 5     13.09.19 12:28 Heller $ 
package de.mendelson.comm.as2.client.manualsend;

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
 * @version $Revision: 5 $
 */
public class ResourceBundleManualSend_de extends MecResourceBundle {

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
        {"button.browse", "Durchsuchen"},
        {"label.filename", "Datei senden"},
        {"label.testdata", "Testdaten senden"},
        {"label.partner", "Empfänger:"},
        {"label.localstation", "Sender:"},
        {"label.selectfile", "Bitte wählen Sie die zu versendene Datei"},
        {"title", "Manueller Dateiversand"},
        {"send.success", "Die Datei wurde erfolgreich an den Versandprozess übergeben."},
        {"send.failed", "Wegen eines Fehlers konnte die Datei nicht an den Versandprozess übergeben werden."},};

}
