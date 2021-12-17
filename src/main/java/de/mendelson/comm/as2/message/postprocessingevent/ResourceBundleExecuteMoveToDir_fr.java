//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleExecuteMoveToDir_fr.java 1     4.09.20 15:33 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;
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
 * @version $Revision: 1 $
 */
public class ResourceBundleExecuteMoveToDir_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"executing.receipt", "[Post-traitement] ({0} --> {1}) Ex�cution d''un �v�nement de post-traitement apr�s r�ception." },
        {"executing.send", "[Post-traitement] ({0} --> {1}) Ex�cution d''un �v�nement de post-traitement apr�s l''envoi." },
        {"executing.targetdir", "[Post-traitement] R�pertoire cible: \"{0}\"." },
        {"executing.movetodir", "[Post-traitement] D�placement de \"{0}\" � \"{1}\"." },
        {"executing.movetodir.success", "[Post-traitement] Fichier d�plac� avec succ�s." },
        {"messageid.nolonger.exist", "[Post-traitement] Impossible d'ex�cuter un �v�nement de post-traitement pour le message \"{0}\" - ce message n'existe plus. Sauter.." },
    };
    
}