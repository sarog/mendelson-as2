//$Header: /as2/de/mendelson/comm/as2/cem/ResourceBundleCEM_fr.java 9     7.12.18 11:54 Heller $
package de.mendelson.comm.as2.cem;
import de.mendelson.comm.as2.cem.messages.TrustResponse;
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
 * @version $Revision: 9 $
 */
public class ResourceBundleCEM_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {      
        {"trustrequest.rejected", "La r�ponse � la demande de fiducie re�ue a re�u le statut \"" + TrustResponse.STATUS_REJECTED_STR + "\"." },
        {"trustrequest.accepted", "La r�ponse � la demande de fiducie re�ue a re�u le statut \"" + TrustResponse.STATUS_ACCEPTED_STR + "\"." },
        {"trustrequest.working.on", "Traiter au Trust Request {0}." },
        {"trustrequest.certificates.found", "Nombre de certificats transf�r�s: {0}." },
        {"cem.validated.schema", "Le CEM entrant a �t� valid� avec succ�s." },
        {"cem.structure.info", "Nombre de demandes de confiance dans le CEM entrant: {0}" },
        {"transmitted.certificate.info", "Le certificat transmis a les caract�ristiques IssuerDN=\"{0}\" et num�ro de s�rie \"{1}\"." },
        {CEMReceiptController.KEYSTORE_TYPE_ENC_SIGN +".cert.already.imported", "Le certificat CEM soumis existe d�j� dans le keystore [enc/sign] (alias {0}), l''importation a �t� ignor�e."},
        {CEMReceiptController.KEYSTORE_TYPE_SSL +".cert.already.imported", "Le certificat CEM soumis existe d�j� dans le keystore [SSL/TLS] (alias {0}), l''importation a �t� ignor�e."},
        {CEMReceiptController.KEYSTORE_TYPE_ENC_SIGN +".cert.imported.success", "Le certificat CEM soumis a �t� correctement import� [enc/sign] (alias {0})."},
        {CEMReceiptController.KEYSTORE_TYPE_SSL +".cert.imported.success", "Le certificat CEM soumis a �t� correctement import� [SSL/TLS] (alias {0})."},
        {"category." + CEMEntry.CATEGORY_CRYPT, "Cryptage" },
        {"category." + CEMEntry.CATEGORY_SIGN, "Signature" },
        {"category." + CEMEntry.CATEGORY_SSL, "SSL" },
        {"state." + CEMEntry.STATUS_ACCEPTED_INT, "Accept�e par {0}" },
        {"state." + CEMEntry.STATUS_PENDING_INT, "Pas de r�ponse si loin de {0}" },
        {"state." + CEMEntry.STATUS_REJECTED_INT, "Rejet�e par {0}" },
        {"state." + CEMEntry.STATUS_CANCELED_INT, "Annul�e" },
        {"state." + CEMEntry.STATUS_PROCESSING_ERROR_INT, "Erreur de traitement" },
        {"cemtype.response", "Le CEM message est du type \"certificate response\"" },
        {"cemtype.request", "Le CEM message est du type \"certificate request\"" },
        {"cem.response.relatedrequest.found", "La r�ponse de CEM se rapporte � la demande existante \"{0}\"" },
        {"cem.response.prepared", "Le message de r�ponse de CEM a �t� cr�� pour la demande {0}" },
        {"cem.created.request", "La requ�te CEM a �t� g�n�r�e pour la relation \"{0}\"-\"-\"{1}\". Le certificat avec issuerDN \"{2}\" et le num�ro de s�rie \"{3}\" a �t� int�gr�. L'utilisation d�finie est {4}." },
    };
    
}