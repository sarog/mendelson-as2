//$Header: /as2/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_fr.java 9     4/06/18 1:35p Heller $
package de.mendelson.util.security.cert.gui.keygeneration;

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
 * @version $Revision: 9 $
 */
public class ResourceBundleGenerateKey_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "G�n�rer la cl�"},
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"label.keytype", "Type de cl�:"},
        {"label.signature", "Signature:"},
        {"label.size", "Taille:"},
        {"label.commonname", "Common name:"},
        {"label.commonname.hint", "(Le nom de domaine)" },
        {"label.organisationunit", "Unit� d''organisation:"},
        {"label.organisationname", "Nom de l''organisation:"},
        {"label.locality", "Localit�:"},
        {"label.locality.hint", "(City)" },
        {"label.state", "�tat:"},
        {"label.countrycode", "Code pays:"},
        {"label.countrycode.hint", "(2 chiffres, ISO 3166)" },
        {"label.mailaddress", "EMail:"},
        {"label.validity", "Validit� en jours:"},
        {"label.purpose", "Usage cl� / utilisation de cl� suppl�mentaire:"},
        {"label.purpose.encsign", "Chiffrage et signature"},
        {"label.purpose.ssl", "TSL/SSL"},
        {"label.subjectalternativenames", "Subject alternative names:" },        
        {"warning.mail.in.domain", "L''adresse e-mail ne fait pas partie du domaine \"{0}\" (e.g. myname@{0}).\nCela pourrait �tre un probl�me si vous souhaitez faire confiance � la cl� plus tard."},
        {"warning.nonexisting.domain", "Le nom de domaine \"{0}\" ne semble pas exister." },
        {"warning.invalid.mail", "L''adresse mail \"{0}\" est invalide." },
        {"button.reedit", "Modifier les param�tres" },
        {"button.ignore", "Ignorer les avertissements" },
        {"warning.title", "Possible probl�me de param�tres" },
        {"view.expert", "Vue d''experts" },
        {"view.basic", "Vue de base" },
    };
}
