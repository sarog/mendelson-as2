//$Header: /as2/de/mendelson/comm/as2/preferences/PreferencesPanelSecurity.java 25    5.11.19 10:45 Heller $
package de.mendelson.comm.as2.preferences;

import de.mendelson.util.ColorUtil;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.clients.filesystemview.RemoteFileBrowser;
import de.mendelson.util.clientserver.clients.preferences.PreferencesClient;
import java.awt.Color;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Panel to define the directory preferences
 *
 * @author S.Heller
 * @version: $Revision: 25 $
 */
public class PreferencesPanelSecurity extends PreferencesPanel {

    /**
     * Localize the GUI
     */
    private MecResourceBundle rb = null;

    /**
     * GUI prefs
     */
    private PreferencesClient preferences;
    private BaseClient baseClient;

    private final static MendelsonMultiResolutionImage ICON_CERTIFICATE
            = MendelsonMultiResolutionImage.fromSVG("/comm/as2/preferences/certificate.svg", 
                    JDialogPreferences.IMAGE_HEIGHT, JDialogPreferences.IMAGE_HEIGHT*2);
    private final static MendelsonMultiResolutionImage ICON_WARNING_SIGN
            = MendelsonMultiResolutionImage.fromSVG("/comm/as2/preferences/warning_sign.svg", 64, 128);
    
    /**
     * Creates new form PreferencesPanelDirectories
     */
    public PreferencesPanelSecurity(BaseClient baseClient) {
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePreferences.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        this.baseClient = baseClient;
        this.preferences = new PreferencesClient(baseClient);
        this.initComponents();
        this.jLabelSecurityHint.setIcon(new ImageIcon(ICON_WARNING_SIGN));
        this.jLabelSecurityHint.setText(this.rb.getResourceString("keystore.hint"));
        this.jLabelSecurityHint.setIconTextGap(20);
        this.jLabelSecurityHint.setForeground(Color.BLACK);
    }

    /**
     * Sets new preferences to this panel to changes/modify
     */
    @Override
    public void loadPreferences() {
        this.jTextFieldKeystoreHTTPS.setText(this.preferences.get(PreferencesAS2.KEYSTORE_HTTPS_SEND));
        this.jPasswordFieldKeystoreHTTPPass.setText(this.preferences.get(PreferencesAS2.KEYSTORE_HTTPS_SEND_PASS));
        this.jPasswordFieldKeystorePass.setText(this.preferences.get(PreferencesAS2.KEYSTORE_PASS));
        this.jTextFieldKeystoreEncryptionSign.setText(this.preferences.get(PreferencesAS2.KEYSTORE));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelMargin = new javax.swing.JPanel();
        jTextFieldKeystoreHTTPS = new javax.swing.JTextField();
        jLabelKeystoreHTTPS = new javax.swing.JLabel();
        jButtonBrowseKeystoreHTTPS = new javax.swing.JButton();
        jLabelKeystoreHTTPSPass = new javax.swing.JLabel();
        jPasswordFieldKeystoreHTTPPass = new javax.swing.JPasswordField();
        jLabelKeystorePass = new javax.swing.JLabel();
        jPasswordFieldKeystorePass = new javax.swing.JPasswordField();
        jLabelKeystoreEncryptionSign = new javax.swing.JLabel();
        jTextFieldKeystoreEncryptionSign = new javax.swing.JTextField();
        jPanelWarning = new javax.swing.JPanel();
        jLabelSecurityHint = new javax.swing.JLabel();
        jPanelSpace = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanelMargin.setLayout(new java.awt.GridBagLayout());

        jTextFieldKeystoreHTTPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldKeystoreHTTPSKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanelMargin.add(jTextFieldKeystoreHTTPS, gridBagConstraints);

        jLabelKeystoreHTTPS.setText(this.rb.getResourceString( "label.keystore.https"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanelMargin.add(jLabelKeystoreHTTPS, gridBagConstraints);

        jButtonBrowseKeystoreHTTPS.setText("..");
        jButtonBrowseKeystoreHTTPS.setToolTipText(this.rb.getResourceString( "button.browse"));
        jButtonBrowseKeystoreHTTPS.setMargin(new java.awt.Insets(2, 8, 2, 8));
        jButtonBrowseKeystoreHTTPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseKeystoreHTTPSActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanelMargin.add(jButtonBrowseKeystoreHTTPS, gridBagConstraints);

        jLabelKeystoreHTTPSPass.setText(this.rb.getResourceString( "label.keystore.https.pass"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jLabelKeystoreHTTPSPass, gridBagConstraints);

        jPasswordFieldKeystoreHTTPPass.setMinimumSize(new java.awt.Dimension(200, 20));
        jPasswordFieldKeystoreHTTPPass.setPreferredSize(new java.awt.Dimension(200, 20));
        jPasswordFieldKeystoreHTTPPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldKeystoreHTTPPassKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jPasswordFieldKeystoreHTTPPass, gridBagConstraints);

        jLabelKeystorePass.setText(this.rb.getResourceString( "label.keystore.pass"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jLabelKeystorePass, gridBagConstraints);

        jPasswordFieldKeystorePass.setMinimumSize(new java.awt.Dimension(200, 20));
        jPasswordFieldKeystorePass.setPreferredSize(new java.awt.Dimension(200, 20));
        jPasswordFieldKeystorePass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldKeystorePassKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jPasswordFieldKeystorePass, gridBagConstraints);

        jLabelKeystoreEncryptionSign.setText(this.rb.getResourceString( "label.keystore.encryptionsign"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jLabelKeystoreEncryptionSign, gridBagConstraints);

        jTextFieldKeystoreEncryptionSign.setEditable(false);
        jTextFieldKeystoreEncryptionSign.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldKeystoreEncryptionSignKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jTextFieldKeystoreEncryptionSign, gridBagConstraints);

        jPanelWarning.setBackground(new java.awt.Color(255, 255, 255));
        jPanelWarning.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanelWarning.setLayout(new java.awt.GridBagLayout());

        jLabelSecurityHint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/comm/as2/preferences/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelWarning.add(jLabelSecurityHint, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(30, 15, 5, 15);
        jPanelMargin.add(jPanelWarning, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelMargin.add(jPanelSpace, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanelMargin, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordFieldKeystorePassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldKeystorePassKeyReleased
        this.preferences.put(PreferencesAS2.KEYSTORE_PASS, new String(this.jPasswordFieldKeystorePass.getPassword()));
    }//GEN-LAST:event_jPasswordFieldKeystorePassKeyReleased

    private void jButtonBrowseKeystoreHTTPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseKeystoreHTTPSActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        RemoteFileBrowser browser = new RemoteFileBrowser(parent, this.baseClient,
                this.rb.getResourceString("filechooser.keystore"));
        browser.setDirectoriesOnly(false);
        browser.setSelectedFile(this.jTextFieldKeystoreHTTPS.getText());
        browser.setVisible(true);
        String selectedPath = browser.getSelectedPath();
        if (selectedPath != null && selectedPath.trim().length() > 0) {
            this.jTextFieldKeystoreHTTPS.setText(selectedPath);
        }
        this.preferences.put(PreferencesAS2.KEYSTORE_HTTPS_SEND, this.jTextFieldKeystoreHTTPS.getText());
    }//GEN-LAST:event_jButtonBrowseKeystoreHTTPSActionPerformed

    private void jPasswordFieldKeystoreHTTPPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldKeystoreHTTPPassKeyReleased
        this.preferences.put(PreferencesAS2.KEYSTORE_HTTPS_SEND_PASS, new String(this.jPasswordFieldKeystoreHTTPPass.getPassword()));
    }//GEN-LAST:event_jPasswordFieldKeystoreHTTPPassKeyReleased

    private void jTextFieldKeystoreHTTPSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldKeystoreHTTPSKeyReleased
        this.preferences.put(PreferencesAS2.KEYSTORE_HTTPS_SEND, this.jTextFieldKeystoreHTTPS.getText());
    }//GEN-LAST:event_jTextFieldKeystoreHTTPSKeyReleased

    private void jTextFieldKeystoreEncryptionSignKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldKeystoreEncryptionSignKeyReleased
        // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldKeystoreEncryptionSignKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBrowseKeystoreHTTPS;
    private javax.swing.JLabel jLabelKeystoreEncryptionSign;
    private javax.swing.JLabel jLabelKeystoreHTTPS;
    private javax.swing.JLabel jLabelKeystoreHTTPSPass;
    private javax.swing.JLabel jLabelKeystorePass;
    private javax.swing.JLabel jLabelSecurityHint;
    private javax.swing.JPanel jPanelMargin;
    private javax.swing.JPanel jPanelSpace;
    private javax.swing.JPanel jPanelWarning;
    private javax.swing.JPasswordField jPasswordFieldKeystoreHTTPPass;
    private javax.swing.JPasswordField jPasswordFieldKeystorePass;
    private javax.swing.JTextField jTextFieldKeystoreEncryptionSign;
    private javax.swing.JTextField jTextFieldKeystoreHTTPS;
    // End of variables declaration//GEN-END:variables

    @Override
    public void savePreferences() {
        //NOP
    }

    @Override
    public ImageIcon getIcon() {
        return (new ImageIcon( ICON_CERTIFICATE ));
                
    }

    @Override
    public String getTabResource() {
        return ("tab.security");
    }

}
