//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_fr.java 45    19.11.21 10:35 Heller $
package de.mendelson.comm.as2.preferences;

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
 *
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 45 $
 */
public class ResourceBundlePreferences_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //preferences localized
        {PreferencesAS2.DIR_MSG, "Archivage message"},
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"button.modify", "Modifier"},
        {"button.browse", "Parcourir..."},
        {"filechooser.selectdir", "Sélectionner un répertoire"},
        {"title", "Préférences"},
        {"tab.language", "Langage"},
        {"tab.dir", "Répertoires"},
        {"tab.security", "Sécurité"},
        {"tab.proxy", "Proxy"},
        {"tab.misc", "Divers"},
        {"tab.maintenance", "Maintenance"},
        {"tab.notification", "Notification"},
        {"tab.interface", "Modules"},
        {"tab.log", "Journal"},
        {"header.dirname", "Type"},
        {"header.dirvalue", "Rép."},
        {"label.keystore.https.pass", "Mot de passe du porte-clef (envoi https):"},
        {"label.keystore.pass", "Mot de passe du porte-clef (encryption/signature):"},
        {"label.keystore.https", "Porte-clef (envoi https):"},
        {"label.keystore.encryptionsign", "Porte-clef (enc, sign):"},
        {"label.proxy.url", "URL du proxy:"},
        {"label.proxy.url.hint", "IP ou domaine du proxy"},
        {"label.proxy.port.hint", "Port"},
        {"label.proxy.user", "Utilisateur:"},
        {"label.proxy.user.hint", "Login utilisateur du proxy"},        
        {"label.proxy.pass", "Mot de passe:"},
        {"label.proxy.pass.hint", "Mot de passe utilisateur du proxy"},
        {"label.proxy.use", "Utiliser un proxy HTTP pour les connexions sortante HTTP/HTTPs"},
        {"label.proxy.useauthentification", "Utiliser l''authentification auprès du proxy"},
        {"filechooser.keystore", "Merci de sélectionner le fichier porte-clef (format jks)."},
        {"label.days", "jours"},
        {"label.deletemsgolderthan", "Supprimer automatiquement les messages plus vieux que"},
        {"label.deletemsglog", "Tenir informer dans le log à propos des messages automatiquement supprimés"},
        {"label.deletestatsolderthan", "Supprimer automatiquement les statistiques qui sont plus vieux que"},
        {"label.deletelogdirolderthan", "Suppression automatique des données de journal plus anciennes que"},
        {"label.asyncmdn.timeout", "Temps d''attente maximal pour un MDN asynchrone:"},
        {"label.httpsend.timeout", "Timeout sur envoi HTTP(s):"},
        {"label.min", "min"},
        {"receipt.subdir", "Créer des sous-répertoires par partenaires pour les messages reçus"},
        //notification
        {"checkbox.notifycertexpire", "Notifier l''expiration de certificats"},
        {"checkbox.notifytransactionerror", "Notifier les erreurs de transaction"},
        {"checkbox.notifycem", "Notifier des événements d'échange certificats (CEM)"},
        {"checkbox.notifyfailure", "Notifier les problems système"},
        {"checkbox.notifyresend", "Notifier renvoie rejetés"},
        {"checkbox.notifyconnectionproblem", "Notifier les problèmes de connexion"},
        {"checkbox.notifypostprocessing", "Problèmes lors du post-traitement"},
        {"button.testmail", "Envoyer un e-mail de test"},
        {"label.mailhost", "Hôte du serveur de mail (SMTP):"},
        {"label.mailhost.hint", "IP ou domaine du serveur"},
        {"label.mailport", "Port:"},
        {"label.mailport.hint", "Port"},
        {"label.mailaccount", "Compte sur le serveur de mail:"},
        {"label.mailpass", "Mot de passe sur le serveur de mail:"},
        {"label.notificationmail", "Adresse de notification du destinataire:"},
        {"label.replyto", "Adresse de réponse (Replyto):"},
        {"label.smtpauthentication", "Authentification d''utilisation SMTP"},
        {"label.smtpauthentication.user", "Utilisateur:"},
        {"label.smtpauthentication.user.hint", "Nom d''utilisateur du serveur SMTP"},
        {"label.smtpauthentication.pass", "Mot de passe:"},
        {"label.smtpauthentication.pass.hint", "Mot de passe du serveur SMTP"},
        {"label.security", "Sécurité de connexion:"},
        {"testmail.message.success", "E-mail de test envoyé avec succés."},
        {"testmail.message.error", "Erreur lors de l''envoi de l''e-mail de test:\n{0}"},
        {"testmail.title", "Résultat de l''envoi de l''email de test"},
        {"testmail", "L''email de test"},
        //interface
        {"label.showhttpheader", "Laissez configurer les en-têtes de HTTP dans la configuration d''associé"},
        {"label.showquota", "Laissez configurer l''avis de quote-part dans la configuration d''associé"},
        {"label.cem", "Permettre l''échange de certificat (CEM)"},
        {"label.outboundstatusfiles", "Écrire des fichiers de statut de transaction sortante"},
        {"info.restart.client", "Un redémarrage du client est requise pour effectuer ces modifications valide!"},
        {"remotedir.select", "Sélectionnez le répertoire sur le serveur"},
        //retry
        {"label.retry.max", "Le nombre maximum de tentatives de connexion"},
        {"label.retry.waittime", "Le temps d''attente entre deux tentatives de connexion"},
        {"label.sec", "s"},
        {"keystore.hint", "<HTML><strong>Attention:</strong><br>Voulez pas modifier ces paramètres, sauf si vous avez "
            + "utilisé un outil tiers pour modifier vos mots de passe du fichier de clés (qui n''est pas recommandé). Mise "
            + "en place des mots de passe ici ne sera pas modifier les mots de passe du fichier de clés sous-jacente - ces "
            + "options seulement permettre d''accéder à des fichiers de clés externes. En cas de modification des mots de passe, la mise à jour peut poser "
            + "des problèmes.</HTML>"},
        {"maintenancemultiplier.day", "jour(s)"},
        {"maintenancemultiplier.hour", "heure(s)"},
        {"maintenancemultiplier.minute", "minute(s)"},
        {"label.logpollprocess", "Affichage d''informations sur le processus de vote dans le journal (Énorme quantité d'entrées - ne pas utiliser dans la production)"},
        {"label.max.outboundconnections", "Connexions sortantes parallèles (max)"},
        {"event.preferences.modified.subject", "La valeur {0} du paramètre serveur a été modifiée"},
        {"event.preferences.modified.body", "Valeur précédente: {0}\n\nNouvelle valeur: {1}"},
        {"event.notificationdata.modified.subject", "Les paramètres de notification ont été modifiés."},
        {"event.notificationdata.modified.body", "Les données d''avis sont passées de\n\n{0}\n\nà\n\n{1}"},
        {"label.maxmailspermin", "Nombre maximum de notifications/min:"},
        {"systemmaintenance.hint.deleteoldtransactions", "<HTML>Ce paramètre définit la période pendant laquelle les transactions et les données associées (par exemple, les fichiers temporaires) restent dans le système et doivent être affichées dans l''aperçu des transactions.<br>Ces paramètres n''affectent pas vos données/fichiers reçus, ils ne sont pas affectés.<br>Même pour les transactions supprimées, le journal des transactions est toujours disponible via la fonction recherche log.<HTML>"},
        {"systemmaintenance.hint.deleteoldstatistic", "<HTML>Le système collecte les données de compatibilité des systèmes partenaires et peut les afficher sous forme de statistiques. Cela détermine la période pendant laquelle ces données sont conservées.</HTML>" },
        {"systemmaintenance.hint.deleteoldlogdirs", "<HTML>Même si les anciennes transactions ont été supprimées, les opérations peuvent toujours être retracées grâce aux fichiers journaux existants. Ce paramètre permet de supprimer ces fichiers journaux ainsi que tous les fichiers relatifs aux événements du système qui tombent dans la même période.</HTML>" },
        {"label.colorblindness", "Support pour le daltonisme" },
        {"warning.clientrestart.required", "Les paramètres du client ont été modifiés - veuillez redémarrer le client pour les rendre valides" },
        
    };
}
