//$Header: /as2/de/mendelson/comm/as2/client/ResourceBundleAS2Gui_fr.java 42    21.08.20 17:59 Heller $
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
 * @author E.Pailleau
 * @version $Revision: 42 $
 */
public class ResourceBundleAS2Gui_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"menu.file", "Fichier"},
        {"menu.file.exit", "Fermer"},
        {"menu.file.partner", "Partenaire"},
        {"menu.file.datasheet", "Cr�er une fiche de communication"},
        {"menu.file.certificates", "Certificats"},
        {"menu.file.certificate", "Certificats"},
        {"menu.file.certificate.signcrypt", "Sign/Cryptage"},
        {"menu.file.certificate.ssl", "SSL/TLS"},
        {"menu.file.cem", "Certificat d'�change pr�sentation (CEM)"},
        {"menu.file.cemsend", "Certificats d''�change avec des partenaires (CEM)"},
        {"menu.file.statistic", "Statistiques"},
        {"menu.file.quota", "Quota"},
        {"menu.file.serverinfo", "Affichage Configuration du serveur HTTP"},
        {"menu.file.systemevents", "Ev�nements syst�me"},
        {"menu.file.searchinserverlog", "Rechercher journal"},        
        {"menu.file.preferences", "Pr�f�rences"},
        {"menu.file.send", "Envoyer un fichier � un partenaire"},
        {"menu.file.resend", "Envoyer en tant que nouvelle transaction"},
        {"menu.file.resend.multiple", "Envoyer en tant que nouvelles transactions"},
        {"menu.file.migrate.hsqldb", "Migrer de la HSQLDB"},
        {"menu.help", "Aide"},
        {"menu.help.about", "A propos"},
        {"menu.help.supportrequest", "Demande de soutien"},
        {"menu.help.shop", "mendelson online shop"},
        {"menu.help.helpsystem", "Syst�me d''aide"},
        {"menu.help.forum", "Forum"},
        {"details", "D�tails du message"},
        {"filter.showfinished", "Voir les termin�s"},
        {"filter.showpending", "Voir les en-cours"},
        {"filter.showstopped", "Voir les stopp�s"},
        {"filter.none", "-- Aucun --"},
        {"filter.partner", "Filtrer le partenaire:"},
        {"filter.localstation", "Filtrer le station locale:"},
        {"filter.direction", "Filtrer le direction:"},
        {"filter.direction.inbound", "Entrer"},
        {"filter.direction.outbound", "Sortant"},
        {"filter", "Filtrer"},
        {"filter.use", "Utiliser le filtre de temps" },
        {"filter.from", "De:" },
        {"filter.to", "Jusqu''�:" },
        {"keyrefresh", "Recharger cl�s"},
        {"configurecolumns", "Colonnes" },
        {"delete.msg", "Suppression"},
        {"stoprefresh.msg", "Figer le rafra�chissement"},
        {"dialog.msg.delete.message", "Voulez-vous vraiment supprimer de mani�re permanente les messages s�lectionn�s ?"},
        {"dialog.msg.delete.title", "Suppression"},
        {"msg.delete.success.single", "{0} message a �t� supprim� avec succ�s" },
        {"msg.delete.success.multiple", "{0} messages ont �t� supprim�s avec succ�s" },
        {"welcome", "Bienvenue, {0}"},
        {"fatal.error", "Erreur"},
        {"warning.refreshstopped", "Le rafra�chissement de l''interface a �t� arr�t�."},
        {"tab.welcome", "Nouveaut�s et mises � jour"},
        {"tab.transactions", "Transactions"},
        {"new.version", "Une nouvelle version est disponible. Cliquez ici pour la t�l�charger."},
        {"new.version.logentry.1", "Une nouvelle version est disponible."},
        {"new.version.logentry.2", "Se il vous pla�t visitez {0} pour le t�l�charger."}, 
        {"dbconnection.failed.message", "Incapable d''�tablir une connexion DB au serveur AS2: {0}"},
        {"dbconnection.failed.title", "Impossible de se connecter"},
        {"login.failed.client.incompatible.message", "Le serveur de rapports que ce client est incompatible. Veuillez utiliser la version du client appropri�."},
        {"login.failed.client.incompatible.title", "Login rejet�"},
        {"uploading.to.server", "T�l�chargement sur le serveur"},
        {"refresh.overview", "Rafra�chissant"},
        {"dialog.resend.message", "Voulez-vous vraiment de renvoyer les donn�es de la transaction s�lectionn�e?"},
        {"dialog.resend.message.multiple", "Voulez-vous vraiment de renvoyer les donn�es des {0} transactions s�lectionn�es?"},
        {"dialog.resend.title", "Transaction renvoyer"},        
        {"logputput.disabled", "** La sortie dans le journal a �t� d�sactiv� **"},
        {"logputput.enabled", "** La sortie du journal a �t� activ� **"},
        {"resend.failed.nopayload", "Renvoi en cas d'�chec d'une nouvelle transaction: La transaction s�lectionn�e {0} n'a pas de donn�es utilisateur." },
    };
}