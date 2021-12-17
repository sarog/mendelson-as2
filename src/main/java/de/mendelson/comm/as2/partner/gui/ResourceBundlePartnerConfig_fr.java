//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerConfig_fr.java 10    20.09.18 16:55 Heller $
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
 * @author E.Pailleau
 * @version $Revision: 10 $
 */
public class ResourceBundlePartnerConfig_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Configuration des partenaires" },
        {"button.ok", "Valider" },
        {"button.cancel", "Annuler" },
        {"button.new", "Nouveau" },
        {"button.delete", "Supprimer" },
        {"nolocalstation.message", "Un partenaire au moins doit �tre d�fini comme station locale." },
        {"nolocalstation.title", "Aucune station locale" },
        {"localstation.noprivatekey.message", "Merci d''affecter une clef priv�e � une des stations locale." },
        {"localstation.noprivatekey.title", "Aucune clef affect�e" },
        {"dialog.partner.delete.message", "Vous �tes sur le point de supprimer le partenaire \"{0}\" de la configuration.\nToute les donn�es concernant le partenaire \"{0}\" seront perdues.\n\nVoulez-vous vraiment supprimer le partenaire \"{0}\"?" },
        {"dialog.partner.delete.title", "Suppression de partenaire" },
        {"dialog.partner.deletedir.message","L'associ� \"{0}\" a �t� supprim�. L'annuaire �tre � la base\n\"{1}\"\ndevrait-il �tre supprim� sur le disque dur, aussi?"},
        {"dialog.partner.deletedir.title", "Annuaire de message d'associ� de suppression" },
        {"dialog.partner.renamedir.message", "L'associ� \"{0}\" a �t� retitr� � \"{1}\". L'annuaire �tre � la base \"{2}\" devrait-il �tre retitr� sur le disque dur, aussi?" },
        {"dialog.partner.renamedir.title", "Retitrez l'annuaire de message d'associ�" },
        {"directory.rename.failure", "Incapable de retitrer \"{0}\" � \"{1}\"." },
        {"directory.rename.success", "L'annuaire \"{0}\" a �t� retitr� � \"{1}\"." },
        {"directory.delete.failure", "Incapable de supprimer \"{0}\": [\"{1}\"]" },
        {"directory.delete.success", "L'annuaire \"{0}\" a �t� supprim�." },
        {"saving", "Enregistrement..." },
        {"module.locked", "La gestion des partenaires est verrouill� par un autre client, vous n''�tes pas autoris� � valider vos modifications!" },
        {"event.partner.deleted.subject", "Le partenaire {0} a �t� supprim� de la gestion des partenaires par l''utilisateur." },
        {"event.partner.deleted.body", "Donn�es du partenaire supprim�:\n\n{0}" },
        {"event.partner.deleted.subject", "Le partenaire {0} a �t� supprim� de la gestion des partenaires par l''utilisateur." },
        {"event.partner.deleted.body", "Donn�es du partenaire supprim�:\n\n{0}" },
        {"event.partner.added.subject", "Le partenaire {0} a �t� ajout� par l'utilisateur � la gestion des partenaires." },
        {"event.partner.added.body", "Donn�es du nouveau partenaire:\n\n{0}" },
        {"event.partner.modified.subject", "Le partenaire {0} a �t� modifi� par l''utilisateur" },
        {"event.partner.modified.body", "Donn�es ant�rieures du partenaire:\n\n{0}\n\nDonn�es sur les nouveaux partenaires:\n\n{1}" },        
    };
    
}
