//$Header: /as2/de/mendelson/comm/as2/client/ResourceBundleAS2Gui_de.java 51    21.08.20 17:59 Heller $
package de.mendelson.comm.as2.client;

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
 * @version $Revision: 51 $
 */
public class ResourceBundleAS2Gui_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"menu.file", "Datei"},
        {"menu.file.exit", "Beenden"},
        {"menu.file.partner", "Partner"},
        {"menu.file.datasheet", "Datenblatt f�r Anbindung"},        
        {"menu.file.certificates", "Zertifikate"},
        {"menu.file.certificate", "Zertifikate"},
        {"menu.file.certificate.signcrypt", "Sign/Verschl�sselung"},
        {"menu.file.certificate.ssl", "SSL/TLS"},
        {"menu.file.cem", "Verwaltung Zertifikataustausch (CEM)"},
        {"menu.file.cemsend", "Zertifikate mit Partnern tauschen (CEM)"},
        {"menu.file.preferences", "Einstellungen"},
        {"menu.file.send", "Datei an Partner versenden"},
        {"menu.file.resend", "Als neue Transaktion versenden"},
        {"menu.file.resend.multiple", "Als neue Transaktionen versenden"},
        {"menu.file.migrate.hsqldb", "Von HSQLDB migrieren"},
        {"menu.file.statistic", "Statistik"},
        {"menu.file.quota", "Kontingente"},
        {"menu.file.serverinfo", "HTTP Server Konfiguration anzeigen"},
        {"menu.file.systemevents", "Systemereignisse"},
        {"menu.file.searchinserverlog", "Serverprotokoll durchsuchen"},        
        {"menu.help", "Hilfe"},
        {"menu.help.about", "�ber"},
        {"menu.help.supportrequest", "Support Anfrage"},
        {"menu.help.shop", "mendelson Online Shop"},
        {"menu.help.helpsystem", "Hilfesystem"},
        {"menu.help.forum", "Forum"},
        {"details", "Nachrichtendetails"},
        {"filter.showfinished", "Fertige anzeigen"},
        {"filter.showpending", "Wartende anzeigen"},
        {"filter.showstopped", "Gestoppte anzeigen"},
        {"filter.none", "-- Keine --"},
        {"filter.partner", "Partnerbeschr�nkung:"},
        {"filter.localstation", "Beschr�nkung der lokalen Station:"},
        {"filter.direction", "Richtungsbeschr�nkung:"},
        {"filter.direction.inbound", "Eingehend"},
        {"filter.direction.outbound", "Ausgehend"},
        {"filter", "Filter"},
        {"filter.use", "Zeitliche Einschr�nkung" },
        {"filter.from", "Von:" },
        {"filter.to", "Bis:" },
        {"keyrefresh", "Zertifikate aktualisieren"},
        {"configurecolumns", "Spalten" },
        {"delete.msg", "L�schen"},
        {"dialog.msg.delete.message", "Wollen Sie die selektierten Nachrichten wirklich permanent l�schen?"},
        {"dialog.msg.delete.title", "L�schen von Nachrichten"},
        {"msg.delete.success.single", "{0} Nachricht wurde gel�scht" },
        {"msg.delete.success.multiple", "{0} Nachrichten wurden gel�scht" },
        {"stoprefresh.msg", "Aktualisierung an/aus"},
        {"welcome", "Willkommen, {0}"},
        {"fatal.error", "Fehler"},
        {"warning.refreshstopped", "Die Aktualisierung der Oberfl�che ist abgeschaltet."},
        {"tab.welcome", "News und Updates"},
        {"tab.transactions", "Transaktionen"},
        {"new.version", "Eine neue Version ist verf�gbar. Hier klicken, um sie herunterzuladen."},
        {"new.version.logentry.1", "Eine neue Version ist verf�gbar."},
        {"new.version.logentry.2", "Sie k�nnen Sie unter {0} herunterladen."}, 
        {"dbconnection.failed.message", "Es konnte keine Verbindung zum AS2 Datenbankserver hergestellt werden: {0}"},
        {"dbconnection.failed.title", "Keine Verbindung m�glich"},
        {"login.failed.client.incompatible.message", "Der Server meldet, dass dieser Client nicht die richtige Version hat.\nBitte verwenden Sie den zum Server passenden Client."},
        {"login.failed.client.incompatible.title", "Login wurde zur�ckgewiesen"},
        {"uploading.to.server", "�bertrage zum Server"},
        {"refresh.overview", "Aktualisiere Transaktionsliste"},
        {"dialog.resend.message", "Wollen Sie die selektierte Transaktion wirklich erneut senden?"},
        {"dialog.resend.message.multiple", "Wollen Sie die {0} selektierten Transaktionen wirklich erneut senden?"},
        {"dialog.resend.title", "Daten erneut senden"},
        {"logputput.disabled", "** Die Protokollausgabe wurde unterdr�ckt **"},
        {"logputput.enabled", "** Die Protokollausgabe wurde aktiviert **"},
        {"resend.failed.nopayload", "Erneuter Versand als neue Transaktion ist fehlgeschlagen: Die selektierte Transaktion {0} hat keine Nutzdaten." },
    };
}