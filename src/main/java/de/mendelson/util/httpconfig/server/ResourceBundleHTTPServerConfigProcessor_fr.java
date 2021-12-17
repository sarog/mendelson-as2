//$Header: /as2/de/mendelson/util/httpconfig/server/ResourceBundleHTTPServerConfigProcessor_fr.java 9     25.06.20 10:36 Heller $
package de.mendelson.util.httpconfig.server;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class ResourceBundleHTTPServerConfigProcessor_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"http.server.config.listener", "Port {0} ({1}), Reli� � l''adaptateur r�seau {2}"},
        {"http.server.config.keystorepath", "SSL/TLS keystore: \"{0}\""},
        {"http.server.config.clientauthentication", "Le serveur requiert une authentification client: {0}"},
        {"external.ip", "IP externe: {0} / {1}"},
        {"external.ip.error", "IP externe: -Ne peut pas �tre d�tect�-"},
        {"http.receipturls", "URL de r�ception compl�te qui sont possibles dans la configuration actuelle:"},
        {"http.serverstateurl", "Affichage de l''�tat du serveur:"},
        {"http.deployedwars", "Fichiers war actuellement d�ploy�s dans le serveur HTTP (fonctionnalit� Servlet):"},
        {"webapp.as2.war", "mendelson AS2 Servlet de r�cepteur"},
        {"webapp.as4.war", "mendelson AS4 Servlet de r�cepteur"},
        {"webapp.webas2.war", "mendelson AS2 Server Web Monitoring"},
        {"webapp.as2-sample.war", "Exemples d''API AS2 mendelson"},
        {"webapp.as4-sample.war", "Exemples d''API AS4 mendelson"},
        {"info.cipher", "Les codes suivants sont pris en charge par le serveur HTTP sous-jacent.\nLes mod�les support�s d�pendent de votre Java VM ({1}).\nVous pouvez d�sactiver les diff�rents chiffres dans le fichier de configuration\n(\"{0}\")."},
        {"info.cipher.howtochange", "Pour d�sactiver certains chiffres pour les connexions entrantes, veuillez modifier le fichier de configuration de votre serveur HTTP int�gr� ({0}) avec un �diteur de texte. Veuillez rechercher la cha�ne <Set name=\"ExcludeCipherSuites\">, ajouter le chiffre � exclure et red�marrer le programme."},
        {"info.protocols", "Les protocoles suivants sont pris en charge par le serveur HTTP sous-jacent.\nLes protocoles pris en charge d�pendent de votre VM Java utilis� ({1}).\nVous pouvez d�sactiver les protocoles individuels dans le fichier de configuration\n(\"{0}\")."},
        {"info.protocols.howtochange", "Pour d�sactiver certains protocoles en entr�e, veuillez modifier le fichier de configuration de votre serveur HTTP int�gr� ({0}) avec un �diteur de texte. Veuillez rechercher la cha�ne <Set name=\"ExcludeProtocols\">, ajouter le protocole � exclure et red�marrer le programme."},
    };
}
