//$Header: /as2/de/mendelson/util/ResourceBundleMecFileChooser_fr.java 2     4/06/18 1:35p Heller $
package de.mendelson.util;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the file chooser GUI - if you want to localize
 * eagle to your language, please contact us: localize@mendelson.de
 *
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 2 $
 */
public class ResourceBundleMecFileChooser_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"button.select", "S�lectionner"},};

}
