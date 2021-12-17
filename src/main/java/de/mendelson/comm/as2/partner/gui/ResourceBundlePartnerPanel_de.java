//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerPanel_de.java 62    30.12.20 11:23 Heller $
package de.mendelson.comm.as2.partner.gui;
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
 * @version $Revision: 62 $
 */
public class ResourceBundlePartnerPanel_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Partnerkonfiguration" },
        {"label.name", "Name:" },
        {"label.id", "AS2 id:" },
        {"label.partnercomment", "Kommentar:" },
        {"label.url", "Empfangs-URL:" },
        {"label.mdnurl", "MDN URL:" },
        {"label.signalias.key", "Privater Schl�ssel (Digitale Signatur erstellen):" },
        {"label.cryptalias.key", "Privater Schl�ssel (Datenentschl�sselung):" },
        {"label.signalias.cert", "Partnerzertifikat (Digitale Signatur verifizieren):" },
        {"label.cryptalias.cert", "Partnerzertifikat (Datenverschl�sselung):" },
        {"label.signtype", "Digitale Signatur:" },
        {"label.encryptiontype", "Nachrichtenverschl�sselung:" },
        {"label.email", "Mail Adresse:" },
        {"label.localstation", "Lokale Station" },       
        {"label.compression", "Ausgehende Nachrichten komprimieren (ben�tigt AS2 1.1 Gegenstelle)" }, 
        {"label.usecommandonreceipt", "Nach Empfang:" },
        {"label.usecommandonsenderror", "Nach Versand (fehlerhaft):"},
        {"label.usecommandonsendsuccess", "Nach Versand (erfolgreich):"},
        {"label.keepfilenameonreceipt", "Original Dateiname beibehalten (Wenn der Sender diese Information zur Verf�gung stellt)"},
        {"label.address", "Adresse:" },
        {"label.contact", "Kontakt:" },        
        {"tab.misc", "Allgemein" },
        {"tab.security", "Sicherheit" },
        {"tab.send", "Versand" },
        {"tab.mdn", "MDN" },   
        {"tab.dirpoll", "Verzeichnis�berwachung" }, 
        {"tab.receipt", "Empfang" },  
        {"tab.httpauth", "HTTP Authentifizierung" },
        {"tab.httpheader", "HTTP Header"},
        {"tab.notification", "Benachrichtigung" },
        {"tab.events", "Nachbearbeitung" },
        {"tab.partnersystem", "Info" },
        {"label.subject", "Nutzdaten Subject:" },
        {"label.contenttype", "Nutzdaten Content Type:" },
        {"label.syncmdn", "Sychrone Empfangsbest�tigung (MDN) anfordern" },
        {"label.asyncmdn", "Asychrone Empfangsbest�tigung (MDN) anfordern" },
        {"label.signedmdn", "Signierte Empfangsbest�tigung (MDN) anfordern" },
        {"label.polldir", "�berwachtes Verzeichnis:" },
        {"label.pollinterval", "Abholintervall:" },
        {"label.pollignore", "Abholen ignorieren f�r:" },        
        {"label.maxpollfiles", "Maximale Dateianzahl pro Abholvorgang:"},
        {"label.usehttpauth", "Benutze HTTP Authentifizierung beim Senden von AS2 Nachrichten" },
        {"label.usehttpauth.user", "Benutzername:" },
        {"label.usehttpauth.pass", "Passwort:" },
        {"label.usehttpauth.asyncmdn", "Benutze HTTP Authentifizierung beim Senden von async MDN" },
        {"label.usehttpauth.asyncmdn.user", "Benutzername:" },
        {"label.usehttpauth.asyncmdn.pass", "Passwort:" },        
        {"hint.subject.replacement", "<HTML>$'{'filename} wird durch den Sendedateinamen ersetzt.<br>Dieser Wert wird im HTTP Header �bertragen, daf�r gelten Einschr�nkungen! Bitte verwenden Sie als Zeichenkodierung ISO-8859-1, nur druckbare Zeichen, keine Sonderzeichen. CR, LF und TAB werden ersetzt durch \"\\r\", \"\\n\" und \"\\t\".</HTML>"},
        {"hint.keepfilenameonreceipt", "Empfangene Dateinamen m�ssen eindeutig sein, wenn diese Option eingeschaltet ist!"},        
        {"label.notify.send", "Benachrichtigen, wenn das Sendekontingent folgenden Wert �bersteigt:" },
        {"label.notify.receive", "Benachrichtigen, wenn das Empfangskontingent folgenden Wert �bersteigt:" },
        {"label.notify.sendreceive", "Benachrichtigen, wenn das Sende/Empfangskontingent folgenden Wert �bersteigt:" },
        {"header.httpheaderkey", "Name" },
        {"header.httpheadervalue", "Wert" },
        {"httpheader.add", "Hinzuf�gen" },
        {"httpheader.delete", "Entfernen" },
        {"label.as2version", "AS2 Version:" },
        {"label.productname", "Produktname:" },
        {"label.features", "Funktionen:" },
        {"label.features.cem", "Zertifikataustausch �ber CEM" },
        {"label.features.ma", "Mehrere Anh�nge" },
        {"label.features.compression", "Datenkomprimierung" },
        {"partnerinfo", "Ihr Partner �bermittelt mit jeder AS2 Nachricht auch Informationen �ber die Funktionen seines AS2 Systems. Dies ist die Liste dieser Funktionen." },
        {"partnersystem.noinfo", "Keine Information verf�gbar - gab es schon eine Transaktion?" },
        {"label.httpversion", "HTTP Protokollversion:" },
        {"label.test.connection", "Verbindung pr�fen" },
        {"label.url.hint", "<HTML>Bitte geben Sie diese URL im Format <strong>PROTOKOLL://HOST:PORT/PFAD</strong> an, wobei das <strong>PROTOKOLL</strong> eines von \"http\" oder \"https\" sein mu�. <strong>HOST</strong> bezeichnet den AS2 Server Host Ihres Partners. <strong>PORT</strong> ist der Empfangsport Ihres Partners. Wird er nicht angegeben, wird der Wert \"80\" gesetzt. <strong>PFAD</strong> bezeichnet den Empfangspfad, zum Beispiel \"/as2/HttpReceiver\".</HTML>"},
        {"label.url.hint.mdn", "<HTML>Dies ist die URL, die Ihr Partner f�r die eingehende asynchrone MDN zu dieser lokalen Station verwenden wird.<br>Bitte geben Sie diese URL im Format <strong>PROTOKOLL://HOST:PORT/PFAD</strong> an.<br><strong>PROTOKOLL</strong> mu� eines von \"http\" oder \"https\" sein.<br><strong>HOST</strong> bezeichnet Ihren eigenen AS2 Server Host.<br><strong>PORT</strong> ist der Empfangsport Ihres AS2 Systems. Wird er nicht angegeben, wird der Wert \"80\" gesetzt.<br><strong>PFAD</strong> bezeichnet den Empfangspfad, zum Beispiel \"/as2/HttpReceiver\".</HTML>"},
        {"label.mdn.description", "<HTML>Die MDN (Message Delivery Notification) ist die Best�tigung f�r die AS2 Nachricht. Dieser Abschnitt definiert das Verhalten Ihres Partners f�r Ihre ausgehenden AS2-Nachrichten.</HTML>" },
        {"label.mdn.sync.description", "<HTML>Der Partner sendet die Best�tigung (MDN) auf dem R�ckkanal Ihrer ausgehenden Verbindung.</HTML>" },
        {"label.mdn.async.description", "<HTML>Der Partner baut eine neue Verbindung zu Ihrem System auf, um die Best�tigung f�r Ihre ausgehende Nachricht zu senden.</HTML>" },
        {"label.mdn.sign.description", "<HTML>Das AS2-Protokoll definiert nicht, wie man mit einer MDN umgeht, wenn die Signatur nicht verifiziert werden kann - mendelson AS2 zeigt in diesem Fall eine Warnung an.</HTML>" },
        {"label.algorithmidentifierprotection", "<HTML>\"Algorithm Identifier Protection Attribute\" in der Signatur verwenden (empfohlen), weitere Informationen unter RFC 6211</HTML>" },
        {"label.enabledirpoll", "Verzeichnis�berwachung f�r diesen Partner einschalten" },
        {"tooltip.button.editevent", "Ereignis bearbeiten" },
        {"tooltip.button.addevent", "Neues Ereignis erstellen" },
        {"label.httpauthentication.info", "<HTML>Bitte richten Sie hier die HTTP Basis-Zugangsauthentifizierung ein, wenn dies auf der Seite Ihres Partners aktiviert ist (definiert in RFC 7617). Auf nicht authentifizierte Anfragen (falsche Anmeldedaten usw.) sollte das System des entfernten Partners einen <strong>HTTP 401 Unauthorized</strong> Status zur�ckgeben.<br>Wenn die Verbindung zu Ihrem Partner TLS-Client-Authentifizierung (�ber Zertifikate) erfordert, ist hier keine Einstellung erforderlich. In diesem Fall importieren Sie bitte die Zertifikate des Partners �ber den TLS-Zertifikatsmanager - das System k�mmert sich dann um die TLS-Client-Authentifizierung.</HTML>" },
    };
    
}