//$Header: /as2/de/mendelson/comm/as2/message/loggui/ResourceBundleMessageDetails_de.java 23    5.03.20 17:02 Heller $
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
 * @version $Revision: 23 $
 */
public class ResourceBundleMessageDetails_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        
        {"title", "Nachrichtendetails" },
        {"title.cem", "Nachrichtendetails des Zertifikataustausch (CEM)" },
        {"label.transmissiongraph", "�bertragung:" },
        {"label.transactionstate", "Status:" },
        {"transactionstate.ok.send", "<HTML>Die {0} Nachricht wurde erfolgreich zum Partner \"{1}\" gesendet - er hat eine entsprechende Best�tigung geschickt.</HTML>"},
        {"transactionstate.ok.receive", "<HTML>Die {0} Nachricht wurde erfolgreich vom Partner \"{1}\" empfangen. Eine entsprechende Best�tigung wurde an den Partner verschickt.</HTML>"},
        {"transactionstate.ok.details", "<HTML>Die Daten wurden �bertragen und die Transaktion wurde erfolgreich abgeschlossen</HTML>" },
        {"transactionstate.error.unknown", "Ein unbekannter Fehler trat auf." },
        {"transactionstate.error.out", "<HTML>Sie haben die {0} Nachricht erfolgreich an Ihren Partner \"{1}\" �bermittelt - er war aber nicht in der Lage, sie zu verarbeiten und antwortete mit dem Fehler [{2}]</HTML>" },
        {"transactionstate.error.in", "<HTML>Sie empfingen die {0} Nachricht erfolgreich von Ihrem Partner \"{1}\" - Ihr System war aber nicht in der Lage, sie zu verarbeiten und antwortete mit dem Fehler [{2}]</HTML>" },
        {"transactionstate.error.unknown-trading-partner", "<HTML>Sie und Ihr Partner haben unterschiedliche AS2 Kennungen f�r die beiden Partner der �bertragung in der Konfiguration. Die folgenden Kennungen wurden verwendet: \"{0}\" (Nachrichtensender), \"{1}\" (Nachrichtenempf�nger)</HTML>" },
        {"transactionstate.error.authentication-failed", "<HTML>Der Nachrichtenempf�nger konnte die Signatur des Senders in den Daten nicht erfolgreich pr�fen. Dies ist meistens ein Konfigurationsproblem, da Sender und Empf�nger hier das gleiche Zertifikat verwenden m�ssen. Bitte sehen Sie sich auch die MDN Details im Protokoll an - dieses k�nnte weitere Informationen enthalten.</HTML>" },
        {"transactionstate.error.decompression-failed", "<HTML>Der Nachrichtenempf�nger konnte die empfangene Nachricht nicht dekomprimieren</HTML>" },
        {"transactionstate.error.insufficient-message-security", "<HTML>Der Nachrichtenempf�nger erwartete einen h�heren Sicherheitslevel f�r die empfangenen Daten (zum Beispiel verschl�sselte Daten anstelle von unverschl�sselten)</HTML>" },
        {"transactionstate.error.unexpected-processing-error", "<HTML>Dies ist eine sehr generische Fehlermeldung. Aus unbekanntem Grund konnte der Empf�nger die Nachricht nicht verarbeiten.</HTML>" },
        {"transactionstate.error.decryption-failed", "<HTML>Der Nachrichtenempf�nger konnte die Nachricht nicht entschl�sseln. Meistens ist das ein Konfigurationsproblem, verwendet der Sender das richtige Zertifikat zum Verschl�sseln?</HTML>" },
        {"transactionstate.error.connectionrefused", "<HTML>Sie haben versucht, das Partnersystem zu erreichen. Entweder schlug das fehl oder Ihr Partner hat nicht innerhalb der definierten Zeit mit einer Best�tigung geantwortet.</HTML>" },
        {"transactionstate.error.connectionrefused.details", "<HTML>Dies k�nnte ein Infrastrukturproblem sein, Ihr Partnersystem l�uft gar nicht oder Sie haben die falsche Empfangs-URL in der Konfiguration eingegeben? Wenn die Daten �bermittelt wurden und Ihr Partner sie nicht best�tigt hat, haben Sie eventuell das Zeitfenster f�r die Best�tigung zu klein gew�hlt?</HTML>" },
        {"transactionstate.error.asyncmdnsend", "<HTML>Eine Nachricht mit einer asynchronen MDN-Anforderung wurde empfangen und erfolgreich verarbeitet, aber Ihr System konnte die asynchrone MDN nicht zur�cksenden oder sie wurde vom Partnersystem nicht akzeptiert.</HTML>" },
        {"transactionstate.error.asyncmdnsend.details", "<HTML>Der AS2-Message-Sender �bermittelt die URL, an die er die MDN zur�cksenden soll - entweder ist dieses System nicht erreichbar (Infrastrukturproblem oder das Partnersystem ist ausgefallen?) oder das Partnersystem hat die asynchrone MDN nicht akzeptiert und antwortete mit einem HTTP 400.</HTML>" },
        {"transactionstate.pending", "Diese Transaktion ist im Wartezustand." },
        {"transactiondetails.outbound", "Dies ist eine ausgehende Verbindung, Sie versenden Daten an den Partner \"{0}\"." },
        {"transactiondetails.inbound", "Dies ist eine eingehende Verbindung, Sie empfangen Daten vom Partner \"{0}\"." },
        {"transactiondetails.outbound.sync", " Sie empfangen die Best�tigung direkt als Antwort auf dem R�ckkanal der ausgehenden Verbindung (synchrone MDN)." },
        {"transactiondetails.outbound.async", " F�r die Best�tigung baut Ihr Partner eine neue Verbindung zu Ihnen auf (asynchrone MDN)." },
        {"transactiondetails.inbound.sync", " Sie senden die Best�tigung direkt als Antwort auf dem R�ckkanal der eingehenden Verbindung (synchrone MDN)." },
        {"transactiondetails.inbound.async", " Sie senden die Best�tigung, indem Sie eine neue Verbindung zum Partner aufbauen (asynchrone MDN)." },        
        {"button.ok", "Ok" },
        {"header.timestamp", "Datum" },
        {"header.messageid", "Referenznummer" },
        {"message.raw.decrypted", "�bertragungsdaten (unverschl�sselt)" },         
        {"message.header", "Kopfdaten" },
        {"message.payload", "Nutzdaten" },
        {"message.payload.multiple", "Nutzdaten ({0})" },
        {"tab.log", "Log dieser Nachrichteninstanz" },
        {"header.encryption", "Verschl�sselung" },
        {"header.signature", "Digitale Signatur" },
        {"header.senderhost", "Sender" },
        {"header.useragent", "AS2 Server" },
    };
    
}