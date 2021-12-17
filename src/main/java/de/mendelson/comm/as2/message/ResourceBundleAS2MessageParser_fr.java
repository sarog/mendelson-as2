//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessageParser_fr.java 30    11.09.19 14:46 Heller $
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
 * @author E.Pailleau
 * @version $Revision: 30 $
 */
public class ResourceBundleAS2MessageParser_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"mdn.incoming", "La transmission entrante est un MDN." },
        {"mdn.answerto", "Le MDN \"{1}\" est la r�ponse au message AS2 \"{1}\"." },
        {"mdn.state", "L''�tat du MDN est [{0}]." },
        {"mdn.details", "D�tails du MDN re�u ({0}): \"{1}\"" },
        {"msg.incoming", "La transmission entrante est un message AS2 [{0}], taille du message brut: {1}." },
        {"msg.incoming.identproblem", "La transmission entrante est un message AS2. Il n''a pas �t� trait�e en raison d''un probl�me d''identification de partenaire." },   
        {"mdn.signed", "Le MDN est sign� ({0})." },
        {"mdn.unsigned.error", "Le MDN n''est pas sign�. La configuration stipule que le MDN du partenaire \"{0}\" doit �tre sign�." },
        {"mdn.signed.error", "Le MDN est sign�. La configuration stipule que le MDN du partenaire \"{0}\" ne doit pas �tre sign�." },
        {"msg.signed", "Le message AS2 est sign�." },
        {"msg.encrypted", "Le message AS2 est crypt�." },
        {"msg.notencrypted", "Le message AS2 n''est pas crypt�." },
        {"msg.notsigned", "Le message AS2 n''est pas sign�." },
        {"mdn.notsigned", "Le MDN n''est pas sign�." },
        {"message.signature.ok", "La signature num�rique du message AS2 a �t� v�rifi�e avec succ�s." },
        {"mdn.signature.ok", "La signature num�rique du MDN a �t� v�rifi�e avec succ�s." },
        {"mdn.signature.failure", "V�rification de signature digitale du MDN �chou�e - {0}" },
        {"message.signature.failure", "V�rification de signature digitale du message AS2 �chou�e - {0}" },
        {"message.signature.using.alias", "Utilisation du certificat \"{0}\" pour v�rifier la signature du message AS2." },
        {"mdn.signature.using.alias", "Utilisation du certificat \"{0}\" pour v�rifier la signature du MDN." },
        {"decryption.done.alias", "Les donn�es du message AS2 ont �t� d�crypt�es avec la clef \"{0}\", l''algorithme de chiffrement est \"{1}\", l''algorithme de chiffrement cle est \"{2}\"." },
        {"mdn.unexpected.messageid", "Le MDN r�f�rence un message AS2 avec l''identifiant \"{0}\" qui est inconnu." },
        {"mdn.unexpected.state", "Le MDN r�f�rence le message AS2 avec l''identification \"{0}\", cela n''attend pas un MDN" },
        {"data.compressed.expanded", "Le contenu compress� a vu sa taille passer de {0} � {1}." },
        {"found.attachments", "Trouv� {0} contenus en pi�ces attach�es dans le message." },
        {"decryption.inforequired", "Afin de d�crypter les donn�es une clef avec les param�tres suivants est requise:\n{0}" },
        {"decryption.infoassigned", "Une clef avec les param�tres suivants est utilis� pour d�crypter les donn�es (alias \"{0}\"):\n{1}" },
        {"signature.analyzed.digest", "L''�metteur a utilis� l''algorithme \"{0}\" pour signer le message." },
        {"signature.analyzed.digest.failed", "Le syst�me n''a pas pu trouver l''algorithme de signature du message AS2 entrant." },
        {"filename.extraction.error", "Extraire noms de fichier originaux n''est pas possible: \"{0}\", ignor�." },
        {"contentmic.match", "Le Message Integrity Code (MIC) assortit le message AS2 envoy�." },
        {"contentmic.failure", "Le Message Integrity Code (MIC) n'assortit pas le message AS2 envoy� (requis: {0}, re�u: {1})." },
        {"found.cem", "Le message re�u est est un message d'�change de certificat (CEM)." },
        {"data.unable.to.process.content.transfer.encoding", "Les donn�es ont �t� re�ues qui n''ont pas pu �tre trait�es. Le codage de transfert de contenu \"{0}\" est inconnue."},
        {"original.filename.found", "Le nom de fichier original a �t� d�fini par l''exp�diteur comme \"{0}\"." },
        {"original.filename.undefined", "Le nom de fichier original n''a pas �t� transmis par le canal de message." },
        {"data.not.compressed", "Les donn�es AS2 re�ues sont non compress�es." },
    };
    
}
