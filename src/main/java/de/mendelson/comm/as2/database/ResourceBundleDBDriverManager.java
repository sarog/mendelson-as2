//$Header: /as2/de/mendelson/comm/as2/database/ResourceBundleDBDriverManager.java 3     3.02.21 11:30 Heller $
package de.mendelson.comm.as2.database;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * ResourceBundle to localize the mendelson business integration - if you want
 * to localize eagle to your language, please contact us: localize@mendelson.de
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class ResourceBundleDBDriverManager extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"creating.database." + IDBDriverManager.DB_RUNTIME, "Creating runtime database"},
        {"creating.database." + IDBDriverManager.DB_CONFIG, "Creating configuration database"},
        {"database.creation.success." + IDBDriverManager.DB_RUNTIME, "The runtime database has been created successfully" },
        {"database.creation.success." + IDBDriverManager.DB_CONFIG, "The config database has been created successfully" },
        {"database.creation.failed." + IDBDriverManager.DB_RUNTIME, "An error occured during the creation process of the runtime database" },
        {"database.creation.failed." + IDBDriverManager.DB_CONFIG, "An error occured during the creation process of the config database" },
    };

}
