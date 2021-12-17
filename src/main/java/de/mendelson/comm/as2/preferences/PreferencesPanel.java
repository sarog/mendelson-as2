//$Header: /as2/de/mendelson/comm/as2/preferences/PreferencesPanel.java 6     7.05.19 14:12 Heller $
package de.mendelson.comm.as2.preferences;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 *Abstract class for all preferences panels
 * @author S.Heller
 * @version: $Revision: 6 $
 */
public abstract class PreferencesPanel extends JPanel{

    /**Initializes the panel: loads all preferences*/
    public abstract void loadPreferences();

    /**Stores the new preference settings*/
    public abstract void savePreferences();

    /**Returns the icon resource string for the button bar*/
    public abstract ImageIcon getIcon();

    /**Returns the resource string for the tab name of the panel*/
    public abstract String getTabResource();


}
