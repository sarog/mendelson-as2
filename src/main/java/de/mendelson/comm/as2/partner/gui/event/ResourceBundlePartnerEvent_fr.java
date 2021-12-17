//$Header: /as2/de/mendelson/comm/as2/partner/gui/event/ResourceBundlePartnerEvent_fr.java 3     4.01.21 9:48 Heller $
package de.mendelson.comm.as2.partner.gui.event;
import de.mendelson.comm.as2.partner.PartnerEventInformation;
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
 * @version $Revision: 3 $
 */
public class ResourceBundlePartnerEvent_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"type." + PartnerEventInformation.TYPE_ON_RECEIPT, "� la r�ception"},
        {"type." + PartnerEventInformation.TYPE_ON_SENDERROR, "apr�s l''envoi (erreur)"},
        {"type." + PartnerEventInformation.TYPE_ON_SENDSUCCESS, "apr�s l'envoi (succ�s)"},
        {"title.select.process", "Veuillez s�lectionner un nouveau processus comme �v�nement ({0})" },
        {"tab.newprocess", "Processus disponibles pour le post-traitement" },
        {"process.executeshell", "Ex�cution d''un ordre d''obus" },
        {"process.executeshell.description", "Ex�cutez une commande ou un script shell pour le post-traitement des donn�es." },
        {"process.movetopartner", "Transmission aux partenaires" },
        {"process.movetopartner.description", "Transmission � un partenaire, par exemple de la DMZ vers le syst�me ERP" },
        {"process.movetodirectory", "Aller au r�pertoire" },
        {"process.movetodirectory.description", "D�placer les donn�es vers un autre r�pertoire" },
        {"button.ok", "Ok" },
        {"button.cancel", "Annuler" },
        {"title.configuration.shell", "Configuration de la commande shell [Partenaire {0}, {1}]"},
        {"title.configuration.movetodir", "D�placer les messages vers le r�pertoire [Partenaire {0}, {1}]"},
        {"title.configuration.movetopartner", "Transmission de donn�es � un partenaire [Partenaire {0}, {1}]"},
        {"label.shell.info", "<HTML>Veuillez configurer la commande shell � ex�cuter dans ce cas. N'oubliez pas que cette fonction est sp�cifique au syst�me d'exploitation, elle redirigera vers le shell par d�faut de votre syst�me d'exploitation.</HTML>"},
        {"label.shell.command", "Commande ({0}): "},
        {"shell.hint.replacement." + PartnerEventInformation.TYPE_ON_RECEIPT, "<HTML>Les variables suivantes sont remplac�es par des valeurs syst�me dans cette commande avant qu''elle ne soit ex�cut�e:<br><i>$'{'filename}, $'{'subject},$'{'sender}, $'{'receiver}, $'{'messageid}, $'{'originalfilename}</i></HTML>"},
        {"shell.hint.replacement." + PartnerEventInformation.TYPE_ON_SENDERROR, "<HTML>DLes variables suivantes sont remplac�es par des valeurs syst�me dans cette commande avant qu''elle ne soit ex�cut�e:<br><i>$'{'filename}, $'{'fullstoragefilename}, $'{'log}, $'{'subject},$'{'sender}, $'{'receiver}, $'{'messageid}, $'{'mdntext}, $'{'userdefinedid}</i></HTML>"},
        {"shell.hint.replacement." + PartnerEventInformation.TYPE_ON_SENDSUCCESS, "<HTML>Les variables suivantes sont remplac�es par des valeurs syst�me dans cette commande avant qu''elle ne soit ex�cut�e:<br><i>$'{'filename}, $'{'fullstoragefilename}, $'{'log}, $'{'subject},$'{'sender}, $'{'receiver}, $'{'messageid}, $'{'mdntext}, $'{'userdefinedid}</i></HTML>"},
        {"shell.hint.samples", "<HTML><strong>Exemples</strong><br>Windows: <i>cmd /c move \"$'{'filename}\" \"c:\\mydir\"</i><br>Linux: <i>mv \"$'{'filename}\" \"~/mydir/\"</i></HTML>"},
        {"label.movetodir.info", "<HTML>Veuillez configurer le r�pertoire c�t� serveur dans lequel le message doit �tre d�plac�.</HTML>"},        
        {"label.movetodir.targetdir", "R�pertoire cible ({0}): "},
        {"label.movetodir.remotedir.select", "Veuillez s�lectionner le r�pertoire cible sur le serveur" },        
        {"label.movetopartner.info", "<HTML>Veuillez s�lectionner le partenaire � distance auquel le message doit �tre transmis.</HTML>"},
        {"label.movetopartner", "Partenaires cibles: "},
        {"label.movetopartner.noroutingpartner", "<HTML>Il n''y a pas de partenaire � distance disponible dans le syst�me auquel les messages peuvent �tre envoy�s. Veuillez d''abord ajouter un partenaire auquel les messages doivent �tre envoy�s.</HTML>"},
    };
    
}