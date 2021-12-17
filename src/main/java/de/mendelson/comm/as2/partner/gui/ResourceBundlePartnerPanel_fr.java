//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerPanel_fr.java 38    30.12.20 11:23 Heller $
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
 * @version $Revision: 38 $
 */
public class ResourceBundlePartnerPanel_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Configuration des partenaires"},
        {"label.name", "Nom:"},
        {"label.id", "AS2 id:"},
        {"label.partnercomment", "Commentaire:" },
        {"label.url", "URL de r�ception:"},
        {"label.mdnurl", "URL des MDN:"},
        {"label.signalias.key", "Clef priv�e (Cr�ation de signature):"},
        {"label.cryptalias.key", "Clef priv�e (D�cryptage):"},
        {"label.signalias.cert", "Certificat du partenaire (V�rification de la signature):"},
        {"label.cryptalias.cert", "Certificat du partenaire (Cryptage):"},
        {"label.signtype", "Algorithme de signature num�rique:"},
        {"label.encryptiontype", "Algorithme de chiffrement des messages:"},
        {"label.email", "Adresse E-mail:"},
        {"label.localstation", "Station locale"},
        {"label.compression", "Compresser les messages sortants (n�cessite une solution AS2 1.1 en face)"},
        {"label.usecommandonreceipt", "Sur r�ception de message:"},
        {"label.usecommandonsenderror", "Sur envoi �chou� de message:"},
        {"label.usecommandonsendsuccess", "Sur envoi r�ussi de message:"},
        {"label.keepfilenameonreceipt", "Garder le nom de fichier original sur r�ception (si l''�metteur a ajout� cette information)"},
        {"label.address", "Adresse:" },
        {"label.contact", "Contact:" },        
        {"tab.misc", "Divers"},
        {"tab.security", "S�curit�"},
        {"tab.send", "Envoi"},
        {"tab.mdn", "MDN"},
        {"tab.dirpoll", "Scrutation de r�pertoire"},
        {"tab.receipt", "R�ception"},
        {"tab.httpauth", "Authentication HTTP"},
        {"tab.httpheader", "En-t�te de HTTP"},
        {"tab.notification", "Notification" },
        {"tab.events", "Post-traitement" },
        {"tab.partnersystem", "Info" },
        {"label.subject", "Sujet du contenu:"},
        {"label.contenttype", "Type de contenu:"},
        {"label.syncmdn", "Utilise des MDN synchrone"},
        {"label.asyncmdn", "Utilise des MDN asynchrone"},
        {"label.signedmdn", "Utilise des MDN sign�s"},
        {"label.polldir", "R�pertoire de scrutation:"},
        {"label.pollinterval", "Intervalle de scrutation:"},
        {"label.pollignore", "Ignorer les fichiers:"},
        {"label.maxpollfiles", "Maximale des fichiers par sondage:"},
        {"label.usehttpauth", "Utiliser l''authentication HTTP pour envoyer les messages AS2"},
        {"label.usehttpauth.user", "Utilisateur:"},
        {"label.usehttpauth.pass", "Mot de passe:"},
        {"label.usehttpauth.asyncmdn", "Utiliser l''authentication HTTP pour envoyer les MDN asynchrones"},
        {"label.usehttpauth.asyncmdn.user", "Utilisateur:"},
        {"label.usehttpauth.asyncmdn.pass", "Mot de passe:"},
        {"hint.subject.replacement", "<HTML>$'{'filename} sera remplac� par le nom de fichier send.<br>Cette valeur sera transf�r�e dans l''en-t�te HTTP, il y a des restrictions! Veuillez utiliser la norme ISO-8859-1 pour l''encodage des caract�res, uniquement des caract�res imprimables, pas de caract�res sp�ciaux. CR, LF et TAB sont remplac�s par \"\\r\", \"\\n\" et \"\\t\".</HTML>"},
        {"hint.keepfilenameonreceipt", "Merci de vous assurer que votre partenaire envoi des nom de fichiers uniques avant d''activer cette option!"},
        {"label.notify.send", "Notifier lors d''un d�passement de quota sur message envoy�" },
        {"label.notify.receive", "Notifier lors d''un d�passement de quota sur message re�u" },
        {"label.notify.sendreceive", "Notifier lors d''un d�passement de quota sur message envoy� ou re�u" },
        {"header.httpheaderkey", "Nom" },
        {"header.httpheadervalue", "Valeur" },
        {"httpheader.add", "Ajouter " },
        {"httpheader.delete", "�liminer" },
        {"label.as2version", "Version AS2:" },
        {"label.productname", "Nom du produit:" },
        {"label.features", "Fonctionnalit�s:" },
        {"label.features.cem", "Certificat d'�change via CEM" },
        {"label.features.ma", "Plusieurs pi�ces jointes" },
        {"label.features.compression", "Compression" },
        {"partnerinfo", "Votre partenaire transmet avec chaque message AS2 quelques informations � propos de ses capacit�s de syst�me AS2. Il s'agit d'une liste de fonctions qui a �t� transmise par votre partenaire." },
        {"partnersystem.noinfo", "Aucune information n''est disponible, qu''il y avait d�j� une transaction?" },
        {"label.httpversion", "Version du protocole HTTP:" },
        {"label.test.connection", "Connexion de test" },
        {"label.url.hint", "<HTML>Veuillez sp�cifier cette URL au format <strong>PROTOCOL://HOST:PORT/CHEMIN</strong>, o� le <strong>PROTOCOL</strong> doit �tre l''un des formats \"http\" ou \"https\". <strong>HOST</strong> indique l'h�te du serveur AS2 de votre partenaire. <strong>PORT</strong> est le port de r�ception de votre partenaire. Si elle n''est pas sp�cifi�e, la valeur \"80\" sera fix�e. <strong>CHEMIN</strong> est le chemin de r�ception, par exemple \"/as2/HttpReceiver\".</HTML>"},
        {"label.url.hint.mdn", "<HTML>C''est l''URL que votre partenaire utilisera pour le MDN asynchrone entrant vers cette station locale.<br>Veuillez sp�cifier cette URL au format <strong>PROTOCOL://HOST:PORT/CHEMIN</strong>. <br><strong>PROTOCOLE</strong> doit �tre l''un de \"http\" ou \"https\".<br><strong>HOST</strong> indique votre propre h�te de serveur AS2.<br><strong>PORT</strong> est le port de r�ception de votre syst�me AS2. S''il n''est pas sp�cifi�, la valeur \"80\" sera d�finie.<br><strong>CHEMIN</strong> indique le chemin de r�ception, par exemple \"/as2/HttpReceiver\".</HTML>"},
        {"label.mdn.description", "<HTML>Le MDN (Message Delivery Notification) est la confirmation du message AS2. Cette section d�finit le comportement de votre partenaire pour vos messages AS2 sortants.</HTML>" },
        {"label.mdn.sync.description", "<HTML>Le partenaire envoie la confirmation (MDN) sur le canal de retour de votre connexion sortante.</HTML>" },
        {"label.mdn.async.description", "<HTML>Le partenaire �tablit une nouvelle connexion � votre syst�me pour envoyer une confirmation pour votre message sortant.</HTML>" },
        {"label.mdn.sign.description", "<HTML>Le protocole AS2 ne d�finit pas comment g�rer un MDN si la signature ne correspond pas - mendelson AS2 affiche un avertissement dans ce cas.</HTML>" },
        {"label.algorithmidentifierprotection", "<HTML>Utiliser l''attribut de protection de l''identificateur d''algorithme dans la signature (recommand�), voir RFC 6211</HTML>" },
        {"label.enabledirpoll", "Activer le sondage d''annuaire pour ce partenaire" },
        {"tooltip.button.editevent", "Modifier l'�v�nement" },
        {"tooltip.button.addevent", "Cr�er un nouvel �v�nement" },
        {"label.httpauthentication.info", "<HTML>Veuillez configurer ici l''authentification d''acc�s de base HTTP si celle-ci est activ�e du c�t� de votre partenaire (d�finie dans la RFC 7617). Pour les demandes non authentifi�es (donn�es de connexion incorrectes, etc.), le syst�me du partenaire distant doit renvoyer un <strong>HTTP 401 Unauthorized</strong> status.<br>Si la connexion � votre partenaire n�cessite l''authentification du client TLS (via des certificats), aucun r�glage n''est n�cessaire ici. Dans ce cas, veuillez importer les certificats du partenaire via le gestionnaire de certificats TLS - le syst�me se chargera alors de l''authentification du client TLS.</HTML>" },
    };
}
