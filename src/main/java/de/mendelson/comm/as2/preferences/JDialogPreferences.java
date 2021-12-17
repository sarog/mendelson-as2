//$Header: /as2/de/mendelson/comm/as2/preferences/JDialogPreferences.java 39    12.02.20 14:30 Heller $
package de.mendelson.comm.as2.preferences;

import de.mendelson.util.ColorUtil;
import de.mendelson.util.ImageButtonBar;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.uinotification.UINotification;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Dialog to configure a single partner
 *
 * @author S.Heller
 * @version $Revision: 39 $
 */
public class JDialogPreferences extends JDialog {

    public static final int IMAGE_HEIGHT = 28;

    private final static MendelsonMultiResolutionImage IMAGE_LANGUAGE
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/preferences/language.svg", IMAGE_HEIGHT,
                    IMAGE_HEIGHT * 2);
    private final static MendelsonMultiResolutionImage IMAGE_COLORBLIND
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/preferences/color_blindness.svg", 18,
                    36, MendelsonMultiResolutionImage.SVGScalingOption.KEEP_HEIGHT);    

    /**
     * ResourceBundle to localize the GUI
     */
    private MecResourceBundle rb = null;
    /**
     * The language should be stored in the client preferences, no client-server
     * comm required here
     */
    private PreferencesAS2 clientPreferences = new PreferencesAS2();
    /**
     * stores all available panels
     */
    private List<PreferencesPanel> panelList = new ArrayList<PreferencesPanel>();

    /**
     * Creates new form JDialogPartnerConfig
     *
     * @param parameter Parameter to edit, null for a new one
     * @param parameterList List of available parameter
     */
    public JDialogPreferences(JFrame parent, List<PreferencesPanel> panelList, String selectedTab) {
        super(parent, true);
        this.panelList = panelList;
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePreferences.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        initComponents();
        this.setMultiresolutionIcons();
        this.setupCountrySelection();
        ColorUtil.autoCorrectForegroundColor(this.jLabelLanguageInfo);
        if (this.clientPreferences.get(PreferencesAS2.LANGUAGE).equals("de")) {
            this.jRadioButtonLangDE.setSelected(true);
        } else if (this.clientPreferences.get(PreferencesAS2.LANGUAGE).equals("en")) {
            this.jRadioButtonLangEN.setSelected(true);
        } else if (this.clientPreferences.get(PreferencesAS2.LANGUAGE).equals("fr")) {
            this.jRadioButtonLangFR.setSelected(true);
        }
        String selectedCountryCode = this.clientPreferences.get(PreferencesAS2.COUNTRY).toUpperCase();
        this.jListCountry.setSelectedValue(new DisplayCountry(selectedCountryCode), true);
        boolean colorBlindness = this.clientPreferences.getBoolean(PreferencesAS2.COLOR_BLINDNESS);
        this.jCheckBoxColorBlindness.setSelected(colorBlindness);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        for (PreferencesPanel preferencePanel : this.panelList) {
            //initialize the panels
            preferencePanel.loadPreferences();
            //add the panels to the layout
            this.jPanelEdit.add(preferencePanel, gridBagConstraints);
        }
        ImageButtonBar buttonBar = new ImageButtonBar(ImageButtonBar.HORIZONTAL);
        buttonBar.setPreferredButtonSize(85, 84);
        boolean selected = selectedTab == null;
        for (PreferencesPanel preferencePanel : this.panelList) {
            if (selectedTab != null && preferencePanel.getTabResource().equals(selectedTab)) {
                selected = true;
            }
            buttonBar.addButton(
                    preferencePanel.getIcon(),
                    this.rb.getResourceString(preferencePanel.getTabResource()),
                    new JComponent[]{preferencePanel},
                    selected);
            selected = false;
        }
        buttonBar.addButton(
                new ImageIcon(IMAGE_LANGUAGE.toMinResolution(IMAGE_HEIGHT)),
                this.rb.getResourceString("tab.language"),
                new JComponent[]{this.jPanelLanguage},
                false);
        buttonBar.build();
        //add button bar
        this.jPanelButtonBar.setLayout(new BorderLayout());
        this.jPanelButtonBar.add(buttonBar, BorderLayout.CENTER);
        this.getRootPane().setDefaultButton(this.jButtonOk);

    }

    private void setMultiresolutionIcons() {
        this.jLabelIconBlind.setIcon(new ImageIcon(IMAGE_COLORBLIND.toMinResolution(18)));
    }
        
    
    private void captureGUIValues() {
        boolean clientRestartRequired = false;        
        if (this.jRadioButtonLangDE.isSelected()) {
            if( !this.clientPreferences.get( PreferencesAS2.LANGUAGE).equals( "de")){
                clientRestartRequired = true;
            }
            this.clientPreferences.put(PreferencesAS2.LANGUAGE, "de");
        } else if (this.jRadioButtonLangEN.isSelected()) {
            if( !this.clientPreferences.get( PreferencesAS2.LANGUAGE).equals( "en")){
                clientRestartRequired = true;
            }
            this.clientPreferences.put(PreferencesAS2.LANGUAGE, "en");
        } else if (this.jRadioButtonLangFR.isSelected()) {
            if( !this.clientPreferences.get( PreferencesAS2.LANGUAGE).equals( "fr")){
                clientRestartRequired = true;
            }
            this.clientPreferences.put(PreferencesAS2.LANGUAGE, "fr");
        }        
        if (this.jListCountry.getSelectedValue() != null) {
            String newCountryCode = this.jListCountry.getSelectedValue().getCountryCode();
            if( !this.clientPreferences.get( PreferencesAS2.COUNTRY).equals( newCountryCode)){
                clientRestartRequired = true;
            }
            this.clientPreferences.put(PreferencesAS2.COUNTRY, newCountryCode);
        }
        if( this.clientPreferences.getBoolean(PreferencesAS2.COLOR_BLINDNESS)!=( this.jCheckBoxColorBlindness.isSelected())){
                clientRestartRequired = true;
            }
        this.clientPreferences.putBoolean(PreferencesAS2.COLOR_BLINDNESS, this.jCheckBoxColorBlindness.isSelected());
        if( clientRestartRequired ){
            UINotification.instance().addNotification(
                    PreferencesPanelMDN.IMAGE_PREFS,
                    UINotification.TYPE_WARNING, 
                    this.rb.getResourceString("title"),
                    this.rb.getResourceString("warning.clientrestart.required"));
        }
        
    }

    /**
     * Fills in the available countries of the system into the list
     */
    private void setupCountrySelection() {
        Set<String> countryCodes = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2);
        DefaultListModel listModel = (DefaultListModel) this.jListCountry.getModel();
        listModel.clear();
        List<DisplayCountry> displayList = new ArrayList<DisplayCountry>();
        for (String countryCode : countryCodes) {
            displayList.add(new DisplayCountry(countryCode));
        }
        //sort german special chars the right way if the locale is german...
        Collections.sort(displayList);
        DisplayCountry[] countryArray = new DisplayCountry[displayList.size()];
        displayList.toArray(countryArray);
        this.jListCountry.setListData(countryArray);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupLanguage = new javax.swing.ButtonGroup();
        jPanelEdit = new javax.swing.JPanel();
        jPanelLanguage = new javax.swing.JPanel();
        jRadioButtonLangDE = new javax.swing.JRadioButton();
        jRadioButtonLangEN = new javax.swing.JRadioButton();
        jRadioButtonLangFR = new javax.swing.JRadioButton();
        jPanelSpace = new javax.swing.JPanel();
        jLabelLanguageInfo = new javax.swing.JLabel();
        jLabelCountry = new javax.swing.JLabel();
        jScrollPaneCountry = new javax.swing.JScrollPane();
        jListCountry = new javax.swing.JList<>();
        jPanelSpace44 = new javax.swing.JPanel();
        jLabelLanguage = new javax.swing.JLabel();
        jCheckBoxColorBlindness = new javax.swing.JCheckBox();
        jPanelColorBlindness = new javax.swing.JPanel();
        jLabelIconBlind = new javax.swing.JLabel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jPanelButtonBar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(this.rb.getResourceString( "title"));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelEdit.setLayout(new java.awt.GridBagLayout());

        jPanelLanguage.setLayout(new java.awt.GridBagLayout());

        buttonGroupLanguage.add(jRadioButtonLangDE);
        jRadioButtonLangDE.setText("Deutsch");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 5, 5);
        jPanelLanguage.add(jRadioButtonLangDE, gridBagConstraints);

        buttonGroupLanguage.add(jRadioButtonLangEN);
        jRadioButtonLangEN.setText("English");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelLanguage.add(jRadioButtonLangEN, gridBagConstraints);

        buttonGroupLanguage.add(jRadioButtonLangFR);
        jRadioButtonLangFR.setText("Français");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelLanguage.add(jRadioButtonLangFR, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelLanguage.add(jPanelSpace, gridBagConstraints);

        jLabelLanguageInfo.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabelLanguageInfo.setForeground(new java.awt.Color(255, 51, 0));
        jLabelLanguageInfo.setText(this.rb.getResourceString("info.restart.client"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanelLanguage.add(jLabelLanguageInfo, gridBagConstraints);

        jLabelCountry.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCountry.setText(this.rb.getResourceString( "label.country"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 50, 5, 5);
        jPanelLanguage.add(jLabelCountry, gridBagConstraints);

        jScrollPaneCountry.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jListCountry.setModel(new DefaultListModel());
        jListCountry.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListCountry.setVisibleRowCount(15);
        jScrollPaneCountry.setViewportView(jListCountry);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 50, 5, 5);
        jPanelLanguage.add(jScrollPaneCountry, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPanelLanguage.add(jPanelSpace44, gridBagConstraints);

        jLabelLanguage.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelLanguage.setText(this.rb.getResourceString( "label.language"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 5, 5);
        jPanelLanguage.add(jLabelLanguage, gridBagConstraints);

        jCheckBoxColorBlindness.setText(this.rb.getResourceString( "label.colorblindness"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelLanguage.add(jCheckBoxColorBlindness, gridBagConstraints);

        jPanelColorBlindness.setLayout(new java.awt.GridBagLayout());

        jLabelIconBlind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/preferences/missing_image24x24.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelColorBlindness.add(jLabelIconBlind, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelLanguage.add(jPanelColorBlindness, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelEdit.add(jPanelLanguage, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanelEdit, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jButtonOk.setText(this.rb.getResourceString( "button.ok" ));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelButtons.add(jButtonOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanelButtons, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanelButtonBar, gridBagConstraints);

        setSize(new java.awt.Dimension(1011, 627));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        for (PreferencesPanel panel : this.panelList) {
            panel.savePreferences();
        }        
        this.setVisible(false);
        this.captureGUIValues();        
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupLanguage;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JCheckBox jCheckBoxColorBlindness;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelIconBlind;
    private javax.swing.JLabel jLabelLanguage;
    private javax.swing.JLabel jLabelLanguageInfo;
    private javax.swing.JList<DisplayCountry> jListCountry;
    private javax.swing.JPanel jPanelButtonBar;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelColorBlindness;
    private javax.swing.JPanel jPanelEdit;
    private javax.swing.JPanel jPanelLanguage;
    private javax.swing.JPanel jPanelSpace;
    private javax.swing.JPanel jPanelSpace44;
    private javax.swing.JRadioButton jRadioButtonLangDE;
    private javax.swing.JRadioButton jRadioButtonLangEN;
    private javax.swing.JRadioButton jRadioButtonLangFR;
    private javax.swing.JScrollPane jScrollPaneCountry;
    // End of variables declaration//GEN-END:variables

    private static class DisplayCountry implements Comparable<DisplayCountry> {

        private String countryCode;
        private String displayString;

        public DisplayCountry(String countryCode) {
            this.countryCode = countryCode.toUpperCase();
            Locale locale = new Locale(Locale.getDefault().getLanguage(), countryCode);
            this.displayString = locale.getDisplayCountry() + " (" + countryCode + ")";
        }

        @Override
        public String toString() {
            return (this.displayString);
        }

        @Override
        public boolean equals(Object anObject) {
            if (anObject == this) {
                return (true);
            }
            if (anObject != null && anObject instanceof DisplayCountry) {
                DisplayCountry entry = (DisplayCountry) anObject;
                return (entry.getCountryCode().equals(this.getCountryCode()));
            }
            return (false);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + Objects.hashCode(this.getCountryCode());
            return hash;
        }

        @Override
        public int compareTo(DisplayCountry displayCountry) {
            Collator collator = Collator.getInstance(Locale.getDefault());
            //include french and german special chars into the sort mechanism
            return (collator.compare(this.displayString, displayCountry.displayString));
        }

        /**
         * @return the countryCode
         */
        public String getCountryCode() {
            return countryCode;
        }
    }
}
