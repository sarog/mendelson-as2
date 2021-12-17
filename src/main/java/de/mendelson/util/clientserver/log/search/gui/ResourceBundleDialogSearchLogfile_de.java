//$Header: /as2/de/mendelson/util/clientserver/log/search/gui/ResourceBundleDialogSearchLogfile_de.java 3     27.02.19 10:08 Heller $
package de.mendelson.util.clientserver.log.search.gui;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class ResourceBundleDialogSearchLogfile_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"title", "Protokolleintr�ge des Servers durchsuchen"},
        {"no.data.messageid", "**Es gibt keine Protokolldaten f�r die AS2 Nachrichtennummer \"{0}\" in dem gew�hlten Zeitraum. Bitte verwenden Sie als Suchzeichenkette die vollst�ndige Nachrichtennummer." },        
        {"no.data.mdnid", "**Es gibt keine Protokolldaten f�r die MDN Nummer \"{0}\" in dem gew�hlten Zeitraum. Bitte verwenden Sie als Suchzeichenkette die vollst�ndige MDN Nummer, die Sie dem Log einer �bertragung entnehmen k�nnen." },        
        {"no.data.uid", "**Es gibt keine Protokolldaten f�r die benutzerdefinierte Nummer \"{0}\" in dem gew�hlten Zeitraum. Bitte w�hlen Sie als Suchzeichenkette die vollst�ndige benutzerdefinierte Nummer, die Sie der �bertragung mitgegeben haben." },        
        {"label.startdate", "Start: " },
        {"label.enddate", "Ende: " },
        {"button.close", "Schliessen" },
        {"label.search", "<html><div style=\"text-align:center\">Protokoll<br>durchsuchen</div></html>" },
        {"label.info", "<html>Bitte definieren Sie einen Zeitraum, geben eine vollst�ndige AS2 Nachrichtennummer oder die vollst�ndige Nummer einer MDN ein, um alle Protokolleintr�ge daf�r auf dem Server zu finden - dann dr�cken Sie bitte den Knopf \"Protokoll durchsuchen\". Die benutzerdefinierte Nummer k�nnen Sie f�r jede Transaktion definieren, wenn Sie die Daten �ber die Kommandozeile an den laufenden Server schicken.</html>" },
        {"textfield.preset", "mendelsonAS2@partnerAS2" },
        {"label.messageid", "Nachrichtennummer" },
        {"label.mdnid", "MDN Nummer" },
        {"label.uid", "Benutzerdefinierte Nummer" },
        {"problem.serverside", "Es gab ein serverseitiges Problem beim Durchsuchen der Protokolldateien: [{0}] {1}" },
    };
}
