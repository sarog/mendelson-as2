//$Header: /as2/de/mendelson/comm/as2/message/loggui/ResourceBundleMessageDetails_fr.java 14    5.03.20 17:02 Heller $
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
 * @author E.Pailleau
 * @version $Revision: 14 $
 */
public class ResourceBundleMessageDetails_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        
        {"title", "D�tails du message" },
        {"title.cem", "D�tails du message de l'�change de certificat (CEM)" },
        {"label.transmissiongraph", "Conf�rer:" },
        {"label.transactionstate", "Statut:" },
        {"transactionstate.ok.send", "<HTML>Le message {0} a �t� envoy� avec succ�s au partenaire \"{1}\" - il a envoy� une confirmation correspondante.</HTML>"},
        {"transactionstate.ok.receive", "<HTML>Le message {0} a �t� re�u avec succ�s au partenaire \"{1}\". Une confirmation correspondante a �t� envoy�e.</HTML>"},
        {"transactionstate.ok.details", "<HTML>Les donn�es ont �t� transf�r�es et la transaction a �t� effectu�e avec succ�s.</HTML>" },
        {"transactionstate.error.unknown", "Une erreur inconnue s'est produite." },
        {"transactionstate.error.out", "<HTML>Vous avez transmis avec succ�s le message {0} � votre partenaire \"{1}\" - mais il n''a pas �t� en mesure de le traiter et a r�pondu par l''erreur [{2}]</HTML>." },
        {"transactionstate.error.in", "<HTML>Vous avez re�u avec succ�s le message {0} de votre partenaire \"{1}\" - mais votre syst�me n''a pas �t� en mesure de le traiter et a r�pondu avec l''erreur [{2}]</HTML>." },
        {"transactionstate.error.unknown-trading-partner", "<HTML>Vous et votre partenaire avez des identificateurs AS2 diff�rents pour les deux partenaires de transmission dans la configuration. Les identificateurs suivants ont �t� utilis�s : \"{0}\" (lecteur de nouvelles), \"{1}\" (destinataire du message)</HTML>" },
        {"transactionstate.error.authentication-failed", "<HTML>Le destinataire du message n' a pas pu v�rifier avec succ�s la signature de l''exp�diteur dans les donn�es. C''est g�n�ralement un probl�me de configuration, puisque l''exp�diteur et le destinataire doivent utiliser le m�me certificat ici. Veuillez �galement consulter les d�tails du MDN qui se trouvent dans le journal - celui-ci peut contenir des informations suppl�mentaires.</HTML>" },
        {"transactionstate.error.decompression-failed", "<HTML>Le destinataire du message ne pouvait pas d�compresser le message re�u</HTML>" },
        {"transactionstate.error.insufficient-message-security", "<HTML>Le destinataire du message s''attendait � un niveau de s�curit� plus �lev� pour les donn�es re�ues (par exemple, donn�es chiffr�es au lieu de donn�es non chiffr�es)</HTML>" },
        {"transactionstate.error.unexpected-processing-error", "<HTML>Ceci est un message d''erreur tr�s g�n�rique. Pour une raison inconnue, le destinataire n''a pas pu traiter le message.</HTML>" },
        {"transactionstate.error.decryption-failed", "<HTML>Le destinataire du message n'a pas pu d�crypter le message. Habituellement, c''est un probl�me de configuration, est-ce que l''exp�diteur utilise le bon certificat de cryptage?</HTML>" },
        {"transactionstate.error.connectionrefused", "<HTML>Vous avez essay� d'atteindre le syst�me partenaire. Soit cela a �chou�, soit votre partenaire n''a pas r�pondu dans le d�lai imparti avec une confirmation.</HTML>" },
        {"transactionstate.error.connectionrefused.details", "<HTML>Cela peut �tre un probl�me d''infrastructure, votre syst�me partenaire n'est pas en cours d''ex�cution ou vous avez entr� la mauvaise URL dans la configuration? Si les donn�es ont �t� transf�r�es et que votre partenaire ne les a pas confirm�es, avez-vous choisi une fen�tre de confirmation trop petite?</HTML>" },
        {"transactionstate.error.asyncmdnsend", "<HTML>Un message contenant une demande de MDN asynchrone a �t� re�u et trait� avec succ�s, mais votre syst�me n''a pas pu renvoyer le MDN asynchrone ou il n' a pas �t� accept� par le syst�me partenaire.</HTML>" },
        {"transactionstate.error.asyncmdnsend.details", "<HTML>L''exp�diteur du message AS2 transmet l''URL � laquelle il doit renvoyer le MDN - soit ce syst�me n'est pas joignable (probl�me d''infrastructure ou syst�me partenaire a �chou�?) soit le syst�me partenaire n''a pas accept� le MDN asynchrone et a r�pondu par un HTTP 400.</HTML>" },
        {"transactionstate.pending", "Cette transaction est en attente." },
        {"transactiondetails.outbound", "Il s''agit d''une connexion sortante, vous envoyez des donn�es au partenaire \"{0}\"." },
        {"transactiondetails.inbound", "Il s''agit d'une connexion entrante, vous recevez les donn�es du partenaire \"{0}\"." },
        {"transactiondetails.outbound.sync", " Vous recevez la confirmation directement en r�ponse sur le canal arri�re de votre connexion sortante (MDN synchrone)." },
        {"transactiondetails.outbound.async", " Votre partenaire �tablit une nouvelle connexion avec vous pour confirmation (MDN asynchrone)." },
        {"transactiondetails.inbound.sync", " Vous envoyez la confirmation directement en r�ponse sur le canal arri�re de la connexion entrante (MDN synchrone)." },
        {"transactiondetails.inbound.async", " Vous envoyez la confirmation en �tablissant une nouvelle connexion au partenaire (MDN asynchrone)." },        
        {"button.ok", "Valider" },
        {"header.timestamp", "Date" },
        {"header.messageid", "R�f No" },
        {"message.raw.decrypted", "Donn�es brutes (non d�crypt�)" },
        {"message.header", "Ent�te message" },
        {"message.payload", "Contenu transf�r�" },
        {"message.payload.multiple", "Contenu ({0})" },
        {"tab.log", "Log de l''instance de ce message" },
        {"header.encryption", "Cryptage" },
        {"header.signature", "Signature" },
        {"header.senderhost", "Emetteur" },
        {"header.useragent", "Serveur AS2" },
    };
    
}
