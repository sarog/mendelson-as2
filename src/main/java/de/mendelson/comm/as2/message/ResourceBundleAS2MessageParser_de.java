//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessageParser_de.java 44    20.09.19 16:37 Heller $
package de.mendelson.comm.as2.message;

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
 * @version $Revision: 44 $
 */
public class ResourceBundleAS2MessageParser_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"mdn.incoming", "Eingegangene �bertragung ist eine Empfangsbest�tigung (MDN)."},
        {"mdn.answerto", "Die eingegangene Empfangsbest�tigung (MDN) mit der Nachrichtennummer \"{0}\" ist die Antwort auf die ausgegangene AS2 Nachricht \"{1}\"."},
        {"mdn.state", "Status der eingegangenen Empfangsbest�tigung (MDN) ist [{0}]."},
        {"mdn.details", "Details der eingegangenen Empfangsbest�tigung (MDN) von {0}: \"{1}\""},
        {"msg.incoming", "Eingehende �bertragung ist eine AS2 Nachricht [{0}], Rohdatengr�sse: {1}"},
        {"msg.incoming.identproblem", "Eingehende �bertragung ist eine AS2 Nachricht. Sie wurde nicht verarbeitet, weil es ein Problem mit der Partneridentifikation gab." },   
        {"mdn.signed", "Empfangsbest�tigung (MDN) ist digital signiert ({0})."},
        {"mdn.unsigned.error", "Eingegangene Empfangsbest�tigung (MDN) ist entgegen der Partnerkonfiguration \"{0}\" NICHT digital signiert."},
        {"mdn.signed.error", "Eingegangene Empfangsbest�tigung (MDN) ist entgegen der Partnerkonfiguration \"{0}\" digital signiert."},
        {"msg.signed", "Eingegangene AS2 Nachricht ist digital signiert."},
        {"msg.encrypted", "Eingegangene AS2 Nachricht ist verschl�sselt."},
        {"msg.notencrypted", "Eingegangene AS2 Nachricht ist nicht verschl�sselt."},
        {"msg.notsigned", "Eingegangene AS2 Nachricht ist nicht digital signiert."},
        {"mdn.notsigned", "Eingegangene Empfangsbest�tigung (MDN) ist nicht digital signiert."},
        {"message.signature.ok", "Digitale Signatur der eingegangenen AS2 Nachricht wurde erfolgreich �berpr�ft."},
        {"mdn.signature.ok", "Digitale Signatur der eingegangenen MDN wurde erfolgreich �berpr�ft."},
        {"message.signature.failure", "�berpr�fung der digitalen Signatur der eingegangenen AS2 Nachricht schlug fehl - {0}"},
        {"mdn.signature.failure", "�berpr�fung der digitalen Signatur der eingegangenen MDN schlug fehl - {0}"},
        {"message.signature.using.alias", "Benutze das Zertifikat \"{0}\" zum �berpr�fen der digitalen Signatur der eingegangenen AS2 Nachricht."},
        {"mdn.signature.using.alias", "Benutze das Zertifikat \"{0}\" zum �berpr�fen der digitalen Signatur der eingegangenen MDN."},
        {"decryption.done.alias", "Die Daten der eingegangenen AS2 Nachricht wurden mit Hilfe des Schl�ssels \"{0}\" entschl�sselt, der Verschl�sselungsalgorithmus war \"{1}\", der Schl�sselverschl�sselungsalgorithmus war \"{2}\"."},
        {"mdn.unexpected.messageid", "Die eingegangene Empfangsbest�tigung (MDN) referenziert eine AS2 Nachricht der Referenznummer \"{0}\", die nicht existert."},
        {"mdn.unexpected.messageid", "Die eingegangene Empfangsbest�tigung (MDN) referenziert die AS2 Nachricht der Referenznummer \"{0}\", die keine MDN erwartet."},
        {"data.compressed.expanded", "Die komprimierten Nutzdaten der eingegangenen AS2 Nachricht wurden von {0} auf {1} expandiert."},
        {"found.attachments", "Es wurden {0} Anh�nge mit Nutzdaten in der AS2 Nachricht gefunden."},
        {"decryption.inforequired", "Zum Entschl�sseln der eingegangenen AS2 Nachricht ist ein Schl�ssel mit folgenden Parametern notwendig:\n{0}"},
        {"decryption.infoassigned", "Zum Entschl�sseln der eingegangenen AS2 Nachricht wurde ein Schl�ssel mit folgenden Parametern benutzt (Alias \"{0}\"):\n{1}"},
        {"signature.analyzed.digest", "F�r die digitale Signatur wurde vom Sender der Algorithmus \"{0}\" verwendet."},
        {"signature.analyzed.digest.failed", "Das System konnte den Signaturalgorithmus der eingehenden AS2 Nachricht nicht herausfinden." },
        {"filename.extraction.error", "Extrahieren des Originaldateinamens der eingegangenen AS2 Nachricht ist nicht m�glich: \"{0}\", wird ignoriert."},
        {"contentmic.match", "Der Message Integrity Code (MIC) stimmt mit der gesandten AS2 Nachricht �berein."},
        {"contentmic.failure", "Der Message Integrity Code (MIC) stimmt nicht mit der gesandten AS2 Nachricht �berein (erwartet: {0}, erhalten: {1})."},
        {"found.cem", "Die eingegangene Nachricht ist eine Anfrage f�r einen Zertifikataustausch (CEM)."},
        {"data.unable.to.process.content.transfer.encoding", "Es sind Daten empfangen worden, die nicht verarbeitet werden konnten, weil sie Fehler enthalten. Das Content Transfer Encoding \"{0}\" ist unbekannt."},
        {"original.filename.found", "Der originale Dateiname wurde vom Sender als \"{0}\" �bertragen." },
        {"original.filename.undefined", "Der Originaldateiname wurde nicht �bertragen." },
        {"data.not.compressed", "Die eingegangenen AS2 Daten sind unkomprimiert." },
    };
}
