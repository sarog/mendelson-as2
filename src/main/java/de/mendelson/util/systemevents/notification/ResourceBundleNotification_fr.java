//$Header: /as2/de/mendelson/util/systemevents/notification/ResourceBundleNotification_fr.java 10    14.08.19 17:08 Heller $
package de.mendelson.util.systemevents.notification;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * ResourceBundle to localize the mendelson products - if you want to localize
 * eagle to your language, please contact us: localize@mendelson.de
 *
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 10 $
 */
public class ResourceBundleNotification_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //dialog
        {"test.message.send", "Un e-mail de test a �t� envoy� � {0}."},
        {"test.message.debug", "\nEnvoyer un processus envoi a �chou�, voici quelques informations de d�bogage qui pourraient vous aider �:\n"},
        {"misc.message.send", "Un e-mail de notification a �t� envoy� � {0} ({1}-{2})."},
        {"misc.message.send.failed", "L''envoi d'un message de notification � {0} a �chou�"},
        {"notification.about.event", "Cette notification se r�f�re � l'�v�nement syst�me de {0}.\nUrgence: {1}\nEnfin: {2}\nTyp: {3}\nId: {4}"},
        {"notification.summary", "R�sum� des {0} �v�nements syst�me"},
        {"notification.summary.info", 
            "Ce message r�capitulatif s''affiche parce que vous avez d�fini un nombre limit�\n"
            + "d''avis par unit� de temps. Pour obtenir les d�tails de chaque �v�nement,\n"
            + "veuillez d�marrer le client et naviguer vers\n"
            + "\"Fichier-�v�nements syst�me\".\n"
            + "Entrez le num�ro unique de l''�v�nement dans le masque de recherche."},
        {"misc.message.summary.send", "Un courriel de notification sommaire a �t� envoy� � {0}"},
        {"misc.message.summary.failed", "L'envoi d'un message de notification sommaire � {0} a �chou�"},
        {"do.not.reply", "Veuillez ne pas r�pondre � ce mail."},};

}
