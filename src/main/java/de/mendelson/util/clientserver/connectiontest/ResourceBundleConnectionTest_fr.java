//$Header: /as2/de/mendelson/util/clientserver/connectiontest/ResourceBundleConnectionTest_fr.java 6     4.11.20 15:50 Heller $
package de.mendelson.util.clientserver.connectiontest;

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
 * @version $Revision: 6 $
 */
public class ResourceBundleConnectionTest_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"tag", "Test de connexion sur {0}"},
        {"timeout.set", "R�glage du d�lai d''attente sur {0}ms"},
        {"test.start.ssl", "D�marrer la v�rification de la connexion sur {0} � l''aide de la connexion SSL/TLS. N''oubliez pas que pendant ce test, votre client fera confiance � tous les certificats de serveur et ignorera votre keystore TLS - ce qui signifie qu''il n''est pas assur� que votre keystore TLS est configur� correctement m�me si ce test est r�ussi."},
        {"test.start.plain", "D�marrage du contr�le de connexion sur {0} � l''aide de la connexion PLAIN....."},
        {"connection.problem", "Impossible d''atteindre {0} - probl�me d'infrastructure ou mauvaise adresse saisie"},
        {"connection.success", "Connexion � {0} �tablie avec succ�s"},
        {"exception.occured", "Une exception s''est produite pendant le test de connexion : [{0}] {1}"},
        {"exception.occured.oftpservice", "Le syst�me est incapable d''identifier un syst�me OFTP2 en cours d'ex�cution � l''adresse et au port souhait�s. Cela peut �tre un probl�me temporaire - cela signifie qu''il est possible que les param�tres d'adresse soient corrects et que le serveur OFTP2 distant ne fonctionne pas actuellement ou qu''il existe un probl�me d''infrastructure temporaire au niveau de vos partenaires. L''exception suivante s'est produite: [{0}] {1}"},
        {"remote.service.identification", "Identification du service � distance: \"{0}\""},
        {"service.found.success", "Le succ�s: Ex�cution du service OFTP trouv� � {0}."},
        {"service.found.failure", "Echec: Aucun service OFTP en cours d''ex�cution trouv� � {0}."},
        {"wrong.protocol", "Le protocole trouv� est\"{0}\", ce n'est pas une connexion s�curis�e. "
            + "Vous avez essay� de vous connecter via [{1}] � cette adresse mais ce n''est pas fourni par le serveur distant � cette adresse et � ce port."},
        {"wrong.protocol.hint", "Soit votre partenaire s''attend � une connexion simple, soit il utilise le mauvais protocole, soit l''authentification du client est requise."},
        {"protocol.information", "Le protocole utilis� a �t� identifi� comme suit \"{0}\""},
        {"requesting.certificates", "Demande de certificat(s) � un h�te distant"},
        {"certificates.found", "{0} certificats ont �t� trouv�s et t�l�charg�s"},
        {"certificates.found.details", "Certificat [{0}/{1}]: {2}"},
        {"check.for.service.oftp2", "V�rifier le fonctionnement du service OFTP2...."},
        {"certificate.ca", "CA Certificat"},
        {"certificate.enduser", "Certificat d''utilisateur final"},
        {"certificate.selfsigned", "Auto-sign�"},
        {"certificate.does.not.exist.local", "Ce certificat n''existe pas dans votre keystore TLS/SSL local - veuillez l''importer."},
        {"certificate.does.exist.local", "Ce certificat existe dans votre keystore TLS/SSL local, l''alias est \"{0}\""},
        {"test.connection.direct", "Une connexion IP directe est utilis�e"},
        {"test.connection.proxy.auth", "La connexion utilise le proxy {0} avec authentification (Utilisateur \"{1}\")"},
        {"test.connection.proxy.noauth", "La connexion utilise le proxy {0} sans authentification"},
        {"result.exception", "L''erreur suivante s''est produite pendant le test :{0}."},
        {"info.protocols", "Le client permet la n�gociation via les protocoles TLS suivants: {0}" },
        {"sni.extension.set", "L''extension TLS SNI (nom de l''h�te) a �t� fix�e � \"{0}\"" },
    };

}
