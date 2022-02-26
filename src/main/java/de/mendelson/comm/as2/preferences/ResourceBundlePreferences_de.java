//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_de.java 55    19.11.21 10:35 Heller $
package de.mendelson.comm.as2.preferences;

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
 * @version $Revision: 55 $
 */
public class ResourceBundlePreferences_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //preferences localized
        {PreferencesAS2.DIR_MSG, "Nachrichtenverzeichnis"},
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"button.modify", "Bearbeiten"},
        {"button.browse", "Durchsuchen"},
        {"filechooser.selectdir", "Bitte wählen Sie das zu setzene Verzeichnis"},
        {"title", "Einstellungen"},
        {"tab.language", "Sprache"},
        {"tab.dir", "Verzeichnisse"},
        {"tab.security", "Sicherheit"},
        {"tab.proxy", "Proxy"},
        {"tab.misc", "Allgemein"},
        {"tab.maintenance", "Systempflege"},
        {"tab.notification", "Benachrichtigungen"},
        {"tab.interface", "Module"},
        {"tab.log", "Protokoll"},
        {"header.dirname", "Typ"},
        {"header.dirvalue", "Verzeichnis"},
        {"label.language", "Sprache" },
        {"label.country", "Land/Region" },
        {"label.keystore.https.pass", "Keystore Passwort (zum Senden via Https):"},
        {"label.keystore.pass", "Keystore Password (Verschlüsselung/digitale Signatur):"},
        {"label.keystore.https", "Keystore (zum Senden via Https):"},
        {"label.keystore.encryptionsign", "Keystore( Verschl., Signatur):"},
        {"label.proxy.url", "Proxy URL:"},
        {"label.proxy.url.hint", "Proxy IP oder Domain"},
        {"label.proxy.port.hint", "Port"},
        {"label.proxy.user", "Benutzer:"},
        {"label.proxy.user.hint", "Proxy Login Benutzer"},
        {"label.proxy.pass", "Passwort:"},
        {"label.proxy.pass.hint", "Proxy Login Passwort"},
        {"label.proxy.use", "HTTP Proxy für ausgehende HTTP/HTTPs Verbindungen benutzen"},
        {"label.proxy.useauthentification", "Authentifizierung für Proxy benutzen"},
        {"filechooser.keystore", "Bitte wählen Sie die Keystore Datei (JKS Format)."},
        {"label.days", "Tage"},
        {"label.deletemsgolderthan", "Automatisches Löschen von Nachrichten, die älter sind als"},
        {"label.deletemsglog", "Automatisches Löschen von Dateien und Logeinträgen protokollieren"},
        {"label.deletestatsolderthan", "Automatisches Löschen von Statistikdaten, die älter sind als"},
        {"label.deletelogdirolderthan", "Automatisches Löschen von Protokolldaten, die älter sind als"},
        {"label.asyncmdn.timeout", "Maximale Wartezeit auf asynchrone MDNs:"},
        {"label.httpsend.timeout", "HTTP(s) Sende-Timeout:"},
        {"label.min", "min"},
        {"receipt.subdir", "Unterverzeichnisse pro Partner für Nachrichtenempfang anlegen"},
        //notification
        {"checkbox.notifycertexpire", "Vor dem Auslaufen von Zertifikaten"},
        {"checkbox.notifytransactionerror", "Nach Fehlern in Transaktionen"},
        {"checkbox.notifycem", "Ereignisse beim Zertifikataustausch (CEM)"},
        {"checkbox.notifyfailure", "Nach Systemproblemen"},
        {"checkbox.notifyresend", "Nach abgewiesenen Resends"},
        {"checkbox.notifyconnectionproblem", "Bei Verbindungsproblemen"},
        {"checkbox.notifypostprocessing", "Probleme bei der Nachbearbeitung"},
        {"button.testmail", "Sende Test Mail"},
        {"label.mailhost", "Mailserver (SMTP):"},
        {"label.mailhost.hint", "IP oder Domain des Servers"},
        {"label.mailport", "Port:"},
        {"label.mailport.hint", "SMTP Port"},
        {"label.mailaccount", "Mailserver Account:"},
        {"label.mailpass", "Mailserver Passwort:"},
        {"label.notificationmail", "Benachrichtigungsempfänger eMail:"},
        {"label.replyto", "Replyto Addresse:"},
        {"label.smtpauthentication", "SMTP Authentifizierung benutzen"},
        {"label.smtpauthentication.user", "Benutzer:"},
        {"label.smtpauthentication.user.hint", "SMTP Server Benutzername"},
        {"label.smtpauthentication.pass", "Passwort:"},
        {"label.smtpauthentication.pass.hint", "SMTP Server Passwort"},
        {"label.security", "Verbindungssicherheit:"},
        {"testmail.message.success", "Eine Test-eMail wurde erfolgreich versandt."},
        {"testmail.message.error", "Fehler beim Senden der Test-eMail:\n{0}"},
        {"testmail.title", "Senden einer Test-eMail"},
        {"testmail", "Test Mail"},
        //interface
        {"label.showhttpheader", "Anzeige der HTTP Header Konfiguration bei den Partnereinstellungen"},
        {"label.showquota", "Anzeige der Benachrichtigungskonfiguration bei den Partnereinstellungen"},
        {"label.cem", "Zertifikataustausch erlauben (CEM)"},
        {"label.outboundstatusfiles", "Statusdateien für ausgehende Transaktionen schreiben"},
        {"info.restart.client", "Sie müssen den Client neu starten, damit diese Änderungen gültig werden!"},
        {"remotedir.select", "Verzeichnis auf dem Server wählen"},
        //retry
        {"label.retry.max", "Max Anzahl der Versuche zum Verbindungsaufbau"},
        {"label.retry.waittime", "Wartezeit zwischen Verbindungsaufbauversuchen"},
        {"label.sec", "s"},
        {"keystore.hint", "<HTML><strong>Achtung:</strong><br>Bitte ändern Sie diese Parameter nur, wenn Sie externe Keystores "
            + "einbinden möchten oder Sie über ein externes Programm die Passwörter der unterliegenden Keystore Dateien "
            + "modifiziert haben (was nicht empfehlenswert ist!). Wenn Sie diese Einstellungen ändern, werden nicht automatisch "
            + "die Pfade der unterliegenden Keystores oder deren Passwörter angepasst. Mit veränderten Passwörtern kann es zu "
            + "Problemen beim Update kommen.</HTML>"},
        {"maintenancemultiplier.day", "Tag(e)"},
        {"maintenancemultiplier.hour", "Stunde(n)"},
        {"maintenancemultiplier.minute", "Minute(n)"},
        {"label.logpollprocess", "Informationen zum Pollprozes im Protokoll anzeigen (viele Einträge - nicht im Produktivbetrieb verwenden)"},
        {"label.max.outboundconnections", "Max ausgehende parallele Verbindungen"},
        {"event.preferences.modified.subject", "Der Wert {0} der Servereinstellungen wurde modifiziert"},
        {"event.preferences.modified.body", "Alter Wert: {0}\nNeuer Wert: {1}"},
        {"event.notificationdata.modified.subject", "Die Einstellungen zur Benachrichtigung wurden verändert"},
        {"event.notificationdata.modified.body", "Die Benachrichtigungsdaten wurden von\n\n{0}\n\nnach\n\n{1}\n\n verändert." },
        {"label.maxmailspermin", "Max Anzahl von Benachrichtigungen/min:"},
        {"systemmaintenance.hint.deleteoldtransactions", "<HTML>Dies legt den Zeitrahmen fest, in dem die Transaktionen und die zugehörigen Daten (z.B. temporäre Dateien) im System verbleiben und in der Transaktionsübersicht angezeigt werden sollen.<br>Diese Einstellungen betreffen <strong>nicht</strong> Ihre empfangenen Daten/Dateien, diese bleiben unberührt.<br>Selbst für gelöschte Transaktionen ist das Transaktionsprotokoll über die Funktionalität der Logsuche weiterhin verfügbar.</HTML>" },
        {"systemmaintenance.hint.deleteoldstatistic", "<HTML>Das System sammelt Kompatibilitätsdaten der Partnersysteme und kann diese als Statistik darstellen. Dies legt den Zeitrahmen fest, in dem diese Daten vorgehalten werden.</HTML>" },
        {"systemmaintenance.hint.deleteoldlogdirs", "<HTML>Auch wenn alte Transaktionen gelöscht wurden, lassen sich die Vorgänge noch über bestehende Protokolldateien nachvollziehen. Diese Einstellung löscht diese Protokolldateien und auch alle Dateien für Systemereignisse, die in den gleichen Zeitraum fallen.</HTML>" },
        {"label.colorblindness", "Unterstützung für Farbblindheit" },
        {"warning.clientrestart.required", "Die Client Einstellungen wurden geändert - bitte starten Sie den Client neu, damit sie gültig werden" },
    };

}
