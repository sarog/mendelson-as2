//$Header: /as4/de/mendelson/util/clientserver/connectiontest/gui/ResourceBundleDialogConnectionTestResult_fr.java 3     18.11.20 11:42 Helle $
package de.mendelson.util.clientserver.connectiontest.gui;

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
 * @version $Revision: 3 $
 */
public class ResourceBundleDialogConnectionTestResult_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"title", "R�sultat du test de connexion"},
        {"description." + JDialogConnectionTestResult.CONNECTION_TEST_OFTP2, 
            "Le syst�me a effectu� un test de connexion � l'adresse {0}, port {1}. "
            + "Le r�sultat suivant indique si la connexion a r�ussi et si un serveur OFTP2 "
            + "fonctionne � cette adresse. Si une connexion TLS doit �tre utilis�e et que cela "
            + "�tait possible, vous pouvez t�l�charger les certificats de votre partenaire et les "
            + "importer dans votre keystore."},
        {"description." + JDialogConnectionTestResult.CONNECTION_TEST_AS2, 
            "Le syst�me a effectu� un test de connexion � l''adresse {0}, port {1}. "
            + "Le r�sultat suivant indique si la connexion a r�ussi et si un serveur "
            + "HTTP fonctionne � cette adresse. M�me si le test est r�ussi, il n''est "
            + "pas certain qu''il s'agisse d''un serveur HTTP normal ou d''un serveur "
            + "AS2. Si une connexion TLS doit �tre utilis�e (HTTPS) et que cela a �t� "
            + "possible avec succ�s, vous pouvez t�l�charger les certificats de votre "
            + "partenaire et les importer dans votre keystore."},
        {"description." + JDialogConnectionTestResult.CONNECTION_TEST_AS4, 
            "Le syst�me a effectu� un test de connexion � l''adresse {0}, port {1}. "
            + "Le r�sultat suivant indique si la connexion a r�ussi et si un serveur "
            + "HTTP fonctionne � cette adresse. M�me si le test est r�ussi, il n''est "
            + "pas certain qu''il s'agisse d''un serveur HTTP normal ou d''un serveur "
            + "AS4. Si une connexion TLS doit �tre utilis�e (HTTPS) et que cela a �t� "
            + "possible avec succ�s, vous pouvez t�l�charger les certificats de votre "
            + "partenaire et les importer dans votre keystore."},
        {"OK", "[R�USSIEUX]"},
        {"FAILED", "[ERREUR]"},
        {"AVAILABLE", "[AVANT-PROPOS]"},
        {"NOT_AVAILABLE", "[NON-EXISTANT]"},
        {"header.ssl", "{0} [Raccordement TLS]"},
        {"header.plain", "{0} [Connexion non s�curis�e]"},
        {"no.certificate.plain", "Non disponible (connexion non s�curis�e)"},
        {"button.viewcert", "<HTML><div style=\"text-align:center\">Certificat(s) d''importation</div></HTML>"},
        {"button.close", "Fermer"},
        {"label.connection.established", "La simple connexion IP a �t� �tablie"},
        {"label.certificates.available.local", "Les certificats partenaires (TLS) sont disponibles dans votre syst�me."},
        {"label.running.oftpservice", "Un service OFTP en cours d''ex�cution a �t� trouv�."},
        {"used.cipher", "L''algorithme de cryptage suivant a �t� utilis� pour le test: \"{0}\"" },          
    };

}
