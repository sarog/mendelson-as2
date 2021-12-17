//$Header: /oftp2/de/mendelson/util/systemevents/ResourceBundleSystemEvent_fr.java 22    20.09.19 10:32 Heller $
package de.mendelson.util.systemevents;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products
 *
 * @author S.Heller
 * @version $Revision: 22 $
 */
public class ResourceBundleSystemEvent_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"type." + SystemEvent.TYPE_CERTIFICATE_ADD, "Certificat (ajouter)"},
        {"type." + SystemEvent.TYPE_CERTIFICATE_ANY, "Certificat"},
        {"type." + SystemEvent.TYPE_CERTIFICATE_DEL, "Certificat (supprimer)"},
        {"type." + SystemEvent.TYPE_CERTIFICATE_EXCHANGE_ANY, "Certificat (�change)"},
        {"type." + SystemEvent.TYPE_CERTIFICATE_EXCHANGE_REQUEST_RECEIVED, "Certificat (�change demande entrante)"},
        {"type." + SystemEvent.TYPE_CERTIFICATE_EXPIRE, "Certificat (expire)"},
        {"type." + SystemEvent.TYPE_CERTIFICATE_MODIFY, "Certificat (alias modifi�)"},
        {"type." + SystemEvent.TYPE_CONNECTIVITY_ANY, "Connectivit�"},
        {"type." + SystemEvent.TYPE_CONNECTIVITY_TEST, "Test de connexion"},
        {"type." + SystemEvent.TYPE_DATABASE_ANY, "Base de donn�es"},
        {"type." + SystemEvent.TYPE_DATABASE_CREATION, "Base de donn�es (Cr�ation)"},
        {"type." + SystemEvent.TYPE_DATABASE_UPDATE, "Base de donn�es (Mise � jour)"},
        {"type." + SystemEvent.TYPE_DATABASE_INITIALIZATION, "Base de donn�es (Initialisation)"},
        {"type." + SystemEvent.TYPE_NOTIFICATION_ANY, "Notification"},
        {"type." + SystemEvent.TYPE_NOTIFICATION_SEND_FAILED, "Envoi de la notification (�chec)"},
        {"type." + SystemEvent.TYPE_NOTIFICATION_SEND_SUCCESS, "Envoi de l'avis (succ�s)"},
        {"type." + SystemEvent.TYPE_PARTNER_ADD, "Partenaire (ajouter)"},
        {"type." + SystemEvent.TYPE_PARTNER_DEL, "Partenaire (supprimer)"},
        {"type." + SystemEvent.TYPE_PARTNER_MODIFY, "Partenaire (modifier)"},
        {"type." + SystemEvent.TYPE_QUOTA_ANY, "Contingent"},
        {"type." + SystemEvent.TYPE_QUOTA_RECEIVE_EXCEEDED, "Contingent d�pass�"},
        {"type." + SystemEvent.TYPE_QUOTA_SEND_EXCEEDED, "Contingent d�pass�"},
        {"type." + SystemEvent.TYPE_QUOTA_RECEIVE_EXCEEDED, "Contingent d�pass�"},
        {"type." + SystemEvent.TYPE_SERVER_CONFIGURATION_CHANGED, "Configuration modifi�e"},
        {"type." + SystemEvent.TYPE_SERVER_CONFIGURATION_ANY, "Configuration"},
        {"type." + SystemEvent.TYPE_SERVER_CONFIGURATION_CHECK, "Contr�le de configuration"},
        {"type." + SystemEvent.TYPE_SERVER_COMPONENTS_ANY, "El�ment de serveur"},
        {"type." + SystemEvent.TYPE_MAIN_SERVER_RUNNING, "Serveur est en cours d''ex�cution"},
        {"type." + SystemEvent.TYPE_MAIN_SERVER_SHUTDOWN, "Arr�t du serveur"},
        {"type." + SystemEvent.TYPE_MAIN_SERVER_STARTUP_BEGIN, "D�marrage du serveur"},
        {"type." + SystemEvent.TYPE_DATABASE_SERVER_STARTUP_BEGIN, "D�marrage du serveur de base de donn�es"},
        {"type." + SystemEvent.TYPE_DATABASE_SERVER_RUNNING, "Serveur de base de donn�es en cours d'ex�cution"},
        {"type." + SystemEvent.TYPE_DATABASE_SERVER_SHUTDOWN, "Arr�t du serveur de base de donn�es"},
        {"type." + SystemEvent.TYPE_HTTP_SERVER_STARTUP_BEGIN, "D�marrage du serveur HTTP"},
        {"type." + SystemEvent.TYPE_HTTP_SERVER_RUNNING, "Serveur HTTP en cours d'ex�cution"},
        {"type." + SystemEvent.TYPE_HTTP_SERVER_SHUTDOWN, "Arr�t du serveur HTTP"},
        {"type." + SystemEvent.TYPE_TRFC_SERVER_STARTUP_BEGIN, "D�marrage du serveur TRFC"},
        {"type." + SystemEvent.TYPE_TRFC_SERVER_RUNNING, "Serveur TRFC en cours d'ex�cution"},
        {"type." + SystemEvent.TYPE_TRFC_SERVER_SHUTDOWN, "Arr�t du serveur TRFC"},
        {"type." + SystemEvent.TYPE_TRFC_SERVER_STATE, "Statut du serveur TRFC"},
        {"type." + SystemEvent.TYPE_SCHEDULER_SERVER_STARTUP_BEGIN, "D�marrage de l'ordonnanceur"},      
        {"type." + SystemEvent.TYPE_SCHEDULER_SERVER_RUNNING, "Planificateur en cours d''ex�cution"},      
        {"type." + SystemEvent.TYPE_SCHEDULER_SERVER_SHUTDOWN, "Arr�t de l''ordonnanceur"},  
        {"type." + SystemEvent.TYPE_TRANSACTION_ANY, "Transaction"},
        {"type." + SystemEvent.TYPE_TRANSACTION_ERROR, "Transaction (erreur)"},
        {"type." + SystemEvent.TYPE_TRANSACTION_REJECTED_RESEND, "Transaction (r�exp�dition rejet�e)"},
        {"type." + SystemEvent.TYPE_TRANSACTION_DUPLICATE_MESSAGE, "Transaction (double message)"},
        {"type." + SystemEvent.TYPE_TRANSACTION_DELETE, "Transaction (supprimer)"},
        {"type." + SystemEvent.TYPE_TRANSACTION_CANCEL, "Transaction (annuler)"},
        {"type." + SystemEvent.TYPE_TRANSACTION_RESEND, "Transaction (renvoyer)"},
        {"type." + SystemEvent.TYPE_PROCESSING_ANY, "Traitement des donn�es"},
        {"type." + SystemEvent.TYPE_PRE_PROCESSING, "Pr�traitement"},
        {"type." + SystemEvent.TYPE_POST_PROCESSING, "Post-traitement"},
        {"type." + SystemEvent.TYPE_ACTIVATION_ANY, "Activation"},
        {"type." + SystemEvent.TYPE_FILE_OPERATION_ANY, "Op�ration sur fichier"},
        {"type." + SystemEvent.TYPE_FILE_DELETE, "Fichier (supprimer)"},
        {"type." + SystemEvent.TYPE_FILE_MOVE, "Fichier (d�placer)"},
        {"type." + SystemEvent.TYPE_FILE_COPY, "Fichier (copie)"},
        {"type." + SystemEvent.TYPE_MKDIR, "R�pertoire (cr�er)"},
        {"type." + SystemEvent.TYPE_DIRECTORY_MONITORING_STATE_CHANGED, "Surveillance du r�pertoire (statut modifi�)"},
        {"type." + SystemEvent.TYPE_CLIENT_ANY, "Interface utilisateur"},
        {"type." + SystemEvent.TYPE_CLIENT_LOGIN_FAILURE, "Connexion utilisateur (�chec)"},
        {"type." + SystemEvent.TYPE_CLIENT_LOGIN_SUCCESS, "Connexion utilisateur (succ�s)"},
        {"type." + SystemEvent.TYPE_CLIENT_LOGOFF, "D�connexion utilisateur"},
        {"type." + SystemEvent.TYPE_OTHER, "Autre"},
        {"type." + SystemEvent.TYPE_PORT_LISTENER, "L''auditeur du port"},
        {"origin." + SystemEvent.ORIGIN_SYSTEM, "Syst�me" },
        {"origin." + SystemEvent.ORIGIN_TRANSACTION, "Transaction" },
        {"origin." + SystemEvent.ORIGIN_USER, "Utilisateur" },
        {"severity." + SystemEvent.SEVERITY_ERROR, "Erreur"},
        {"severity." + SystemEvent.SEVERITY_WARNING, "Avertissement"},
        {"severity." + SystemEvent.SEVERITY_INFO, "Info"},
        {"category." + SystemEvent.CATEGORY_ACTIVATION, "D�clenchement" },
        {"category." + SystemEvent.CATEGORY_CERTIFICATE, "Brevet" },
        {"category." + SystemEvent.CATEGORY_CONFIGURATION, "Konfiguration" },
        {"category." + SystemEvent.CATEGORY_CONNECTIVITY, "Liaison" },
        {"category." + SystemEvent.CATEGORY_DATABASE, "Banque de donn�es" },
        {"category." + SystemEvent.CATEGORY_NOTIFICATION, "Notification" },
        {"category." + SystemEvent.CATEGORY_OTHER, "Autre" },
        {"category." + SystemEvent.CATEGORY_PROCESSING, "Traitement des donn�es" },
        {"category." + SystemEvent.CATEGORY_QUOTA, "Conditionnel" },
        {"category." + SystemEvent.CATEGORY_SERVER_COMPONENTS, "Composant Serveur" },
        {"category." + SystemEvent.CATEGORY_TRANSACTION, "Transaction"},
        {"category." + SystemEvent.CATEGORY_FILE_OPERATION, "Op�ration sur fichier" },
        {"category." + SystemEvent.CATEGORY_CLIENT_OPERATION, "Op�ration interface utilisateur" },
    };
}
