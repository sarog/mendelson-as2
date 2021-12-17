//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessagePacker_fr.java 11    6.12.18 16:11 Heller $
package de.mendelson.comm.as2.message;

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
 * @version $Revision: 11 $
 */
public class ResourceBundleAS2MessagePacker_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"message.signed", "Message sortant sign� avec l''algorithme {1}, utilisant l''alias \"{0}\" du porte-clef."},
        {"message.notsigned", "Le message sortant n''est pas sign�."},
        {"message.encrypted", "Message sortant crypt� avec l''algorithme {1}, utilisant l''alias \"{0}\" du porte-clef."},
        {"message.notencrypted", "Le message sortant n''a pas �t� crypt�."},
        {"mdn.created", "MDN sortant cr�� pour message AS2 \"{0}\", �tat pass� � [{1}]."},
        {"mdn.details", "D�tails MDN: {0}"},
        {"message.compressed", "Contenu sortant compress� de {0} � {1}."},
        {"message.compressed.unknownratio", "Contenu sortant compress�."},
        {"mdn.signed", "Le MDN sortant a �t� sign� avec l''algorithme \"{0}\"."},
        {"mdn.notsigned", "Le MDN sortant n''a pas �t� sign�."},
        {"mdn.creation.start", "G�n�ration sortant MDN, la mise en identifiant de message � \"{0}\"."},
        {"message.creation.start", "G�n�ration sortant message AS2, la mise en identifiant de message � \"{0}\"."},
        {"signature.no.aipa", "Le processus de signature ne pas utiliser l'attribut Algorithm Protection Identificateur tel que d�fini dans la configuration - ceci est peu s�curitaire!"},};

}
