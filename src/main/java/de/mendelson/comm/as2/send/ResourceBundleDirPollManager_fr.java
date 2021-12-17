//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleDirPollManager_fr.java 12    7.12.18 9:51 Heller $
package de.mendelson.comm.as2.send;

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
 *
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 12 $
 */
public class ResourceBundleDirPollManager_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"none", "Aucun"},
        {"manager.status.modified", "La surveillance des r�pertoires a chang�, {0} les r�pertoires sont surveill�s."},
        {"poll.stopped", "[Gestionnaire de scrutation des r�pertoires] Scrutation pour les relations \"{0}/{1}\" stopp�."},
        {"poll.started", "[Gestionnaire de scrutation des r�pertoires] Scrutation pour les relations \"{0}/{1}\" d�marr�. Fichiers ignor�s: \"{2}\". Intervalle de scrutation: {3}s"},
        {"poll.modified", "[Gestionnaire de scrutation des r�pertoires] Param�tres de partenaire pour la relation \"{0}/{1}\" ont �t� modifi�s."},
        {"warning.noread", "[Gestionnaire de scrutation des r�pertoires] Pas d''acc�s en lecture pour le fichier outbox {0}, ignorer."},
        {"warning.ro", "[Gestionnaire de scrutation des r�pertoires] Le fichier {0} dans la bo�te de d�part est en lecture seule, ignor�."},
        {"warning.notcomplete", "[Gestionnaire de scrutation des r�pertoires] {0}: Le dossier d'outbox n'est pas complet jusqu'ici et sera ignor�."},
        {"messagefile.deleted", "Le fichier \"{0}\" a �t� d�plac� dans la queue de messages � traiter par le serveur."},
        {"processing.file", "Traitement du fichier \"{0}\" pour les relations \"{1}/{2}\"."},
        {"processing.file.error", "Erreur de traitement du fichier \"{0}\" pour les relations \"{1}/{2}\": \"{3}\"."},
        {"poll.log.wait", "[R�pertoire sondage] {0}->{1}: Suivant processus de sondage sortant dans {2}s ({3})"},
        {"poll.log.polling", "[R�pertoire sondage] {0}->{1}: R�pertoire de vote \"{2}\""},
        {"title.list.polls.running", "R�sum� des annuaires surveill�s:"},
        {"title.list.polls.stopped", "Les op�rations de surveillance suivantes ont pris fin"},
        {"title.list.polls.started", "Le suivi suivant a �t� entam�"},};

}
