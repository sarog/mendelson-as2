package de.mendelson.util.clientserver.connectiontest.gui;

import de.mendelson.util.ColorUtil;
import de.mendelson.util.clientserver.connectiontest.ConnectionTestResult;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.clientserver.connectiontest.ConnectionTest;
import de.mendelson.util.log.JTextPaneLoggingHandler;
import de.mendelson.util.log.LogFormatter;
import de.mendelson.util.log.LoggingHandlerLogEntryArray;
import static de.mendelson.util.log.panel.LogConsolePanel.COLOR_BLACK;
import static de.mendelson.util.log.panel.LogConsolePanel.COLOR_BLUE;
import static de.mendelson.util.log.panel.LogConsolePanel.COLOR_BROWN;
import static de.mendelson.util.log.panel.LogConsolePanel.COLOR_DARK_GREEN;
import static de.mendelson.util.log.panel.LogConsolePanel.COLOR_LIGHT_GRAY;
import static de.mendelson.util.log.panel.LogConsolePanel.COLOR_RED;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.gui.JDialogInfoOnExternalCertificate;
import de.mendelson.util.security.cert.gui.ResourceBundleCertificates;
import de.mendelson.util.uinotification.UINotification;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Dialog to display the test result of a connection test
 *
 * @author S.Heller
 * @version $Revision: 22 $
 */
public class JDialogConnectionTestResult extends JDialog {

    public static final int CONNECTION_TEST_OFTP2 = ConnectionTest.CONNECTION_TEST_OFTP2;
    public static final int CONNECTION_TEST_AS2 = ConnectionTest.CONNECTION_TEST_AS2;
    public static final int CONNECTION_TEST_AS4 = ConnectionTest.CONNECTION_TEST_AS4;

    private ConnectionTestResult result;
    private MecResourceBundle rb = null;
    private CertificateManager certManagerSSL;
    private MecResourceBundle rbCerts;

    private static final MendelsonMultiResolutionImage IMAGE_CONNECTIONTEST
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/clientserver/connectiontest/gui/testconnection.svg", 24, 48);
    private static final MendelsonMultiResolutionImage IMAGE_IMPORT
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/clientserver/connectiontest/gui/import.svg", 24, 48);

    /**
     * Creates new form JDialogTestResult
     */
    public JDialogConnectionTestResult(JFrame parent,
            final int CONNECTION_TYPE_TEST,
            List<LoggingHandlerLogEntryArray.LogEntry> logEntries,
            ConnectionTestResult result,
            CertificateManager certManagerEncSign,
            CertificateManager certManagerSSL) {
        super(parent, true);
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleDialogConnectionTestResult.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        try {
            this.rbCerts = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCertificates.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        initComponents();
        this.setMultiresolutionIcons();
        Color errorColor = Color.red.darker();
        Color okColor = Color.green.darker().darker();
        Color labelBackground = this.jLabelConnectionState.getBackground();
        errorColor = ColorUtil.getBestContrastColorAroundForeground(labelBackground, errorColor);
        okColor = ColorUtil.getBestContrastColorAroundForeground(labelBackground, okColor);
        this.jLabelRemoteOFTPService.setVisible(false);
        this.jLabelOFTPServiceState.setVisible(false);
        if (CONNECTION_TYPE_TEST == CONNECTION_TEST_OFTP2) {
            this.jLabelRemoteOFTPService.setVisible(true);
            this.jLabelOFTPServiceState.setVisible(true);
        }
        this.certManagerSSL = certManagerSSL;
        this.result = result;
        this.setTitle(this.rb.getResourceString("title"));
        if (result.wasSSLTest()) {
            this.jLabelHeader.setText(this.rb.getResourceString("header.ssl", result.getTestedRemoteAddress()));
        } else {
            this.jLabelHeader.setText(this.rb.getResourceString("header.plain", result.getTestedRemoteAddress()));
        }
        if (result.isConnectionIsPossible()) {
            this.jLabelConnectionState.setForeground(okColor);
            this.jLabelConnectionState.setText(this.rb.getResourceString("OK"));
        } else {
            this.jLabelConnectionState.setForeground(errorColor);
            this.jLabelConnectionState.setText(this.rb.getResourceString("FAILED"));
        }
        if (!result.wasSSLTest()) {
            this.jButtonImportCertificates.setEnabled(false);
            this.jLabelRemoteCertificatesAvailableLocal.setEnabled(false);
            this.jLabelCertificateState.setEnabled(false);
            this.jLabelCertificateState.setText(this.rb.getResourceString("no.certificate.plain"));
        } else {
            if (result.getFoundCertificates() == null || result.getFoundCertificates().length == 0) {
                this.jLabelCertificateState.setForeground(errorColor);
                this.jLabelCertificateState.setText(this.rb.getResourceString("FAILED"));
                this.jButtonImportCertificates.setEnabled(false);
            } else if (result.getFoundCertificates() != null && result.getFoundCertificates().length > 0
                    && this.remoteCertificatesAreAvailable(certManagerSSL, result.getFoundCertificates())) {
                this.jLabelCertificateState.setForeground(okColor);
                this.jLabelCertificateState.setText(this.rb.getResourceString("AVAILABLE"));
                this.jButtonImportCertificates.setEnabled(true);
            } else {
                this.jLabelCertificateState.setForeground(errorColor);
                this.jLabelCertificateState.setText(this.rb.getResourceString("NOT_AVAILABLE"));
                this.jButtonImportCertificates.setEnabled(true);
            }
        }
        if (result.isOftpServiceFound()) {
            this.jLabelOFTPServiceState.setForeground(okColor);
            this.jLabelOFTPServiceState.setText(this.rb.getResourceString("OK"));
        } else {
            this.jLabelOFTPServiceState.setForeground(errorColor);
            this.jLabelOFTPServiceState.setText(this.rb.getResourceString("FAILED"));
        }
        //display the log if there is any
        Logger testLogger = Logger.getAnonymousLogger();
        testLogger.setUseParentHandlers(false);
        JTextPaneLoggingHandler handler = new JTextPaneLoggingHandler(this.jTextPaneLog,
                new LogFormatter(LogFormatter.FORMAT_CONSOLE));
        this.setDefaultLogColors(handler);
        handler.adjustColorsByContrast();
        testLogger.setLevel(Level.ALL);
        testLogger.addHandler(handler);
        testLogger.log(Level.FINE,
                this.rb.getResourceString("description." + CONNECTION_TYPE_TEST,
                        new Object[]{
                            result.getTestedRemoteAddress().getHostString(),
                            String.valueOf(result.getTestedRemoteAddress().getPort())
                        }));
        testLogger.log(Level.INFO, "");
        testLogger.log(Level.INFO, "");
        for (LoggingHandlerLogEntryArray.LogEntry logEntry : logEntries) {
            testLogger.log(logEntry.getLevel(), logEntry.getMessage());
        }
        //log some technical information - the ciphers
        if (result.wasSSLTest()) {
            String cipher = result.getUsedCipherSuite();
            //SSL_NULL_WITH_NULL_NULL is the inital cipher - if it is still the selected then a successful handshake did not happen
            if (cipher != null && !cipher.equals("SSL_NULL_WITH_NULL_NULL")) {
                testLogger.log(Level.FINEST, this.rb.getResourceString("used.cipher", cipher));
            }
        }
        //hide dialog on esc
        ActionListener actionListenerESC = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jButtonClose.doClick();
            }
        };
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getRootPane().registerKeyboardAction(actionListenerESC, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void setMultiresolutionIcons() {
        this.jLabelIcon.setIcon(new ImageIcon(IMAGE_CONNECTIONTEST.toMinResolution(32)));
        this.jButtonImportCertificates.setIcon(new ImageIcon(IMAGE_IMPORT.toMinResolution(24)));
    }

    /**
     * Checks if the downloaded certificates are available in the local TLS
     * keystore
     *
     */
    private boolean remoteCertificatesAreAvailable(CertificateManager certManagerSSL,
            X509Certificate[] remoteCertificates) {
        boolean exists = true;
        for (X509Certificate remoteCertificate : remoteCertificates) {
            KeystoreCertificate testCert = new KeystoreCertificate();
            testCert.setCertificate(remoteCertificate);
            String fingerPrint = testCert.getFingerPrintSHA1();
            if (certManagerSSL.getKeystoreCertificateByFingerprintSHA1(fingerPrint) == null) {
                exists = false;
                break;
            }
        }
        return (exists);
    }

    /**
     * Sets some default colors for the logger levels. Overwrite these colors
     * using the setColor method
     *
     */
    public void setDefaultLogColors(JTextPaneLoggingHandler handler) {
        handler.setColor(Level.SEVERE, COLOR_RED);
        handler.setColor(Level.WARNING, COLOR_BROWN);
        handler.setColor(Level.INFO, COLOR_BLACK);
        handler.setColor(Level.CONFIG, COLOR_DARK_GREEN);
        handler.setColor(Level.FINE, COLOR_BLUE);
        handler.setColor(Level.FINER, COLOR_BLUE);
        handler.setColor(Level.FINEST, COLOR_LIGHT_GRAY);
    }

    private void viewCertificates() {
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        List<X509Certificate> certList = new ArrayList<X509Certificate>();
        certList.addAll(Arrays.asList(this.result.getFoundCertificates()));
        JDialogInfoOnExternalCertificate infoDialog = new JDialogInfoOnExternalCertificate(parent, certList, this.certManagerSSL);
        infoDialog.setVisible(true);
        //user pressed cancel: bail out
        while (infoDialog.importPressed()) {
            int selectedCertificateIndex = infoDialog.getCertificateIndex();
            KeyStoreUtil util = new KeyStoreUtil();
            Provider provBC = new BouncyCastleProvider();
            X509Certificate importCertificate = certList.get(selectedCertificateIndex);
            try {
                String alias = util.importX509Certificate(this.certManagerSSL.getKeystore(), importCertificate, provBC);
                this.certManagerSSL.saveKeystore();
                this.certManagerSSL.rereadKeystoreCertificates();
                UINotification.instance().addNotification(null,
                        UINotification.TYPE_SUCCESS,
                        this.rbCerts.getResourceString("certificate.import.success.title"),
                        this.rbCerts.getResourceString("certificate.import.success.message", alias));
            } catch (Throwable e) {
                e.printStackTrace();
                UINotification.instance().addNotification(null,
                        UINotification.TYPE_SUCCESS,
                        this.rbCerts.getResourceString("certificate.import.error.title"),
                        this.rbCerts.getResourceString("certificate.import.error.message", e.getMessage()));
            }
            if (certList.size() > 1) {
                infoDialog.setVisible(true);
            } else {
                break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane = new javax.swing.JSplitPane();
        jPanelOverview = new javax.swing.JPanel();
        jLabelIcon = new javax.swing.JLabel();
        jLabelHeader = new javax.swing.JLabel();
        jLabelIPConnection = new javax.swing.JLabel();
        jLabelRemoteCertificatesAvailableLocal = new javax.swing.JLabel();
        jLabelRemoteOFTPService = new javax.swing.JLabel();
        jLabelConnectionState = new javax.swing.JLabel();
        jLabelCertificateState = new javax.swing.JLabel();
        jLabelOFTPServiceState = new javax.swing.JLabel();
        jPanelSpace = new javax.swing.JPanel();
        jButtonImportCertificates = new javax.swing.JButton();
        jPanelLog = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        jTextPaneLog = new javax.swing.JTextPane();
        jPanelButton = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jSplitPane.setDividerLocation(235);
        jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanelOverview.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelOverview.setLayout(new java.awt.GridBagLayout());

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/clientserver/connectiontest/gui/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 10, 10);
        jPanelOverview.add(jLabelIcon, gridBagConstraints);

        jLabelHeader.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelHeader.setText("<Header with URL>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 10, 5);
        jPanelOverview.add(jLabelHeader, gridBagConstraints);

        jLabelIPConnection.setText(this.rb.getResourceString("label.connection.established"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 20, 5);
        jPanelOverview.add(jLabelIPConnection, gridBagConstraints);

        jLabelRemoteCertificatesAvailableLocal.setText(this.rb.getResourceString("label.certificates.available.local"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 20, 5);
        jPanelOverview.add(jLabelRemoteCertificatesAvailableLocal, gridBagConstraints);

        jLabelRemoteOFTPService.setText(this.rb.getResourceString("label.running.oftpservice"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 20, 5);
        jPanelOverview.add(jLabelRemoteOFTPService, gridBagConstraints);

        jLabelConnectionState.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelConnectionState.setText("<STATE>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 20, 5);
        jPanelOverview.add(jLabelConnectionState, gridBagConstraints);

        jLabelCertificateState.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelCertificateState.setText("<STATE>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 20, 5);
        jPanelOverview.add(jLabelCertificateState, gridBagConstraints);

        jLabelOFTPServiceState.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelOFTPServiceState.setText("<STATE>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 20, 5);
        jPanelOverview.add(jLabelOFTPServiceState, gridBagConstraints);

        jPanelSpace.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelSpace.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelOverview.add(jPanelSpace, gridBagConstraints);

        jButtonImportCertificates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/clientserver/connectiontest/gui/missing_image24x24.gif"))); // NOI18N
        jButtonImportCertificates.setText(this.rb.getResourceString( "button.viewcert"));
        jButtonImportCertificates.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonImportCertificates.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonImportCertificates.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonImportCertificates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportCertificatesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanelOverview.add(jButtonImportCertificates, gridBagConstraints);

        jSplitPane.setLeftComponent(jPanelOverview);

        jPanelLog.setLayout(new java.awt.GridBagLayout());

        jTextPaneLog.setEditable(false);
        jScrollPane.setViewportView(jTextPaneLog);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelLog.add(jScrollPane, gridBagConstraints);

        jSplitPane.setRightComponent(jPanelLog);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jSplitPane, gridBagConstraints);

        jPanelButton.setLayout(new java.awt.GridBagLayout());

        jButtonClose.setText(this.rb.getResourceString( "button.close"));
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelButton.add(jButtonClose, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanelButton, gridBagConstraints);

        setSize(new java.awt.Dimension(929, 656));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonImportCertificatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportCertificatesActionPerformed
        this.viewCertificates();
    }//GEN-LAST:event_jButtonImportCertificatesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonImportCertificates;
    private javax.swing.JLabel jLabelCertificateState;
    private javax.swing.JLabel jLabelConnectionState;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelIPConnection;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelOFTPServiceState;
    private javax.swing.JLabel jLabelRemoteCertificatesAvailableLocal;
    private javax.swing.JLabel jLabelRemoteOFTPService;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JPanel jPanelLog;
    private javax.swing.JPanel jPanelOverview;
    private javax.swing.JPanel jPanelSpace;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JTextPane jTextPaneLog;
    // End of variables declaration//GEN-END:variables
}
