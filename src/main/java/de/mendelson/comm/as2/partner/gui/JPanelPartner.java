//$Header: /mec_as2/de/mendelson/comm/as2/partner/gui/JPanelPartner.java 149   18.12.20 14:25 Heller $
package de.mendelson.comm.as2.partner.gui;

import de.mendelson.comm.as2.client.AS2Gui;
import de.mendelson.comm.as2.client.AS2StatusBar;
import de.mendelson.comm.as2.client.ListCellRendererEncryption;
import de.mendelson.comm.as2.client.ListCellRendererSignature;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.store.MessageStoreHandler;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerCertificateInformation;
import de.mendelson.comm.as2.partner.PartnerEventInformation;
import de.mendelson.comm.as2.partner.PartnerHttpHeader;
import de.mendelson.comm.as2.partner.PartnerSystem;
import de.mendelson.comm.as2.partner.gui.event.JDialogConfigureEventMoveToDir;
import de.mendelson.comm.as2.partner.gui.event.JDialogConfigureEventMoveToPartner;
import de.mendelson.comm.as2.partner.gui.event.JDialogConfigureEventShell;
import de.mendelson.comm.as2.partner.gui.event.ResourceBundlePartnerEvent;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.send.HttpConnectionParameter;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewRequest;
import de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewResponse;
import de.mendelson.util.clientserver.clients.preferences.PreferencesClient;
import de.mendelson.util.clientserver.connectiontest.clientserver.ConnectionTestRequest;
import de.mendelson.util.clientserver.connectiontest.clientserver.ConnectionTestResponse;
import de.mendelson.util.clientserver.connectiontest.gui.JDialogConnectionTestResult;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.ListCellRendererCertificates;
import de.mendelson.util.uinotification.UINotification;
import java.awt.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import de.mendelson.util.wizard.category.Category;
import de.mendelson.util.wizard.category.JDialogCategorySelection;
import de.mendelson.util.wizard.category.Subcategory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Panel to edit a single partner
 *
 * @author S.Heller
 * @version $Revision: 149 $
 */
public class JPanelPartner extends JPanel {

    private final String STR_CONTENT_TRANSFER_ENCODING_BINARY = "binary";
    private final String STR_CONTENT_TRANSFER_ENCODING_BASE64 = "base64";
    /**
     * Localize your GUI!
     */
    private MecResourceBundle rb = null;
    private MecResourceBundle rbEvents = null;
    /**
     * Partner to edit
     */
    private Partner partner = null;
    private DefaultMutableTreeNode partnerNode = null;
    private JTreePartner tree = null;
    private CertificateManager certificateManagerEncSign;
    private CertificateManager certificateManagerSSL;
    private JButtonPartnerConfigOk buttonOk = null;
    private PreferencesClient preferences;
    private Logger logger = Logger.getLogger("de.mendelson.as2.client");
    private boolean displayNotificationPanel = false;
    private boolean displayHttpHeaderPanel = false;
    private BaseClient baseClient;
    private AS2StatusBar statusbar;
    private String serverSideFileSeparator = "/";
    private String serverSideMessageDirectoryAbsolute = "messages";
    /**Store all available partner system requests*/
    private final Map<String, PartnerSystem> partnerSystemMap = new HashMap <String, PartnerSystem>();
    /**
     * Stores the last selection of the tab panels if a new partner is set
     */
    private Component lastSelectedPanel = null;

    private final static MendelsonMultiResolutionImage IMAGE_DELETE
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/delete.svg", 24, 48);
    private final static MendelsonMultiResolutionImage IMAGE_ADD
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/add.svg", 24, 48);
    private final static MendelsonMultiResolutionImage IMAGE_EDIT
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/event/edit.svg", 24, 48);
    private final static MendelsonMultiResolutionImage IMAGE_TESTCONNECTION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/testconnection.svg", 24, 48);
    private final static MendelsonMultiResolutionImage IMAGE_SYNC_MDN
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/sync_mdn.svg", 90, 130);
    private final static MendelsonMultiResolutionImage IMAGE_ASYNC_MDN
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/async_mdn.svg", 90, 130);

    /**
     * Creates new form JPanelFunctionGraph
     */
    public JPanelPartner(BaseClient baseClient, JTreePartner tree,
            CertificateManager certificateManagerEncSign,
            CertificateManager certificateManagerSSL,
            JButtonPartnerConfigOk buttonOk,
            AS2StatusBar statusbar, boolean changesAllowed,
            List<PartnerSystem> partnerSystemList) {
        this.statusbar = statusbar;
        this.baseClient = baseClient;
        this.tree = tree;
        this.buttonOk = buttonOk;
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePartnerPanel.class.getName());
            this.rbEvents = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePartnerEvent.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.preferences = new PreferencesClient(baseClient);
        this.initComponents();
        this.setMultiresolutionIcons();
        this.buttonOk.initialize(tree, this.jTextFieldName, this.jTextFieldId, this.jTextFieldURL, this.jTextFieldMDNURL,
                changesAllowed);
        //some disabled checkboxes should still have black text: wrapp their text in html tags
        this.jCheckBoxEdiintFeaturesCEM.setText("<html>" + this.jCheckBoxEdiintFeaturesCEM.getText() + "</html>");
        this.jCheckBoxEdiintFeaturesCompression.setText("<html>" + this.jCheckBoxEdiintFeaturesCompression.getText() + "</html>");
        this.jCheckBoxEdiintFeaturesMA.setText("<html>" + this.jCheckBoxEdiintFeaturesMA.getText() + "</html>");
        this.jTextAreaPartnerSystemInformation.setText(this.rb.getResourceString("partnerinfo"));
        this.jComboBoxContentTransferEncoding.removeAllItems();
        this.jComboBoxContentTransferEncoding.addItem(STR_CONTENT_TRANSFER_ENCODING_BINARY);
        this.jComboBoxContentTransferEncoding.addItem(STR_CONTENT_TRANSFER_ENCODING_BASE64);
        this.jComboBoxHTTPProtocolVersion.removeAllItems();
        this.jComboBoxHTTPProtocolVersion.addItem(HttpConnectionParameter.HTTP_1_0);
        this.jComboBoxHTTPProtocolVersion.addItem(HttpConnectionParameter.HTTP_1_1);
        this.certificateManagerEncSign = certificateManagerEncSign;
        this.certificateManagerSSL = certificateManagerSSL;
        this.jComboBoxSignType.setRenderer(new ListCellRendererSignature());
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_NONE));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA1));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_MD5));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA256));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA384));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA512));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA1_RSASSA_PSS));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA256_RSASSA_PSS));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA384_RSASSA_PSS));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA512_RSASSA_PSS));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_224));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_256));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_384));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_512));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_224_RSASSA_PSS));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_256_RSASSA_PSS));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_384_RSASSA_PSS));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA3_512_RSASSA_PSS));
        this.jComboBoxEncryptionType.setRenderer(new ListCellRendererEncryption());
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_NONE));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_3DES));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_40));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_64));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_128));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_196));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_128));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_192));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_256));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_128_RSAES_AOEP));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_192_RSAES_AOEP));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_256_RSAES_AOEP));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC4_40));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC4_56));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC4_128));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_DES));
        List<KeystoreCertificate> encSignCertificateList = this.certificateManagerEncSign.getKeyStoreCertificateList();
        //clone the array
        List<KeystoreCertificate> sortedEncSignCertificateList = new ArrayList<KeystoreCertificate>();
        sortedEncSignCertificateList.addAll(encSignCertificateList);
        Collections.sort(sortedEncSignCertificateList);
        this.jComboBoxSignCert.setRenderer(new ListCellRendererCertificates());
        this.jComboBoxCryptCert.setRenderer(new ListCellRendererCertificates());
        for (KeystoreCertificate cert : sortedEncSignCertificateList) {
            this.jComboBoxSignCert.addItem(cert);
            this.jComboBoxCryptCert.addItem(cert);
        }
        this.jTextPanePartnerComment.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setComment(jTextPanePartnerComment.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setComment(jTextPanePartnerComment.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setComment(jTextPanePartnerComment.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }
        });
        this.jTextPanePartnerContact.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setContactAS2(jTextPanePartnerContact.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setContactAS2(jTextPanePartnerContact.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setContactAS2(jTextPanePartnerContact.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }
        });
        this.jTextPanePartnerAddress.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setContactCompany(jTextPanePartnerAddress.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setContactCompany(jTextPanePartnerAddress.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (partner != null) {
                    partner.setContactCompany(jTextPanePartnerAddress.getText());
                    informTreeModelNodeChanged();
                }
                setButtonState();
            }
        });
        this.jTableHttpHeader.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jButtonHttpHeaderRemove.setEnabled(jTableHttpHeader.getSelectedRow() >= 0);
            }
        });
        this.jTableHttpHeader.getTableHeader().setReorderingAllowed(false);
        //figure out the server side file separator
        FileSystemViewRequest request = new FileSystemViewRequest(FileSystemViewRequest.TYPE_GET_FILE_SEPARATOR);
        FileSystemViewResponse response = (FileSystemViewResponse) this.baseClient.sendSync(request);
        this.serverSideFileSeparator = response.getParameterString();
        this.serverSideMessageDirectoryAbsolute = this.preferences.get(PreferencesAS2.DIR_MSG);
        String[] dirRequestResult = this.getAbsolutePathOnServerSide(this.serverSideMessageDirectoryAbsolute);
        this.serverSideMessageDirectoryAbsolute = dirRequestResult[0];
        //build cache for the PartnerSystems
        for( PartnerSystem partnerSystem:partnerSystemList){
            this.partnerSystemMap.put( partnerSystem.getPartner().getAS2Identification(), partnerSystem);
        }
    }

    /**
     * Updates the text in a TextPane without triggering a document listener
     *
     * @param textPane
     */
    private void setUIValueWithoutEvent(JTextPane textPane, String text) {
        if (textPane.getDocument() instanceof AbstractDocument) {
            DocumentListener[] listeners = ((AbstractDocument) textPane.getDocument()).getDocumentListeners();
            for (DocumentListener listener : listeners) {
                textPane.getDocument().removeDocumentListener(listener);
            }
            textPane.setText(text);
            for (DocumentListener listener : listeners) {
                textPane.getDocument().addDocumentListener(listener);
            }
        } else {
            //unable to remove the listeners..
            textPane.setText(text);
        }
    }

    /**
     * Sets a checkbox value without triggering an event
     *
     * @param checkbox to select/deselect
     */
    private void setUIValueWithoutEvent(JCheckBox checkbox, boolean state) {
        ActionListener[] actionListener = checkbox.getActionListeners();
        for (ActionListener listener : actionListener) {
            checkbox.removeActionListener(listener);
        }
        checkbox.setSelected(state);
        for (ActionListener listener : actionListener) {
            checkbox.addActionListener(listener);
        }
    }

    /**
     * Sets a combo box value value without triggering an event
     *
     * @param combobox to set the item in
     */
    private void setUIValueWithoutEvent(JComboBox combobox, Object item) {
        ActionListener[] actionListener = combobox.getActionListeners();
        for (ActionListener listener : actionListener) {
            combobox.removeActionListener(listener);
        }
        combobox.setSelectedItem(item);
        for (ActionListener listener : actionListener) {
            combobox.addActionListener(listener);
        }
    }

    /**
     * Sets a radio button value value without triggering an event
     *
     * @param combobox to set the item in
     */
    private void setUIValueWithoutEvent(JRadioButton radioButton, boolean state) {
        ActionListener[] actionListener = radioButton.getActionListeners();
        for (ActionListener listener : actionListener) {
            radioButton.removeActionListener(listener);
        }
        radioButton.setSelected(state);
        for (ActionListener listener : actionListener) {
            radioButton.addActionListener(listener);
        }
    }

    private void setMultiresolutionIcons() {
        this.jButtonHttpHeaderAdd.setIcon(new ImageIcon(IMAGE_ADD.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jButtonHttpHeaderRemove.setIcon(new ImageIcon(IMAGE_DELETE.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jButtonTestConnection.setIcon(new ImageIcon(IMAGE_TESTCONNECTION.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jLabelIconAsyncMDN.setIcon(new ImageIcon(IMAGE_ASYNC_MDN.toMinResolution(90)));
        this.jLabelIconSyncMDN.setIcon(new ImageIcon(IMAGE_SYNC_MDN.toMinResolution(90)));
        this.jButtonAddEventOnReceipt.setIcon(new ImageIcon(IMAGE_ADD.toMinResolution(20)));
        this.jButtonEditEventOnReceipt.setIcon(new ImageIcon(IMAGE_EDIT.toMinResolution(20)));
        this.jButtonAddEventOnSendError.setIcon(new ImageIcon(IMAGE_ADD.toMinResolution(20)));
        this.jButtonEditEventOnSendError.setIcon(new ImageIcon(IMAGE_EDIT.toMinResolution(20)));
        this.jButtonAddEventOnSendSuccess.setIcon(new ImageIcon(IMAGE_ADD.toMinResolution(20)));
        this.jButtonEditEventOnSendSuccess.setIcon(new ImageIcon(IMAGE_EDIT.toMinResolution(20)));

    }

    private void testConnection() {
        final String uniqueId = this.getClass().getName() + ".testConnection." + System.currentTimeMillis();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                JDialogPartnerConfig parentDialog = (JDialogPartnerConfig) SwingUtilities.getAncestorOfClass(JDialogPartnerConfig.class, JPanelPartner.this);
                try {
                    parentDialog.lock();
                    //display wait indicator
                    JPanelPartner.this.statusbar.startProgressIndeterminate(
                            JPanelPartner.this.rb.getResourceString("label.test.connection"), uniqueId);
                    String urlStr = JPanelPartner.this.jTextFieldURL.getText();
                    URL url = new URL(urlStr);
                    int port = 80;
                    if (url.getPort() > 0) {
                        //will be -1 by default if no specified...
                        port = url.getPort();
                    }
                    //get connection timeout from server preferences
                    long connectionTimeoutInMS = JPanelPartner.this.preferences.getInt(PreferencesAS2.HTTP_SEND_TIMEOUT);
                    ConnectionTestRequest request = new ConnectionTestRequest(url.getHost(),
                            port, url.getProtocol().equalsIgnoreCase("https"));
                    request.setTimeout(connectionTimeoutInMS);
                    request.setPartnerName(JPanelPartner.this.jTextFieldName.getText());
                    ConnectionTestResponse response = (ConnectionTestResponse) JPanelPartner.this.baseClient.sendSync(request);
                    if (response.getException() != null) {
                        throw response.getException();
                    }
                    JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, JPanelPartner.this);
                    JDialogConnectionTestResult dialog = new JDialogConnectionTestResult(parent,
                            JDialogConnectionTestResult.CONNECTION_TEST_AS2,
                            response.getLogEntries(),
                            response.getResult(),
                            JPanelPartner.this.certificateManagerEncSign, JPanelPartner.this.certificateManagerSSL);
                    JPanelPartner.this.statusbar.stopProgressIfExists(uniqueId);
                    parentDialog.unlock();
                    dialog.setVisible(true);
                } catch (Throwable e) {
                    JPanelPartner.this.statusbar.stopProgressIfExists(uniqueId);
                    UINotification.instance().addNotification(e);
                } finally {
                    JPanelPartner.this.statusbar.stopProgressIfExists(uniqueId);
                    parentDialog.unlock();
                }
            }
        };
        Executors.newSingleThreadExecutor().submit(runnable);
    }

    public void setDisplayNotificationPanel(boolean display) {
        this.displayNotificationPanel = display;
    }

    public void setDisplayHttpHeaderPanel(boolean display) {
        this.displayHttpHeaderPanel = display;
    }

    /**
     * Informs the partner tree model that a node value has been changed
     */
    private void informTreeModelNodeChanged() {
        ((DefaultTreeModel) this.tree.getModel()).nodeChanged(this.partnerNode);
    }

    /**
     * Edits a passed partner
     */
    public void setPartner(Partner partner, DefaultMutableTreeNode selectedNode) {
        long startTime = System.currentTimeMillis();
        if (this.lastSelectedPanel == null) {
            this.lastSelectedPanel = this.jPanelMisc;
        } else {
            this.lastSelectedPanel = this.jTabbedPane.getSelectedComponent();
        }
        this.partnerNode = selectedNode;
        this.partner = partner;
        this.buttonOk.setPartner(partner);
        this.buttonOk.computeErrorState();
        this.jTextFieldId.setText(partner.getAS2Identification());
        this.jTextFieldName.setText(partner.getName());
        this.jTextFieldURL.setText(partner.getURL());
        this.jTextFieldMDNURL.setText(partner.getMdnURL());
        this.jTextFieldEMail.setText(partner.getEmail());
        this.setUIValueWithoutEvent(this.jCheckBoxLocalStation, partner.isLocalStation());
        this.setUIValueWithoutEvent(this.jComboBoxSignCert, this.certificateManagerEncSign.getKeystoreCertificateByFingerprintSHA1(
                partner.getSignFingerprintSHA1()));
        this.setUIValueWithoutEvent(this.jComboBoxCryptCert, this.certificateManagerEncSign.getKeystoreCertificateByFingerprintSHA1(
                partner.getCryptFingerprintSHA1()));
        if (partner.isLocalStation()) {
            this.jLabelCryptAlias.setText(this.rb.getResourceString("label.cryptalias.key"));
            this.jLabelSignAlias.setText(this.rb.getResourceString("label.signalias.key"));
        } else {
            this.jLabelCryptAlias.setText(this.rb.getResourceString("label.cryptalias.cert"));
            this.jLabelSignAlias.setText(this.rb.getResourceString("label.signalias.cert"));
        }
        this.setUIValueWithoutEvent(this.jComboBoxSignType, Integer.valueOf(partner.getSignType()));
        this.setUIValueWithoutEvent(this.jComboBoxEncryptionType, Integer.valueOf(partner.getEncryptionType()));
        this.jTextFieldSubject.setText(partner.getSubject());
        this.jTextFieldContentType.setText(partner.getContentType());
        this.setUIValueWithoutEvent(this.jRadioButtonSyncMDN, partner.isSyncMDN());
        this.setUIValueWithoutEvent(this.jRadioButtonAsyncMDN, !partner.isSyncMDN());
        this.jLabelIconSyncMDN.setEnabled(partner.isSyncMDN());
        this.jLabelIconAsyncMDN.setEnabled(!partner.isSyncMDN());
        this.setUIValueWithoutEvent(this.jCheckBoxSignedMDN, partner.isSignedMDN());
        this.updatePollDirDisplay(this.partner);
        String pollIgnoreList = this.partner.getPollIgnoreListAsString();
        if (pollIgnoreList == null) {
            this.jTextFieldIgnorePollFilterList.setText("");
        } else {
            this.jTextFieldIgnorePollFilterList.setText(pollIgnoreList);
        }
        this.jTextFieldPollMaxFiles.setText(String.valueOf(this.partner.getMaxPollFiles()));
        this.jTextFieldPollInterval.setText(String.valueOf(this.partner.getPollInterval()));
        this.setUIValueWithoutEvent(this.jCheckBoxCompress, this.partner.getCompressionType() == AS2Message.COMPRESSION_ZLIB);
        this.setUIValueWithoutEvent(this.jCheckBoxHttpAuth, this.partner.getAuthentication().isEnabled());
        this.jTextFieldHttpAuthUser.setText(this.partner.getAuthentication().getUser());
        this.jPasswordFieldHttpPass.setText(this.partner.getAuthentication().getPassword());
        this.setUIValueWithoutEvent(this.jCheckBoxHttpAuthAsyncMDN, this.partner.getAuthenticationAsyncMDN().isEnabled());
        this.jTextFieldHttpAuthAsyncMDNUser.setText(this.partner.getAuthenticationAsyncMDN().getUser());
        this.jPasswordFieldHttpPassAsyncMDN.setText(this.partner.getAuthenticationAsyncMDN().getPassword());
        this.setUIValueWithoutEvent(this.jCheckBoxKeepFilenameOnReceipt, this.partner.getKeepOriginalFilenameOnReceipt());
        if (this.partner.getComment() != null && this.partner.getComment().length() > 0) {
            this.setUIValueWithoutEvent(this.jTextPanePartnerComment, this.partner.getComment());
        } else if (this.jTextPanePartnerComment.getText().length() > 0) {
            this.setUIValueWithoutEvent(this.jTextPanePartnerComment, "");
        }
        if (this.partner.getContactCompany() != null && this.partner.getContactCompany().length() > 0) {
            this.setUIValueWithoutEvent(this.jTextPanePartnerAddress, this.partner.getContactCompany());
        } else if (this.jTextPanePartnerAddress.getText().length() > 0) {
            this.setUIValueWithoutEvent(this.jTextPanePartnerAddress, "");
        }
        if (this.partner.getContactAS2() != null && this.partner.getContactAS2().length() > 0) {
            this.setUIValueWithoutEvent(this.jTextPanePartnerContact, this.partner.getContactAS2());
        } else if (this.jTextPanePartnerContact.getText().length() > 0) {
            this.setUIValueWithoutEvent(this.jTextPanePartnerContact, "");
        }
        this.setUIValueWithoutEvent(this.jCheckBoxNotifySend, this.partner.isNotifySendEnabled());
        this.setUIValueWithoutEvent(this.jCheckBoxNotifyReceive, this.partner.isNotifyReceiveEnabled());
        this.setUIValueWithoutEvent(this.jCheckBoxNotifySendReceive, this.partner.isNotifySendReceiveEnabled());
        this.jTextFieldNotifySend.setText(String.valueOf(this.partner.getNotifySend()));
        this.jTextFieldNotifyReceive.setText(String.valueOf(this.partner.getNotifyReceive()));
        this.jTextFieldNotifySendReceive.setText(String.valueOf(this.partner.getNotifySendReceive()));
        if (this.partner.getContentTransferEncoding() == AS2Message.CONTENT_TRANSFER_ENCODING_BINARY) {
            this.setUIValueWithoutEvent(this.jComboBoxContentTransferEncoding, STR_CONTENT_TRANSFER_ENCODING_BINARY);
        } else {
            this.setUIValueWithoutEvent(this.jComboBoxContentTransferEncoding, STR_CONTENT_TRANSFER_ENCODING_BASE64);
        }
        this.updatePartnerSystemInformation(this.partner);
        if (this.displayHttpHeaderPanel) {
            ((TableModelHttpHeader) this.jTableHttpHeader.getModel()).passNewData(partner);
        }
        this.setUIValueWithoutEvent(this.jComboBoxHTTPProtocolVersion, partner.getHttpProtocolVersion());
        this.setUIValueWithoutEvent(this.jCheckBoxUseAlgorithmIdentifierProtectionAttribute,
                partner.getUseAlgorithmIdentifierProtectionAttribute());
        this.setUIValueWithoutEvent(this.jCheckBoxEnableDirPoll, partner.isEnableDirPoll());
        this.handleVisibilityStateOfWidgets();
        this.disableEnableWidgets();
        this.updateHttpAuthState();
        this.setPanelVisiblilityState();
        this.renderEvents();
        try {
            if (this.lastSelectedPanel != null) {
                this.jTabbedPane.setSelectedComponent(this.lastSelectedPanel);
            }
        } catch (Exception e) {
            //ignore, not every panel that was selected for the last partner must be available for this
            //partner
        }
    }

    /**
     * Sets the visibility state depending if the partner is local station or
     * not. Has to be called every time the local station state changes.
     */
    private void handleVisibilityStateOfWidgets() {
        this.jTextFieldMDNURL.setVisible(this.partner.isLocalStation());
        this.jLabelMDNDescription.setVisible(!this.partner.isLocalStation());
        this.jLabelMDNSignatureDescription.setVisible(!this.partner.isLocalStation());
        this.jLabelAsyncMDNDescription.setVisible(!this.partner.isLocalStation());
        this.jLabelSyncMDNDescription.setVisible(!this.partner.isLocalStation());
        this.jLabelMDNURL.setVisible(this.partner.isLocalStation());
        this.jLabelMDNURLHint.setVisible(this.partner.isLocalStation());
        this.jRadioButtonAsyncMDN.setVisible(!partner.isLocalStation());
        this.jLabelIconAsyncMDN.setVisible(!partner.isLocalStation());
        this.jLabelIconSyncMDN.setVisible(!partner.isLocalStation());
        this.jRadioButtonSyncMDN.setVisible(!partner.isLocalStation());
        this.jCheckBoxSignedMDN.setVisible(!partner.isLocalStation());
        this.jCheckBoxUseAlgorithmIdentifierProtectionAttribute.setVisible(!partner.isLocalStation());
    }

    private void disableEnableWidgets() {
        this.jTextFieldIgnorePollFilterList.setEnabled(this.partner.isEnableDirPoll());
        this.jTextFieldIgnorePollFilterList.setEditable(this.partner.isEnableDirPoll());
        this.jLabelIgnorePollFilterList.setEnabled(this.partner.isEnableDirPoll());
        this.jTextFieldPollInterval.setEnabled(this.partner.isEnableDirPoll());
        this.jTextFieldPollInterval.setEditable(this.partner.isEnableDirPoll());
        this.jLabelPollInterval.setEnabled(this.partner.isEnableDirPoll());
        this.jLabelPollMaxFiles.setEnabled(this.partner.isEnableDirPoll());
        this.jTextFieldPollMaxFiles.setEnabled(this.partner.isEnableDirPoll());
        this.jTextFieldPollMaxFiles.setEditable(this.partner.isEnableDirPoll());
        this.jLabelPollIntervalSeconds.setEnabled(this.partner.isEnableDirPoll());
        this.jLabelPollDir.setEnabled(this.partner.isEnableDirPoll());
        this.jTextFieldPollDir.setEnabled(this.partner.isEnableDirPoll());
    }

    private void setPanelVisiblilityState() {
        this.jTabbedPane.removeAll();
        this.jTabbedPane.addTab(this.rb.getResourceString("tab.misc"), this.jPanelMiscMain);
        this.jTabbedPane.addTab(this.rb.getResourceString("tab.security"), this.jPanelSecurityMain);
        if (!this.partner.isLocalStation()) {
            this.jTabbedPane.addTab(this.rb.getResourceString("tab.send"), this.jPanelSend);
        }
        this.jTabbedPane.addTab(this.rb.getResourceString("tab.mdn"), this.jPanelMDN);
        if (!this.partner.isLocalStation()) {
            this.jTabbedPane.addTab(this.rb.getResourceString("tab.dirpoll"), this.jPanelDirPoll);
            this.jTabbedPane.addTab(this.rb.getResourceString("tab.receipt"), this.jPanelReceipt);
            this.jTabbedPane.addTab(this.rb.getResourceString("tab.httpauth"), this.jPanelHTTPAuth);
            if (this.displayHttpHeaderPanel) {
                this.jTabbedPane.addTab(this.rb.getResourceString("tab.httpheader"), this.jPanelHTTPHeader);
            }
            if (this.displayNotificationPanel) {
                this.jTabbedPane.addTab(this.rb.getResourceString("tab.notification"), this.jPanelNotification);
            }
            this.jTabbedPane.addTab(this.rb.getResourceString("tab.events"), this.jPanelEvents);
            this.jTabbedPane.addTab(this.rb.getResourceString("tab.partnersystem"), this.jPanelPartnerSystem);
        }
    }

    /**
     * graphically updates the state of the input fields in the HTTP auth panel
     */
    private void updateHttpAuthState() {
        this.jTextFieldHttpAuthUser.setEditable(this.jCheckBoxHttpAuth.isSelected());
        this.jTextFieldHttpAuthUser.setEnabled(this.jCheckBoxHttpAuth.isSelected());
        this.jPasswordFieldHttpPass.setEditable(this.jCheckBoxHttpAuth.isSelected());
        this.jPasswordFieldHttpPass.setEnabled(this.jCheckBoxHttpAuth.isSelected());
        this.jTextFieldHttpAuthAsyncMDNUser.setEditable(this.jCheckBoxHttpAuthAsyncMDN.isSelected());
        this.jTextFieldHttpAuthAsyncMDNUser.setEnabled(this.jCheckBoxHttpAuthAsyncMDN.isSelected());
        this.jPasswordFieldHttpPassAsyncMDN.setEditable(this.jCheckBoxHttpAuthAsyncMDN.isSelected());
        this.jPasswordFieldHttpPassAsyncMDN.setEnabled(this.jCheckBoxHttpAuthAsyncMDN.isSelected());
    }

    /**
     * Updates the partner system information of the selected partner
     */
    private void updatePartnerSystemInformation(final Partner finalPartner) {
        PartnerSystem partnerSystem = this.partnerSystemMap.get(finalPartner.getAS2Identification());
        if (partnerSystem != null) {
            JPanelPartner.this.jTextFieldAS2Version.setText(partnerSystem.getAS2Version());
            JPanelPartner.this.jTextFieldProductName.setText(partnerSystem.getProductName());
            JPanelPartner.this.jCheckBoxEdiintFeaturesCompression.setSelected(partnerSystem.supportsCompression());
            JPanelPartner.this.jCheckBoxEdiintFeaturesCEM.setSelected(partnerSystem.supportsCEM());
            JPanelPartner.this.jCheckBoxEdiintFeaturesMA.setSelected(partnerSystem.supportsMA());
        } else {
            JPanelPartner.this.jTextFieldAS2Version.setText(JPanelPartner.this.rb.getResourceString("partnersystem.noinfo"));
            JPanelPartner.this.jTextFieldProductName.setText(JPanelPartner.this.rb.getResourceString("partnersystem.noinfo"));
            JPanelPartner.this.jCheckBoxEdiintFeaturesCompression.setSelected(false);
            JPanelPartner.this.jCheckBoxEdiintFeaturesCEM.setSelected(false);
            JPanelPartner.this.jCheckBoxEdiintFeaturesMA.setSelected(false);
        }
    }

    /**
     * Displays the directory that is assigned with the partner to be polled. It
     * must not be the same because the name may not be a valid filename
     */
    private void updatePollDirDisplay(final Partner finalPartner) {
        StringBuilder pollDirStr = new StringBuilder();
        pollDirStr.append(this.serverSideMessageDirectoryAbsolute);
        pollDirStr.append(this.serverSideFileSeparator);
        pollDirStr.append(MessageStoreHandler.convertToValidFilename(finalPartner.getName()));
        pollDirStr.append(this.serverSideFileSeparator);
        pollDirStr.append("outbox");
        //for single local stations display add the name of the local station, else display <localstation>
        List<Partner> localStations = this.tree.getLocalStations();
        String localStationDir = "<localstation>";
        if (localStations.size() == 1) {
            localStationDir = MessageStoreHandler.convertToValidFilename(localStations.get(0).getName());
        }
        this.jTextFieldPollDir.setText(pollDirStr + serverSideFileSeparator + localStationDir);
    }

    /**
     * Asks the server for an absolute path on its side - this is useful if
     * client and server are running on different OS or if the client/server
     * path structure is not the same
     *
     * @param directory
     * @return A string array of the size 2: 0: path, 1: path separator
     */
    private String[] getAbsolutePathOnServerSide(String directory) {
        FileSystemViewRequest request = new FileSystemViewRequest(FileSystemViewRequest.TYPE_GET_ABSOLUTE_PATH_STR);
        request.setRequestFilePath(directory);
        FileSystemViewResponse response = (FileSystemViewResponse) this.baseClient.sendSync(request);
        return (new String[]{response.getParameterString(), response.getServerSideFileSeparator()});
    }

    private void setButtonState() {
        if (this.partner != null) {
            this.jTextFieldURL.setEditable(!this.partner.isLocalStation());
            this.jTextFieldURL.setEnabled(!this.partner.isLocalStation());
            this.jTextFieldEMail.setEnabled(this.partner.isLocalStation());
            this.jTextFieldEMail.setEditable(this.partner.isLocalStation());
            this.jLabelCertSignType.setVisible(!this.partner.isLocalStation());
            this.jLabelEncryptionType.setVisible(!this.partner.isLocalStation());
            this.jComboBoxEncryptionType.setVisible(!this.partner.isLocalStation());
            this.jComboBoxSignType.setVisible(!this.partner.isLocalStation());
            this.jPanelSendMain.setVisible(!this.partner.isLocalStation());
            this.jPanelPollOptions.setVisible(!this.partner.isLocalStation());
            this.jPanelReceiptOptions.setVisible(!this.partner.isLocalStation());
            this.jPanelHttpAuthData.setVisible(!this.partner.isLocalStation());
            this.jTextFieldNotifySend.setEnabled(this.partner.isNotifySendEnabled());
            this.jTextFieldNotifySend.setEditable(this.partner.isNotifySendEnabled());
            this.jTextFieldNotifyReceive.setEnabled(this.partner.isNotifyReceiveEnabled());
            this.jTextFieldNotifyReceive.setEditable(this.partner.isNotifyReceiveEnabled());
            this.jTextFieldNotifySendReceive.setEnabled(this.partner.isNotifySendReceiveEnabled());
            this.jTextFieldNotifySendReceive.setEditable(this.partner.isNotifySendReceiveEnabled());
            this.renderEvents();
        }
    }

    /**
     * Renders the events for the selected partner - also refreshes the display
     * if there were any changes
     */
    private void renderEvents() {
        int processTypeOnReceipt = this.partner.getPartnerEvents().getProcess(PartnerEventInformation.TYPE_ON_RECEIPT);
        int processTypeOnSendSuccess = this.partner.getPartnerEvents().getProcess(PartnerEventInformation.TYPE_ON_SENDSUCCESS);
        int processTypeOnSendError = this.partner.getPartnerEvents().getProcess(PartnerEventInformation.TYPE_ON_SENDERROR);
        this.jLabelIconProcessTypeOnReceipt.setIcon(new ImageIcon(PartnerEventInformation.getImageForProcess(processTypeOnReceipt).toMinResolution(24)));
        this.jLabelIconProcessTypeOnSendError.setIcon(new ImageIcon(PartnerEventInformation.getImageForProcess(processTypeOnSendError).toMinResolution(24)));
        this.jLabelIconProcessTypeOnSendSuccess.setIcon(new ImageIcon(PartnerEventInformation.getImageForProcess(processTypeOnSendSuccess).toMinResolution(24)));
        List<String> onReceiptParameter = this.partner.getPartnerEvents().getParameter(PartnerEventInformation.TYPE_ON_RECEIPT);
        if (onReceiptParameter.isEmpty()) {
            this.jTextFieldEventInfoOnReceipt.setText("");
        } else {
            if (processTypeOnReceipt == PartnerEventInformation.PROCESS_MOVE_TO_PARTNER) {
                String as2Id = onReceiptParameter.get(0);
                List<Partner> partnerList = this.tree.getAllPartner();
                Partner foundPartner = null;
                for (Partner testPartner : partnerList) {
                    if (testPartner.getAS2Identification().equals(as2Id)) {
                        foundPartner = testPartner;
                        break;
                    }
                }
                if (foundPartner == null) {
                    this.jTextFieldEventInfoOnReceipt.setText("<UNKNOWN>");
                } else {
                    this.jTextFieldEventInfoOnReceipt.setText(foundPartner.toString());
                }
            } else {
                this.jTextFieldEventInfoOnReceipt.setText(onReceiptParameter.get(0));
            }
        }
        this.jCheckBoxUseEventOnReceipt.setSelected(this.partner.getPartnerEvents().useOnReceipt());
        this.jButtonAddEventOnReceipt.setEnabled(this.jCheckBoxUseEventOnReceipt.isSelected());
        this.jButtonEditEventOnReceipt.setEnabled(this.jCheckBoxUseEventOnReceipt.isSelected());
        this.jLabelIconProcessTypeOnReceipt.setEnabled(this.jCheckBoxUseEventOnReceipt.isSelected());
        List<String> onSendErrorParameter = this.partner.getPartnerEvents().getParameter(PartnerEventInformation.TYPE_ON_SENDERROR);
        if (onSendErrorParameter.isEmpty()) {
            this.jTextFieldEventInfoOnSendError.setText("");
        } else {
            if (processTypeOnSendError == PartnerEventInformation.PROCESS_MOVE_TO_PARTNER) {
                String as2Id = onSendErrorParameter.get(0);
                List<Partner> partnerList = this.tree.getAllPartner();
                Partner foundPartner = null;
                for (Partner testPartner : partnerList) {
                    if (testPartner.getAS2Identification().equals(as2Id)) {
                        foundPartner = testPartner;
                        break;
                    }
                }
                if (foundPartner == null) {
                    this.jTextFieldEventInfoOnSendError.setText("<UNKNOWN>");
                } else {
                    this.jTextFieldEventInfoOnSendError.setText(foundPartner.toString());
                }
            } else {
                this.jTextFieldEventInfoOnSendError.setText(onSendErrorParameter.get(0));
            }
        }
        this.jCheckBoxUseEventOnSendError.setSelected(this.partner.getPartnerEvents().useOnSenderror());
        this.jButtonAddEventOnSendError.setEnabled(this.jCheckBoxUseEventOnSendError.isSelected());
        this.jButtonEditEventOnSendError.setEnabled(this.jCheckBoxUseEventOnSendError.isSelected());
        this.jLabelIconProcessTypeOnSendError.setEnabled(this.jCheckBoxUseEventOnSendError.isSelected());
        List<String> onSendSuccessParameter = this.partner.getPartnerEvents().getParameter(PartnerEventInformation.TYPE_ON_SENDSUCCESS);
        if (onSendSuccessParameter.isEmpty()) {
            this.jTextFieldEventInfoOnSendSuccess.setText("");
        } else {
            if (processTypeOnSendSuccess == PartnerEventInformation.PROCESS_MOVE_TO_PARTNER) {
                String as2Id = onSendSuccessParameter.get(0);
                List<Partner> partnerList = this.tree.getAllPartner();
                Partner foundPartner = null;
                for (Partner testPartner : partnerList) {
                    if (testPartner.getAS2Identification().equals(as2Id)) {
                        foundPartner = testPartner;
                        break;
                    }
                }
                if (foundPartner == null) {
                    this.jTextFieldEventInfoOnSendSuccess.setText("<UNKNOWN>");
                } else {
                    this.jTextFieldEventInfoOnSendSuccess.setText(foundPartner.toString());
                }
            } else {
                this.jTextFieldEventInfoOnSendSuccess.setText(onSendSuccessParameter.get(0));
            }
        }
        this.jCheckBoxUseEventOnSendSuccess.setSelected(this.partner.getPartnerEvents().useOnSendsuccess());
        this.jButtonAddEventOnSendSuccess.setEnabled(this.jCheckBoxUseEventOnSendSuccess.isSelected());
        this.jButtonEditEventOnSendSuccess.setEnabled(this.jCheckBoxUseEventOnSendSuccess.isSelected());
        this.jLabelIconProcessTypeOnSendSuccess.setEnabled(this.jCheckBoxUseEventOnSendSuccess.isSelected());
    }

    /**
     * Creates a new process and configures it for the passed event type
     *
     * @param EVENT_TYPE
     */
    private void createProcess(final int EVENT_TYPE) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String actionCommand = evt.getActionCommand();
                try {
                    if (actionCommand.equals(rbEvents.getResourceString("process.executeshell"))) {
                        editEvent(EVENT_TYPE, PartnerEventInformation.PROCESS_EXECUTE_SHELL);
                    } else if (actionCommand.equals(rbEvents.getResourceString("process.movetopartner"))) {
                        editEvent(EVENT_TYPE, PartnerEventInformation.PROCESS_MOVE_TO_PARTNER);
                    } else if (actionCommand.equals(rbEvents.getResourceString("process.movetodirectory"))) {
                        editEvent(EVENT_TYPE, PartnerEventInformation.PROCESS_MOVE_TO_DIR);
                    }
                } catch (Exception e) {
                    UINotification.instance().addNotification(e);
                    e.printStackTrace();
                }
            }
        };
        JFrame parentFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class,
                this);
        JDialogCategorySelection dialog = new JDialogCategorySelection(parentFrame);
        dialog.setTitle(this.rbEvents.getResourceString("title.select.process",
                this.rbEvents.getResourceString("type." + EVENT_TYPE)));
        Category category = new Category();
        category.setTitle(this.rbEvents.getResourceString("tab.newprocess"));
        Subcategory subExecuteShell = new Subcategory();
        subExecuteShell.setActionCommand(
                this.rbEvents.getResourceString("process.executeshell"));
        subExecuteShell.setIcon(new ImageIcon(PartnerEventInformation.getImageForProcess(PartnerEventInformation.PROCESS_EXECUTE_SHELL).toMinResolution(36)));
        subExecuteShell.setTitle(this.rbEvents.getResourceString("process.executeshell"));
        subExecuteShell.setDescription(this.rbEvents.getResourceString("process.executeshell.description"));
        category.addSubcategory(subExecuteShell);
        Subcategory subMoveToPartner = new Subcategory();
        subMoveToPartner.setActionCommand(
                this.rbEvents.getResourceString("process.movetopartner"));
        subMoveToPartner.setIcon(new ImageIcon(PartnerEventInformation.getImageForProcess(PartnerEventInformation.PROCESS_MOVE_TO_PARTNER).toMinResolution(36)));
        subMoveToPartner.setTitle(this.rbEvents.getResourceString("process.movetopartner"));
        subMoveToPartner.setDescription(this.rbEvents.getResourceString("process.movetopartner.description"));
        category.addSubcategory(subMoveToPartner);
        Subcategory subMoveToDirectory = new Subcategory();
        subMoveToDirectory.setActionCommand(
                this.rbEvents.getResourceString("process.movetodirectory"));
        subMoveToDirectory.setIcon(new ImageIcon(PartnerEventInformation.getImageForProcess(PartnerEventInformation.PROCESS_MOVE_TO_DIR).toMinResolution(36)));
        subMoveToDirectory.setTitle(this.rbEvents.getResourceString("process.movetodirectory"));
        subMoveToDirectory.setDescription(this.rbEvents.getResourceString("process.movetodirectory.description"));
        category.addSubcategory(subMoveToDirectory);
        dialog.addCategory(category);
        dialog.addActionListener(actionListener);
        dialog.setVisible(true);
    }

    private void editEvent(final int EVENT_TYPE, final int PROCESS_TYPE) {
        JFrame parentFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class,
                JPanelPartner.this);
        if (PROCESS_TYPE == PartnerEventInformation.PROCESS_EXECUTE_SHELL) {
            JDialogConfigureEventShell dialog = new JDialogConfigureEventShell(
                    parentFrame, JPanelPartner.this.partner,
                    EVENT_TYPE);
            dialog.setVisible(true);
        } else if (PROCESS_TYPE == PartnerEventInformation.PROCESS_MOVE_TO_DIR) {
            JDialogConfigureEventMoveToDir dialog = new JDialogConfigureEventMoveToDir(
                    parentFrame, JPanelPartner.this.baseClient, JPanelPartner.this.partner,
                    EVENT_TYPE);
            dialog.setVisible(true);
        } else if (PROCESS_TYPE == PartnerEventInformation.PROCESS_MOVE_TO_PARTNER) {
            JDialogConfigureEventMoveToPartner dialog = new JDialogConfigureEventMoveToPartner(
                    parentFrame, this.tree.getAllPartner(), JPanelPartner.this.partner,
                    EVENT_TYPE);
            dialog.setVisible(true);
        }
        this.renderEvents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupSyncAsyncMDN = new javax.swing.ButtonGroup();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelSend = new javax.swing.JPanel();
        jPanelSendMain = new javax.swing.JPanel();
        jLabelURL = new javax.swing.JLabel();
        jTextFieldURL = new javax.swing.JTextField();
        jLabelSubject = new javax.swing.JLabel();
        jTextFieldSubject = new javax.swing.JTextField();
        jLabelContentType = new javax.swing.JLabel();
        jTextFieldContentType = new javax.swing.JTextField();
        jPanelSpace14 = new javax.swing.JPanel();
        jCheckBoxCompress = new javax.swing.JCheckBox();
        jLabelSendUrlHint = new javax.swing.JLabel();
        jLabelSubjectHint = new javax.swing.JLabel();
        jPanelSep = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jComboBoxContentTransferEncoding = new javax.swing.JComboBox();
        jLabelContentTransferEncoding = new javax.swing.JLabel();
        jLabelHTTPProtocolVersion = new javax.swing.JLabel();
        jComboBoxHTTPProtocolVersion = new javax.swing.JComboBox();
        jButtonTestConnection = new javax.swing.JButton();
        jPanelMDN = new javax.swing.JPanel();
        jPanelMDNMain = new javax.swing.JPanel();
        jLabelMDNURL = new javax.swing.JLabel();
        jTextFieldMDNURL = new javax.swing.JTextField();
        jPanelSpace99 = new javax.swing.JPanel();
        jRadioButtonSyncMDN = new javax.swing.JRadioButton();
        jRadioButtonAsyncMDN = new javax.swing.JRadioButton();
        jCheckBoxSignedMDN = new javax.swing.JCheckBox();
        jLabelMDNURLHint = new javax.swing.JLabel();
        jLabelIconSyncMDN = new javax.swing.JLabel();
        jLabelIconAsyncMDN = new javax.swing.JLabel();
        jLabelSyncMDNDescription = new javax.swing.JLabel();
        jLabelAsyncMDNDescription = new javax.swing.JLabel();
        jLabelMDNSignatureDescription = new javax.swing.JLabel();
        jLabelMDNDescription = new javax.swing.JLabel();
        jPanelDirPoll = new javax.swing.JPanel();
        jPanelPollOptions = new javax.swing.JPanel();
        jCheckBoxEnableDirPoll = new javax.swing.JCheckBox();
        jLabelPollDir = new javax.swing.JLabel();
        jTextFieldPollDir = new javax.swing.JTextField();
        jLabelPollInterval = new javax.swing.JLabel();
        jTextFieldPollInterval = new javax.swing.JTextField();
        jPanelSpaceX = new javax.swing.JPanel();
        jLabelPollIntervalSeconds = new javax.swing.JLabel();
        jLabelIgnorePollFilterList = new javax.swing.JLabel();
        jTextFieldIgnorePollFilterList = new javax.swing.JTextField();
        jLabelPollMaxFiles = new javax.swing.JLabel();
        jTextFieldPollMaxFiles = new javax.swing.JTextField();
        jPanelReceipt = new javax.swing.JPanel();
        jPanelReceiptOptions = new javax.swing.JPanel();
        jPanelSpace456 = new javax.swing.JPanel();
        jCheckBoxKeepFilenameOnReceipt = new javax.swing.JCheckBox();
        jLabelHintKeepFilenameOnReceipt = new javax.swing.JLabel();
        jPanelHTTPAuth = new javax.swing.JPanel();
        jPanelHttpAuthData = new javax.swing.JPanel();
        jCheckBoxHttpAuth = new javax.swing.JCheckBox();
        jLabelHttpAuth = new javax.swing.JLabel();
        jTextFieldHttpAuthUser = new javax.swing.JTextField();
        jLabelHttpPass = new javax.swing.JLabel();
        jPasswordFieldHttpPass = new javax.swing.JPasswordField();
        jCheckBoxHttpAuthAsyncMDN = new javax.swing.JCheckBox();
        jLabelHttpAuthAsyncMDN = new javax.swing.JLabel();
        jTextFieldHttpAuthAsyncMDNUser = new javax.swing.JTextField();
        jLabelHttpPassAsyncMDN = new javax.swing.JLabel();
        jPasswordFieldHttpPassAsyncMDN = new javax.swing.JPasswordField();
        jPanelSpace199 = new javax.swing.JPanel();
        jLabelHttpAuthDataInfo = new javax.swing.JLabel();
        jPanelHTTPHeader = new javax.swing.JPanel();
        jScrollPaneHttpHeader = new javax.swing.JScrollPane();
        jTableHttpHeader = new javax.swing.JTable();
        jButtonHttpHeaderAdd = new javax.swing.JButton();
        jButtonHttpHeaderRemove = new javax.swing.JButton();
        jPanelNotification = new javax.swing.JPanel();
        jPanelNotificationMain = new javax.swing.JPanel();
        jCheckBoxNotifySend = new javax.swing.JCheckBox();
        jCheckBoxNotifyReceive = new javax.swing.JCheckBox();
        jCheckBoxNotifySendReceive = new javax.swing.JCheckBox();
        jTextFieldNotifyReceive = new javax.swing.JTextField();
        jTextFieldNotifySend = new javax.swing.JTextField();
        jTextFieldNotifySendReceive = new javax.swing.JTextField();
        jPanelSpace23 = new javax.swing.JPanel();
        jPanelEvents = new javax.swing.JPanel();
        jPanelEventsMain = new javax.swing.JPanel();
        jCheckBoxUseEventOnSendError = new javax.swing.JCheckBox();
        jTextFieldEventInfoOnSendError = new javax.swing.JTextField();
        jCheckBoxUseEventOnSendSuccess = new javax.swing.JCheckBox();
        jTextFieldEventInfoOnSendSuccess = new javax.swing.JTextField();
        jCheckBoxUseEventOnReceipt = new javax.swing.JCheckBox();
        jTextFieldEventInfoOnReceipt = new javax.swing.JTextField();
        jPanelSpace = new javax.swing.JPanel();
        jButtonAddEventOnReceipt = new javax.swing.JButton();
        jButtonEditEventOnReceipt = new javax.swing.JButton();
        jLabelIconProcessTypeOnReceipt = new javax.swing.JLabel();
        jButtonEditEventOnSendError = new javax.swing.JButton();
        jButtonAddEventOnSendError = new javax.swing.JButton();
        jButtonEditEventOnSendSuccess = new javax.swing.JButton();
        jButtonAddEventOnSendSuccess = new javax.swing.JButton();
        jLabelIconProcessTypeOnSendError = new javax.swing.JLabel();
        jLabelIconProcessTypeOnSendSuccess = new javax.swing.JLabel();
        jPanelSpace123 = new javax.swing.JPanel();
        jPanelSpace124 = new javax.swing.JPanel();
        jPanelPartnerSystem = new javax.swing.JPanel();
        jPanelPartnerSystemMain = new javax.swing.JPanel();
        jLabelAS2Version = new javax.swing.JLabel();
        jLabelProductName = new javax.swing.JLabel();
        jLabelFeatures = new javax.swing.JLabel();
        jCheckBoxEdiintFeaturesCompression = new javax.swing.JCheckBox();
        jCheckBoxEdiintFeaturesMA = new javax.swing.JCheckBox();
        jCheckBoxEdiintFeaturesCEM = new javax.swing.JCheckBox();
        jTextFieldAS2Version = new javax.swing.JTextField();
        jTextFieldProductName = new javax.swing.JTextField();
        jPanelSpaceSpace = new javax.swing.JPanel();
        jScrollPaneTextAreaPartnerSystemInformation = new javax.swing.JScrollPane();
        jTextAreaPartnerSystemInformation = new javax.swing.JTextArea();
        jPanelMisc = new javax.swing.JPanel();
        jPanelMiscMain = new javax.swing.JPanel();
        jTextFieldId = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jCheckBoxLocalStation = new javax.swing.JCheckBox();
        jLabelEMail = new javax.swing.JLabel();
        jTextFieldEMail = new javax.swing.JTextField();
        jScrollPanePartnerComment = new javax.swing.JScrollPane();
        jTextPanePartnerComment = new javax.swing.JTextPane();
        jLabelPartnerComment = new javax.swing.JLabel();
        jScrollPanePartnerAddress = new javax.swing.JScrollPane();
        jTextPanePartnerContact = new javax.swing.JTextPane();
        jScrollPanePartnerContact = new javax.swing.JScrollPane();
        jTextPanePartnerAddress = new javax.swing.JTextPane();
        jLabelAddress = new javax.swing.JLabel();
        jLabelContact = new javax.swing.JLabel();
        jPanelSecurity = new javax.swing.JPanel();
        jPanelSecurityMain = new javax.swing.JPanel();
        jLabelSignAlias = new javax.swing.JLabel();
        jComboBoxSignCert = new javax.swing.JComboBox();
        jPanelSpace2 = new javax.swing.JPanel();
        jComboBoxSignType = new javax.swing.JComboBox();
        jLabelEncryptionType = new javax.swing.JLabel();
        jComboBoxEncryptionType = new javax.swing.JComboBox();
        jLabelCertSignType = new javax.swing.JLabel();
        jLabelCryptAlias = new javax.swing.JLabel();
        jComboBoxCryptCert = new javax.swing.JComboBox();
        jCheckBoxUseAlgorithmIdentifierProtectionAttribute = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        jTabbedPane.setMinimumSize(new java.awt.Dimension(10, 10));
        jTabbedPane.setPreferredSize(new java.awt.Dimension(10, 10));

        jPanelSend.setLayout(new java.awt.GridBagLayout());

        jPanelSendMain.setMinimumSize(new java.awt.Dimension(20, 20));
        jPanelSendMain.setLayout(new java.awt.GridBagLayout());

        jLabelURL.setText(this.rb.getResourceString( "label.url"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelSendMain.add(jLabelURL, gridBagConstraints);

        jTextFieldURL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldURLKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelSendMain.add(jTextFieldURL, gridBagConstraints);

        jLabelSubject.setText(this.rb.getResourceString( "label.subject"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelSendMain.add(jLabelSubject, gridBagConstraints);

        jTextFieldSubject.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSubjectKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelSendMain.add(jTextFieldSubject, gridBagConstraints);

        jLabelContentType.setText(this.rb.getResourceString( "label.contenttype"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelSendMain.add(jLabelContentType, gridBagConstraints);

        jTextFieldContentType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldContentTypeKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelSendMain.add(jTextFieldContentType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelSendMain.add(jPanelSpace14, gridBagConstraints);

        jCheckBoxCompress.setText(this.rb.getResourceString( "label.compression"));
        jCheckBoxCompress.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxCompress.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxCompress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCompressActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanelSendMain.add(jCheckBoxCompress, gridBagConstraints);

        jLabelSendUrlHint.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelSendUrlHint.setText(this.rb.getResourceString( "label.url.hint"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanelSendMain.add(jLabelSendUrlHint, gridBagConstraints);

        jLabelSubjectHint.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelSubjectHint.setText(this.rb.getResourceString( "hint.subject.replacement"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanelSendMain.add(jLabelSubjectHint, gridBagConstraints);

        jPanelSep.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanelSep.add(jSeparator1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanelSendMain.add(jPanelSep, gridBagConstraints);

        jComboBoxContentTransferEncoding.setMinimumSize(new java.awt.Dimension(70, 22));
        jComboBoxContentTransferEncoding.setPreferredSize(new java.awt.Dimension(70, 22));
        jComboBoxContentTransferEncoding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxContentTransferEncodingActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelSendMain.add(jComboBoxContentTransferEncoding, gridBagConstraints);

        jLabelContentTransferEncoding.setText("Content Transfer Encoding:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelSendMain.add(jLabelContentTransferEncoding, gridBagConstraints);

        jLabelHTTPProtocolVersion.setText(this.rb.getResourceString("label.httpversion"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelSendMain.add(jLabelHTTPProtocolVersion, gridBagConstraints);

        jComboBoxHTTPProtocolVersion.setMinimumSize(new java.awt.Dimension(50, 22));
        jComboBoxHTTPProtocolVersion.setPreferredSize(new java.awt.Dimension(50, 22));
        jComboBoxHTTPProtocolVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxHTTPProtocolVersionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelSendMain.add(jComboBoxHTTPProtocolVersion, gridBagConstraints);

        jButtonTestConnection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonTestConnection.setText(this.rb.getResourceString("label.test.connection"));
        jButtonTestConnection.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonTestConnection.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonTestConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestConnectionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanelSendMain.add(jButtonTestConnection, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelSend.add(jPanelSendMain, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.send"), jPanelSend);

        jPanelMDN.setLayout(new java.awt.GridBagLayout());

        jPanelMDNMain.setLayout(new java.awt.GridBagLayout());

        jLabelMDNURL.setText(this.rb.getResourceString( "label.mdnurl"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMDNMain.add(jLabelMDNURL, gridBagConstraints);

        jTextFieldMDNURL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldMDNURLKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelMDNMain.add(jTextFieldMDNURL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelMDNMain.add(jPanelSpace99, gridBagConstraints);

        buttonGroupSyncAsyncMDN.add(jRadioButtonSyncMDN);
        jRadioButtonSyncMDN.setSelected(true);
        jRadioButtonSyncMDN.setText(this.rb.getResourceString( "label.syncmdn"));
        jRadioButtonSyncMDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSyncMDNActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanelMDNMain.add(jRadioButtonSyncMDN, gridBagConstraints);

        buttonGroupSyncAsyncMDN.add(jRadioButtonAsyncMDN);
        jRadioButtonAsyncMDN.setText(this.rb.getResourceString( "label.asyncmdn"));
        jRadioButtonAsyncMDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAsyncMDNActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanelMDNMain.add(jRadioButtonAsyncMDN, gridBagConstraints);

        jCheckBoxSignedMDN.setText(this.rb.getResourceString( "label.signedmdn"));
        jCheckBoxSignedMDN.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxSignedMDN.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxSignedMDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSignedMDNActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 9, 5, 5);
        jPanelMDNMain.add(jCheckBoxSignedMDN, gridBagConstraints);

        jLabelMDNURLHint.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelMDNURLHint.setText(this.rb.getResourceString( "label.url.hint.mdn"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanelMDNMain.add(jLabelMDNURLHint, gridBagConstraints);

        jLabelIconSyncMDN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 2, 20, 15);
        jPanelMDNMain.add(jLabelIconSyncMDN, gridBagConstraints);

        jLabelIconAsyncMDN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 2, 20, 15);
        jPanelMDNMain.add(jLabelIconAsyncMDN, gridBagConstraints);

        jLabelSyncMDNDescription.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelSyncMDNDescription.setText(this.rb.getResourceString( "label.mdn.sync.description"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 5);
        jPanelMDNMain.add(jLabelSyncMDNDescription, gridBagConstraints);

        jLabelAsyncMDNDescription.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelAsyncMDNDescription.setText(this.rb.getResourceString( "label.mdn.async.description"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 5);
        jPanelMDNMain.add(jLabelAsyncMDNDescription, gridBagConstraints);

        jLabelMDNSignatureDescription.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelMDNSignatureDescription.setText(this.rb.getResourceString( "label.mdn.sign.description"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 5, 5);
        jPanelMDNMain.add(jLabelMDNSignatureDescription, gridBagConstraints);

        jLabelMDNDescription.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelMDNDescription.setText(this.rb.getResourceString( "label.mdn.description"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        jPanelMDNMain.add(jLabelMDNDescription, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelMDN.add(jPanelMDNMain, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.mdn"), jPanelMDN);

        jPanelDirPoll.setLayout(new java.awt.GridBagLayout());

        jPanelPollOptions.setLayout(new java.awt.GridBagLayout());

        jCheckBoxEnableDirPoll.setText(this.rb.getResourceString( "label.enabledirpoll"));
        jCheckBoxEnableDirPoll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEnableDirPollActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelPollOptions.add(jCheckBoxEnableDirPoll, gridBagConstraints);

        jLabelPollDir.setText(this.rb.getResourceString( "label.polldir"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 5);
        jPanelPollOptions.add(jLabelPollDir, gridBagConstraints);

        jTextFieldPollDir.setEditable(false);
        jTextFieldPollDir.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 10);
        jPanelPollOptions.add(jTextFieldPollDir, gridBagConstraints);

        jLabelPollInterval.setText(this.rb.getResourceString( "label.pollinterval"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelPollOptions.add(jLabelPollInterval, gridBagConstraints);

        jTextFieldPollInterval.setColumns(5);
        jTextFieldPollInterval.setMinimumSize(new java.awt.Dimension(70, 20));
        jTextFieldPollInterval.setPreferredSize(new java.awt.Dimension(70, 20));
        jTextFieldPollInterval.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPollIntervalKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanelPollOptions.add(jTextFieldPollInterval, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelPollOptions.add(jPanelSpaceX, gridBagConstraints);

        jLabelPollIntervalSeconds.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelPollOptions.add(jLabelPollIntervalSeconds, gridBagConstraints);

        jLabelIgnorePollFilterList.setText(this.rb.getResourceString( "label.pollignore"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelPollOptions.add(jLabelIgnorePollFilterList, gridBagConstraints);

        jTextFieldIgnorePollFilterList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldIgnorePollFilterListKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelPollOptions.add(jTextFieldIgnorePollFilterList, gridBagConstraints);

        jLabelPollMaxFiles.setText(this.rb.getResourceString( "label.maxpollfiles"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelPollOptions.add(jLabelPollMaxFiles, gridBagConstraints);

        jTextFieldPollMaxFiles.setColumns(5);
        jTextFieldPollMaxFiles.setMinimumSize(new java.awt.Dimension(70, 20));
        jTextFieldPollMaxFiles.setPreferredSize(new java.awt.Dimension(70, 20));
        jTextFieldPollMaxFiles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPollMaxFilesKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanelPollOptions.add(jTextFieldPollMaxFiles, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelDirPoll.add(jPanelPollOptions, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.dirpoll"), jPanelDirPoll);

        jPanelReceipt.setLayout(new java.awt.GridBagLayout());

        jPanelReceiptOptions.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelReceiptOptions.add(jPanelSpace456, gridBagConstraints);

        jCheckBoxKeepFilenameOnReceipt.setText(this.rb.getResourceString( "label.keepfilenameonreceipt"));
        jCheckBoxKeepFilenameOnReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxKeepFilenameOnReceiptActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        jPanelReceiptOptions.add(jCheckBoxKeepFilenameOnReceipt, gridBagConstraints);

        jLabelHintKeepFilenameOnReceipt.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelHintKeepFilenameOnReceipt.setText(this.rb.getResourceString( "hint.keepfilenameonreceipt"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanelReceiptOptions.add(jLabelHintKeepFilenameOnReceipt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelReceipt.add(jPanelReceiptOptions, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.receipt"), jPanelReceipt);

        jPanelHTTPAuth.setLayout(new java.awt.GridBagLayout());

        jPanelHttpAuthData.setLayout(new java.awt.GridBagLayout());

        jCheckBoxHttpAuth.setText(this.rb.getResourceString( "label.usehttpauth" ));
        jCheckBoxHttpAuth.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxHttpAuth.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxHttpAuth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxHttpAuthActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jCheckBoxHttpAuth, gridBagConstraints);

        jLabelHttpAuth.setText(this.rb.getResourceString( "label.usehttpauth.user" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jLabelHttpAuth, gridBagConstraints);

        jTextFieldHttpAuthUser.setColumns(30);
        jTextFieldHttpAuthUser.setMinimumSize(new java.awt.Dimension(150, 20));
        jTextFieldHttpAuthUser.setPreferredSize(new java.awt.Dimension(150, 20));
        jTextFieldHttpAuthUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldHttpAuthUserKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jTextFieldHttpAuthUser, gridBagConstraints);

        jLabelHttpPass.setText(this.rb.getResourceString( "label.usehttpauth.pass" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jLabelHttpPass, gridBagConstraints);

        jPasswordFieldHttpPass.setColumns(30);
        jPasswordFieldHttpPass.setText("jPasswordField1");
        jPasswordFieldHttpPass.setMinimumSize(new java.awt.Dimension(150, 20));
        jPasswordFieldHttpPass.setPreferredSize(new java.awt.Dimension(150, 20));
        jPasswordFieldHttpPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldHttpPassKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jPasswordFieldHttpPass, gridBagConstraints);

        jCheckBoxHttpAuthAsyncMDN.setText(this.rb.getResourceString( "label.usehttpauth.asyncmdn" ));
        jCheckBoxHttpAuthAsyncMDN.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxHttpAuthAsyncMDN.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxHttpAuthAsyncMDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxHttpAuthAsyncMDNActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanelHttpAuthData.add(jCheckBoxHttpAuthAsyncMDN, gridBagConstraints);

        jLabelHttpAuthAsyncMDN.setText(this.rb.getResourceString( "label.usehttpauth.asyncmdn.user" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jLabelHttpAuthAsyncMDN, gridBagConstraints);

        jTextFieldHttpAuthAsyncMDNUser.setColumns(30);
        jTextFieldHttpAuthAsyncMDNUser.setMinimumSize(new java.awt.Dimension(150, 20));
        jTextFieldHttpAuthAsyncMDNUser.setPreferredSize(new java.awt.Dimension(150, 20));
        jTextFieldHttpAuthAsyncMDNUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldHttpAuthAsyncMDNUserKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jTextFieldHttpAuthAsyncMDNUser, gridBagConstraints);

        jLabelHttpPassAsyncMDN.setText(this.rb.getResourceString( "label.usehttpauth.asyncmdn.pass" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jLabelHttpPassAsyncMDN, gridBagConstraints);

        jPasswordFieldHttpPassAsyncMDN.setColumns(30);
        jPasswordFieldHttpPassAsyncMDN.setText("jPasswordField1");
        jPasswordFieldHttpPassAsyncMDN.setMinimumSize(new java.awt.Dimension(150, 20));
        jPasswordFieldHttpPassAsyncMDN.setPreferredSize(new java.awt.Dimension(150, 20));
        jPasswordFieldHttpPassAsyncMDN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldHttpPassAsyncMDNKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHttpAuthData.add(jPasswordFieldHttpPassAsyncMDN, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelHttpAuthData.add(jPanelSpace199, gridBagConstraints);

        jLabelHttpAuthDataInfo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabelHttpAuthDataInfo.setText(this.rb.getResourceString( "label.httpauthentication.info"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 20, 5);
        jPanelHttpAuthData.add(jLabelHttpAuthDataInfo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelHTTPAuth.add(jPanelHttpAuthData, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.httpauth"), jPanelHTTPAuth);

        jPanelHTTPHeader.setLayout(new java.awt.GridBagLayout());

        jTableHttpHeader.setModel(new TableModelHttpHeader());
        jScrollPaneHttpHeader.setViewportView(jTableHttpHeader);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelHTTPHeader.add(jScrollPaneHttpHeader, gridBagConstraints);

        jButtonHttpHeaderAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonHttpHeaderAdd.setText(this.rb.getResourceString( "httpheader.add"));
        jButtonHttpHeaderAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonHttpHeaderAdd.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonHttpHeaderAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonHttpHeaderAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHttpHeaderAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelHTTPHeader.add(jButtonHttpHeaderAdd, gridBagConstraints);

        jButtonHttpHeaderRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonHttpHeaderRemove.setText(this.rb.getResourceString( "httpheader.delete"));
        jButtonHttpHeaderRemove.setEnabled(false);
        jButtonHttpHeaderRemove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonHttpHeaderRemove.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonHttpHeaderRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonHttpHeaderRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHttpHeaderRemoveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHTTPHeader.add(jButtonHttpHeaderRemove, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.httpheader"), jPanelHTTPHeader);

        jPanelNotification.setLayout(new java.awt.GridBagLayout());

        jPanelNotificationMain.setLayout(new java.awt.GridBagLayout());

        jCheckBoxNotifySend.setText(this.rb.getResourceString("label.notify.send"));
        jCheckBoxNotifySend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxNotifySendActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelNotificationMain.add(jCheckBoxNotifySend, gridBagConstraints);

        jCheckBoxNotifyReceive.setText(this.rb.getResourceString("label.notify.receive"));
        jCheckBoxNotifyReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxNotifyReceiveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelNotificationMain.add(jCheckBoxNotifyReceive, gridBagConstraints);

        jCheckBoxNotifySendReceive.setText(this.rb.getResourceString("label.notify.sendreceive"));
        jCheckBoxNotifySendReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxNotifySendReceiveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelNotificationMain.add(jCheckBoxNotifySendReceive, gridBagConstraints);

        jTextFieldNotifyReceive.setText("0");
        jTextFieldNotifyReceive.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextFieldNotifyReceive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldNotifyReceiveKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelNotificationMain.add(jTextFieldNotifyReceive, gridBagConstraints);

        jTextFieldNotifySend.setText("0");
        jTextFieldNotifySend.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextFieldNotifySend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldNotifySendKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelNotificationMain.add(jTextFieldNotifySend, gridBagConstraints);

        jTextFieldNotifySendReceive.setText("0");
        jTextFieldNotifySendReceive.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextFieldNotifySendReceive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldNotifySendReceiveKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelNotificationMain.add(jTextFieldNotifySendReceive, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelNotificationMain.add(jPanelSpace23, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelNotification.add(jPanelNotificationMain, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.notification"), jPanelNotification);

        jPanelEvents.setLayout(new java.awt.GridBagLayout());

        jPanelEventsMain.setLayout(new java.awt.GridBagLayout());

        jCheckBoxUseEventOnSendError.setText(this.rb.getResourceString( "label.usecommandonsenderror"));
        jCheckBoxUseEventOnSendError.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxUseEventOnSendError.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxUseEventOnSendError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUseEventOnSendErrorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanelEventsMain.add(jCheckBoxUseEventOnSendError, gridBagConstraints);

        jTextFieldEventInfoOnSendError.setEditable(false);
        jTextFieldEventInfoOnSendError.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        jPanelEventsMain.add(jTextFieldEventInfoOnSendError, gridBagConstraints);

        jCheckBoxUseEventOnSendSuccess.setText(this.rb.getResourceString( "label.usecommandonsendsuccess"));
        jCheckBoxUseEventOnSendSuccess.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxUseEventOnSendSuccess.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxUseEventOnSendSuccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUseEventOnSendSuccessActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanelEventsMain.add(jCheckBoxUseEventOnSendSuccess, gridBagConstraints);

        jTextFieldEventInfoOnSendSuccess.setEditable(false);
        jTextFieldEventInfoOnSendSuccess.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        jPanelEventsMain.add(jTextFieldEventInfoOnSendSuccess, gridBagConstraints);

        jCheckBoxUseEventOnReceipt.setText(this.rb.getResourceString( "label.usecommandonreceipt"));
        jCheckBoxUseEventOnReceipt.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxUseEventOnReceipt.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxUseEventOnReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUseEventOnReceiptActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanelEventsMain.add(jCheckBoxUseEventOnReceipt, gridBagConstraints);

        jTextFieldEventInfoOnReceipt.setEditable(false);
        jTextFieldEventInfoOnReceipt.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        jPanelEventsMain.add(jTextFieldEventInfoOnReceipt, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelEventsMain.add(jPanelSpace, gridBagConstraints);

        jButtonAddEventOnReceipt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonAddEventOnReceipt.setToolTipText(this.rb.getResourceString("tooltip.button.addevent"));
        jButtonAddEventOnReceipt.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButtonAddEventOnReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEventOnReceiptActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanelEventsMain.add(jButtonAddEventOnReceipt, gridBagConstraints);

        jButtonEditEventOnReceipt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonEditEventOnReceipt.setToolTipText(this.rb.getResourceString("tooltip.button.editevent"));
        jButtonEditEventOnReceipt.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButtonEditEventOnReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditEventOnReceiptActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanelEventsMain.add(jButtonEditEventOnReceipt, gridBagConstraints);

        jLabelIconProcessTypeOnReceipt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanelEventsMain.add(jLabelIconProcessTypeOnReceipt, gridBagConstraints);

        jButtonEditEventOnSendError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonEditEventOnSendError.setToolTipText(this.rb.getResourceString("tooltip.button.editevent"));
        jButtonEditEventOnSendError.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButtonEditEventOnSendError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditEventOnSendErrorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanelEventsMain.add(jButtonEditEventOnSendError, gridBagConstraints);

        jButtonAddEventOnSendError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonAddEventOnSendError.setToolTipText(this.rb.getResourceString("tooltip.button.addevent"));
        jButtonAddEventOnSendError.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButtonAddEventOnSendError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEventOnSendErrorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanelEventsMain.add(jButtonAddEventOnSendError, gridBagConstraints);

        jButtonEditEventOnSendSuccess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonEditEventOnSendSuccess.setToolTipText(this.rb.getResourceString("tooltip.button.editevent"));
        jButtonEditEventOnSendSuccess.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButtonEditEventOnSendSuccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditEventOnSendSuccessActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanelEventsMain.add(jButtonEditEventOnSendSuccess, gridBagConstraints);

        jButtonAddEventOnSendSuccess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonAddEventOnSendSuccess.setToolTipText(this.rb.getResourceString("tooltip.button.addevent"));
        jButtonAddEventOnSendSuccess.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButtonAddEventOnSendSuccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEventOnSendSuccessActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanelEventsMain.add(jButtonAddEventOnSendSuccess, gridBagConstraints);

        jLabelIconProcessTypeOnSendError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanelEventsMain.add(jLabelIconProcessTypeOnSendError, gridBagConstraints);

        jLabelIconProcessTypeOnSendSuccess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanelEventsMain.add(jLabelIconProcessTypeOnSendSuccess, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanelEventsMain.add(jPanelSpace123, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanelEventsMain.add(jPanelSpace124, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanelEvents.add(jPanelEventsMain, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.events"), jPanelEvents);

        jPanelPartnerSystem.setLayout(new java.awt.GridBagLayout());

        jPanelPartnerSystemMain.setLayout(new java.awt.GridBagLayout());

        jLabelAS2Version.setText(this.rb.getResourceString( "label.as2version"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelPartnerSystemMain.add(jLabelAS2Version, gridBagConstraints);

        jLabelProductName.setText(this.rb.getResourceString( "label.productname"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelPartnerSystemMain.add(jLabelProductName, gridBagConstraints);

        jLabelFeatures.setText(this.rb.getResourceString( "label.features"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 10, 5);
        jPanelPartnerSystemMain.add(jLabelFeatures, gridBagConstraints);

        jCheckBoxEdiintFeaturesCompression.setText(this.rb.getResourceString( "label.features.compression"));
        jCheckBoxEdiintFeaturesCompression.setEnabled(false);
        jCheckBoxEdiintFeaturesCompression.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelPartnerSystemMain.add(jCheckBoxEdiintFeaturesCompression, gridBagConstraints);

        jCheckBoxEdiintFeaturesMA.setText(this.rb.getResourceString( "label.features.ma"));
        jCheckBoxEdiintFeaturesMA.setEnabled(false);
        jCheckBoxEdiintFeaturesMA.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelPartnerSystemMain.add(jCheckBoxEdiintFeaturesMA, gridBagConstraints);

        jCheckBoxEdiintFeaturesCEM.setText(this.rb.getResourceString( "label.features.cem"));
        jCheckBoxEdiintFeaturesCEM.setEnabled(false);
        jCheckBoxEdiintFeaturesCEM.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelPartnerSystemMain.add(jCheckBoxEdiintFeaturesCEM, gridBagConstraints);

        jTextFieldAS2Version.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelPartnerSystemMain.add(jTextFieldAS2Version, gridBagConstraints);

        jTextFieldProductName.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelPartnerSystemMain.add(jTextFieldProductName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelPartnerSystemMain.add(jPanelSpaceSpace, gridBagConstraints);

        jScrollPaneTextAreaPartnerSystemInformation.setBorder(null);

        jTextAreaPartnerSystemInformation.setEditable(false);
        jTextAreaPartnerSystemInformation.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jTextAreaPartnerSystemInformation.setColumns(20);
        jTextAreaPartnerSystemInformation.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jTextAreaPartnerSystemInformation.setLineWrap(true);
        jTextAreaPartnerSystemInformation.setRows(5);
        jTextAreaPartnerSystemInformation.setWrapStyleWord(true);
        jTextAreaPartnerSystemInformation.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextAreaPartnerSystemInformation.setFocusable(false);
        jScrollPaneTextAreaPartnerSystemInformation.setViewportView(jTextAreaPartnerSystemInformation);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanelPartnerSystemMain.add(jScrollPaneTextAreaPartnerSystemInformation, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelPartnerSystem.add(jPanelPartnerSystemMain, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.partnersystem"), jPanelPartnerSystem);

        jPanelMisc.setLayout(new java.awt.GridBagLayout());

        jPanelMiscMain.setLayout(new java.awt.GridBagLayout());

        jTextFieldId.setColumns(30);
        jTextFieldId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldIdKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelMiscMain.add(jTextFieldId, gridBagConstraints);

        jTextFieldName.setColumns(30);
        jTextFieldName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldNameKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelMiscMain.add(jTextFieldName, gridBagConstraints);

        jLabelName.setText(this.rb.getResourceString( "label.name"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMiscMain.add(jLabelName, gridBagConstraints);

        jLabelId.setText(this.rb.getResourceString( "label.id"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMiscMain.add(jLabelId, gridBagConstraints);

        jCheckBoxLocalStation.setText(this.rb.getResourceString( "label.localstation"));
        jCheckBoxLocalStation.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxLocalStationItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelMiscMain.add(jCheckBoxLocalStation, gridBagConstraints);

        jLabelEMail.setText(this.rb.getResourceString( "label.email"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMiscMain.add(jLabelEMail, gridBagConstraints);

        jTextFieldEMail.setColumns(30);
        jTextFieldEMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldEMailKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelMiscMain.add(jTextFieldEMail, gridBagConstraints);

        jScrollPanePartnerComment.setViewportView(jTextPanePartnerComment);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 10);
        jPanelMiscMain.add(jScrollPanePartnerComment, gridBagConstraints);

        jLabelPartnerComment.setText(this.rb.getResourceString( "label.partnercomment"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMiscMain.add(jLabelPartnerComment, gridBagConstraints);

        jScrollPanePartnerAddress.setViewportView(jTextPanePartnerContact);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 10);
        jPanelMiscMain.add(jScrollPanePartnerAddress, gridBagConstraints);

        jScrollPanePartnerContact.setViewportView(jTextPanePartnerAddress);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 10);
        jPanelMiscMain.add(jScrollPanePartnerContact, gridBagConstraints);

        jLabelAddress.setText(this.rb.getResourceString( "label.address")
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMiscMain.add(jLabelAddress, gridBagConstraints);

        jLabelContact.setText(this.rb.getResourceString( "label.contact"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMiscMain.add(jLabelContact, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMisc.add(jPanelMiscMain, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.misc"), jPanelMisc);

        jPanelSecurity.setLayout(new java.awt.GridBagLayout());

        jPanelSecurityMain.setLayout(new java.awt.GridBagLayout());

        jLabelSignAlias.setText("<signalias>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelSecurityMain.add(jLabelSignAlias, gridBagConstraints);

        jComboBoxSignCert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSignCertActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelSecurityMain.add(jComboBoxSignCert, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelSecurityMain.add(jPanelSpace2, gridBagConstraints);

        jComboBoxSignType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSignTypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelSecurityMain.add(jComboBoxSignType, gridBagConstraints);

        jLabelEncryptionType.setText(this.rb.getResourceString( "label.encryptiontype" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelSecurityMain.add(jLabelEncryptionType, gridBagConstraints);

        jComboBoxEncryptionType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEncryptionTypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelSecurityMain.add(jComboBoxEncryptionType, gridBagConstraints);

        jLabelCertSignType.setText(this.rb.getResourceString( "label.signtype"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelSecurityMain.add(jLabelCertSignType, gridBagConstraints);

        jLabelCryptAlias.setText("<cryptalias>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        jPanelSecurityMain.add(jLabelCryptAlias, gridBagConstraints);

        jComboBoxCryptCert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCryptCertActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanelSecurityMain.add(jComboBoxCryptCert, gridBagConstraints);

        jCheckBoxUseAlgorithmIdentifierProtectionAttribute.setText(this.rb.getResourceString( "label.algorithmidentifierprotection"));
        jCheckBoxUseAlgorithmIdentifierProtectionAttribute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUseAlgorithmIdentifierProtectionAttributeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanelSecurityMain.add(jCheckBoxUseAlgorithmIdentifierProtectionAttribute, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelSecurity.add(jPanelSecurityMain, gridBagConstraints);

        jTabbedPane.addTab(this.rb.getResourceString( "tab.security"), jPanelSecurity);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTabbedPane, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    private void jComboBoxCryptCertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCryptCertActionPerformed
        if (this.partner != null && this.jComboBoxCryptCert.getSelectedItem() != null) {
            KeystoreCertificate certificate = (KeystoreCertificate) this.jComboBoxCryptCert.getSelectedItem();
            PartnerCertificateInformation cryptInfo = new PartnerCertificateInformation(
                    certificate.getFingerPrintSHA1(),
                    PartnerCertificateInformation.CATEGORY_CRYPT);
            partner.setCertificateInformation(cryptInfo);
            this.informTreeModelNodeChanged();
        }
        this.setButtonState();
    }//GEN-LAST:event_jComboBoxCryptCertActionPerformed

    private void jPasswordFieldHttpPassAsyncMDNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldHttpPassAsyncMDNKeyReleased
        if (this.jCheckBoxHttpAuthAsyncMDN.isSelected()) {
            if (this.partner != null && this.partner.getAuthenticationAsyncMDN() != null) {
                this.partner.getAuthenticationAsyncMDN().setPassword(new String(this.jPasswordFieldHttpPassAsyncMDN.getPassword()));
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            }
        }
    }//GEN-LAST:event_jPasswordFieldHttpPassAsyncMDNKeyReleased

    private void jTextFieldHttpAuthAsyncMDNUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHttpAuthAsyncMDNUserKeyReleased
        if (this.jCheckBoxHttpAuthAsyncMDN.isSelected()) {
            if (this.partner != null && this.partner.getAuthenticationAsyncMDN() != null) {
                this.partner.getAuthenticationAsyncMDN().setUser(this.jTextFieldHttpAuthAsyncMDNUser.getText());
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            }
        }
    }//GEN-LAST:event_jTextFieldHttpAuthAsyncMDNUserKeyReleased

    private void jPasswordFieldHttpPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldHttpPassKeyReleased
        if (this.jCheckBoxHttpAuth.isSelected()) {
            if (this.partner != null && this.partner.getAuthentication() != null) {
                this.partner.getAuthentication().setPassword(new String(this.jPasswordFieldHttpPass.getPassword()));
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            }
        }
    }//GEN-LAST:event_jPasswordFieldHttpPassKeyReleased

    private void jTextFieldHttpAuthUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldHttpAuthUserKeyReleased
        if (this.jCheckBoxHttpAuth.isSelected()) {
            if (this.partner != null && this.partner.getAuthentication() != null) {
                this.partner.getAuthentication().setUser(this.jTextFieldHttpAuthUser.getText());
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            }
        }
    }//GEN-LAST:event_jTextFieldHttpAuthUserKeyReleased

    private void jCheckBoxHttpAuthAsyncMDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxHttpAuthAsyncMDNActionPerformed
        if (this.partner != null) {
            this.partner.getAuthenticationAsyncMDN().setEnabled(this.jCheckBoxHttpAuthAsyncMDN.isSelected());
            this.informTreeModelNodeChanged();
        }
        this.updateHttpAuthState();
    }//GEN-LAST:event_jCheckBoxHttpAuthAsyncMDNActionPerformed

    private void jCheckBoxHttpAuthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxHttpAuthActionPerformed
        if (this.partner != null) {
            this.partner.getAuthentication().setEnabled(this.jCheckBoxHttpAuth.isSelected());
            this.informTreeModelNodeChanged();
        }
        this.updateHttpAuthState();
    }//GEN-LAST:event_jCheckBoxHttpAuthActionPerformed

    private void jCheckBoxUseEventOnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUseEventOnReceiptActionPerformed
        if (this.partner != null) {
            this.partner.getPartnerEvents().setUseOnReceipt(this.jCheckBoxUseEventOnReceipt.isSelected());
            this.informTreeModelNodeChanged();
        }
        this.renderEvents();
    }//GEN-LAST:event_jCheckBoxUseEventOnReceiptActionPerformed

    private void jCheckBoxSignedMDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSignedMDNActionPerformed
        if (this.partner != null) {
            this.partner.setSignedMDN(this.jCheckBoxSignedMDN.isSelected());
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jCheckBoxSignedMDNActionPerformed

    private void jCheckBoxCompressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCompressActionPerformed
        if (this.partner != null) {
            this.partner.setCompressionType(this.jCheckBoxCompress.isSelected() ? AS2Message.COMPRESSION_ZLIB : AS2Message.COMPRESSION_NONE);
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jCheckBoxCompressActionPerformed

    private void jTextFieldIgnorePollFilterListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldIgnorePollFilterListKeyReleased
        if (this.partner != null) {
            this.partner.setPollIgnoreListString(this.jTextFieldIgnorePollFilterList.getText());
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldIgnorePollFilterListKeyReleased

    private void jTextFieldPollIntervalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPollIntervalKeyReleased
        if (this.partner != null) {
            try {
                int pollInterval = Integer.valueOf(this.jTextFieldPollInterval.getText().trim()).intValue();
                this.partner.setPollInterval(pollInterval);
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            } catch (Exception e) {
                //nop
            }
        }
    }//GEN-LAST:event_jTextFieldPollIntervalKeyReleased

    private void jRadioButtonSyncMDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSyncMDNActionPerformed
        if (this.partner != null) {
            this.partner.setSyncMDN(true);
            this.jLabelIconSyncMDN.setEnabled(true);
            this.jLabelIconAsyncMDN.setEnabled(false);
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jRadioButtonSyncMDNActionPerformed

    private void jRadioButtonAsyncMDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAsyncMDNActionPerformed
        if (this.partner != null) {
            this.partner.setSyncMDN(false);
            this.jLabelIconSyncMDN.setEnabled(false);
            this.jLabelIconAsyncMDN.setEnabled(true);
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jRadioButtonAsyncMDNActionPerformed

    private void jTextFieldContentTypeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldContentTypeKeyReleased
        if (this.partner != null) {
            if (this.jTextFieldContentType.getText().trim().length() == 0) {
                this.partner.setContentType("application/EDI-Consent");
            } else {
                this.partner.setContentType(this.jTextFieldContentType.getText());
            }
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldContentTypeKeyReleased

    private void jTextFieldSubjectKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSubjectKeyReleased
        if (this.partner != null) {
            if (this.jTextFieldSubject.getText().trim().length() == 0) {
                this.partner.setSubject("AS2 message");
            } else {
                this.partner.setSubject(this.jTextFieldSubject.getText());
            }
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldSubjectKeyReleased

    private void jTextFieldEMailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEMailKeyReleased
        if (this.partner != null) {
            if (this.jTextFieldEMail.getText().trim().length() == 0) {
                this.partner.setEmail("sender@as2server.com");
            } else {
                this.partner.setEmail(this.jTextFieldEMail.getText());
            }
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldEMailKeyReleased

    private void jTextFieldMDNURLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMDNURLKeyReleased
        if (this.partner != null) {
            if (this.jTextFieldMDNURL.getText().trim().length() == 0) {
                this.partner.setMdnURL(this.partner.getDefaultURL());
            } else {
                this.partner.setMdnURL(this.jTextFieldMDNURL.getText());
            }
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldMDNURLKeyReleased

    private void jComboBoxEncryptionTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEncryptionTypeActionPerformed
        if (this.partner != null) {
            if (this.jComboBoxEncryptionType.getSelectedItem() != null) {
                this.partner.setEncryptionType(((Integer) this.jComboBoxEncryptionType.getSelectedItem()).intValue());
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            }
        }
    }//GEN-LAST:event_jComboBoxEncryptionTypeActionPerformed

    private void jComboBoxSignCertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSignCertActionPerformed
        if (this.partner != null && this.jComboBoxSignCert.getSelectedItem() != null) {
            KeystoreCertificate certificate = (KeystoreCertificate) this.jComboBoxSignCert.getSelectedItem();
            PartnerCertificateInformation signInfo = new PartnerCertificateInformation(
                    certificate.getFingerPrintSHA1(),
                    PartnerCertificateInformation.CATEGORY_SIGN);
            partner.setCertificateInformation(signInfo);
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
        this.setButtonState();
    }//GEN-LAST:event_jComboBoxSignCertActionPerformed

    private void jComboBoxSignTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSignTypeActionPerformed
        if (this.partner != null && this.jComboBoxSignType.getSelectedItem() != null) {
            this.partner.setSignType(((Integer) this.jComboBoxSignType.getSelectedItem()).intValue());
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
        this.setButtonState();
    }//GEN-LAST:event_jComboBoxSignTypeActionPerformed

    private void jTextFieldURLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldURLKeyReleased
        if (this.partner != null) {
            this.partner.setURL(this.jTextFieldURL.getText());
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldURLKeyReleased

    private void jTextFieldIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldIdKeyReleased
        if (this.partner != null) {
            this.partner.setAS2Identification(this.jTextFieldId.getText());
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldIdKeyReleased

    private void jTextFieldNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNameKeyReleased
        if (this.partner != null) {
            this.partner.setName(this.jTextFieldName.getText());
            this.updatePollDirDisplay(this.partner);
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }//GEN-LAST:event_jTextFieldNameKeyReleased

    private void jCheckBoxLocalStationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxLocalStationItemStateChanged
        if (this.partner != null) {
            this.partner.setLocalStation(this.jCheckBoxLocalStation.isSelected());
            if (this.partner.isLocalStation()) {
                this.tree.setToLocalStation(this.partner);
            }
            this.informTreeModelNodeChanged();
            this.setPanelVisiblilityState();
            this.handleVisibilityStateOfWidgets();
            this.disableEnableWidgets();
            this.buttonOk.computeErrorState();
            this.updatePollDirDisplay(this.partner);
        }
        this.setButtonState();
    }//GEN-LAST:event_jCheckBoxLocalStationItemStateChanged

    private void jCheckBoxKeepFilenameOnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxKeepFilenameOnReceiptActionPerformed
        if (this.partner != null) {
            this.partner.setKeepOriginalFilenameOnReceipt(this.jCheckBoxKeepFilenameOnReceipt.isSelected());
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
        this.setButtonState();
    }//GEN-LAST:event_jCheckBoxKeepFilenameOnReceiptActionPerformed

private void jTextFieldNotifySendKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNotifySendKeyReleased
    if (this.jCheckBoxNotifySend.isSelected()) {
        if (this.partner != null) {
            try {
                this.partner.setNotifySend(Integer.valueOf(this.jTextFieldNotifySend.getText()).intValue());
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            } catch (NumberFormatException e) {
                //nop
            }
        }
    }
}//GEN-LAST:event_jTextFieldNotifySendKeyReleased

private void jTextFieldNotifyReceiveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNotifyReceiveKeyReleased
    if (this.jCheckBoxNotifyReceive.isSelected()) {
        if (this.partner != null) {
            try {
                this.partner.setNotifyReceive(Integer.valueOf(this.jTextFieldNotifyReceive.getText()).intValue());
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            } catch (NumberFormatException e) {
                //nop
            }
        }
    }
}//GEN-LAST:event_jTextFieldNotifyReceiveKeyReleased

private void jTextFieldNotifySendReceiveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNotifySendReceiveKeyReleased
    if (this.jCheckBoxNotifySendReceive.isSelected()) {
        if (this.partner != null) {
            try {
                this.partner.setNotifySendReceive(Integer.valueOf(this.jTextFieldNotifySendReceive.getText()).intValue());
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            } catch (NumberFormatException e) {
                //nop
            }
        }
    }
}//GEN-LAST:event_jTextFieldNotifySendReceiveKeyReleased

private void jCheckBoxNotifySendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxNotifySendActionPerformed
    if (this.partner != null) {
        this.partner.setNotifySendEnabled(this.jCheckBoxNotifySend.isSelected());
        this.buttonOk.computeErrorState();
        this.informTreeModelNodeChanged();
        this.setButtonState();
    }
}//GEN-LAST:event_jCheckBoxNotifySendActionPerformed

private void jCheckBoxNotifyReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxNotifyReceiveActionPerformed
    if (this.partner != null) {
        this.partner.setNotifyReceiveEnabled(this.jCheckBoxNotifyReceive.isSelected());
        this.buttonOk.computeErrorState();
        this.informTreeModelNodeChanged();
        this.setButtonState();
    }
}//GEN-LAST:event_jCheckBoxNotifyReceiveActionPerformed

private void jCheckBoxNotifySendReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxNotifySendReceiveActionPerformed
    if (this.partner != null) {
        this.partner.setNotifySendReceiveEnabled(this.jCheckBoxNotifySendReceive.isSelected());
        this.buttonOk.computeErrorState();
        this.informTreeModelNodeChanged();
        this.setButtonState();
    }
}//GEN-LAST:event_jCheckBoxNotifySendReceiveActionPerformed

private void jCheckBoxUseEventOnSendSuccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUseEventOnSendSuccessActionPerformed
    if (this.partner != null) {
        this.partner.getPartnerEvents().setUseOnSendsuccess(this.jCheckBoxUseEventOnSendSuccess.isSelected());
        this.buttonOk.computeErrorState();
        this.informTreeModelNodeChanged();
    }
    this.renderEvents();
}//GEN-LAST:event_jCheckBoxUseEventOnSendSuccessActionPerformed

private void jCheckBoxUseEventOnSendErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUseEventOnSendErrorActionPerformed
    if (this.partner != null) {
        this.partner.getPartnerEvents().setUse(PartnerEventInformation.TYPE_ON_SENDERROR, this.jCheckBoxUseEventOnSendError.isSelected());
        this.buttonOk.computeErrorState();
        this.informTreeModelNodeChanged();
    }
    this.renderEvents();
}//GEN-LAST:event_jCheckBoxUseEventOnSendErrorActionPerformed

private void jComboBoxContentTransferEncodingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxContentTransferEncodingActionPerformed
    if (this.partner != null) {
        int newTransferEncoding = -1;
        if (this.jComboBoxContentTransferEncoding.getSelectedItem().equals(STR_CONTENT_TRANSFER_ENCODING_BINARY)) {
            newTransferEncoding = AS2Message.CONTENT_TRANSFER_ENCODING_BINARY;
        } else {
            newTransferEncoding = AS2Message.CONTENT_TRANSFER_ENCODING_BASE64;
        }
        if (this.partner.getContentTransferEncoding() != newTransferEncoding) {
            this.partner.setContentTransferEncoding(newTransferEncoding);
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        }
    }
}//GEN-LAST:event_jComboBoxContentTransferEncodingActionPerformed

private void jButtonHttpHeaderAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHttpHeaderAddActionPerformed
    PartnerHttpHeader header = new PartnerHttpHeader();
    header.setKey("");
    header.setValue("");
    ((TableModelHttpHeader) this.jTableHttpHeader.getModel()).addRow(header);
}//GEN-LAST:event_jButtonHttpHeaderAddActionPerformed

private void jButtonHttpHeaderRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHttpHeaderRemoveActionPerformed
    int selectedRow = this.jTableHttpHeader.getSelectedRow();
    ((TableModelHttpHeader) this.jTableHttpHeader.getModel()).deleteRow(selectedRow);
    if (selectedRow > this.jTableHttpHeader.getRowCount() - 1) {
        selectedRow = this.jTableHttpHeader.getRowCount() - 1;
    }
    this.jTableHttpHeader.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
}//GEN-LAST:event_jButtonHttpHeaderRemoveActionPerformed

private void jComboBoxHTTPProtocolVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxHTTPProtocolVersionActionPerformed
    if (this.partner != null) {
        this.partner.setHttpProtocolVersion((String) this.jComboBoxHTTPProtocolVersion.getSelectedItem());
        this.buttonOk.computeErrorState();
        this.informTreeModelNodeChanged();
    }
}//GEN-LAST:event_jComboBoxHTTPProtocolVersionActionPerformed

private void jTextFieldPollMaxFilesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPollMaxFilesKeyReleased
    if (this.partner != null) {
        try {
            int maxPollFiles = Integer.valueOf(this.jTextFieldPollMaxFiles.getText().trim()).intValue();
            this.partner.setMaxPollFiles(maxPollFiles);
            this.buttonOk.computeErrorState();
            this.informTreeModelNodeChanged();
        } catch (Exception e) {
            //nop
        }
    }
}//GEN-LAST:event_jTextFieldPollMaxFilesKeyReleased

    private void jCheckBoxUseAlgorithmIdentifierProtectionAttributeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUseAlgorithmIdentifierProtectionAttributeActionPerformed
        if (this.partner != null) {
            try {
                this.partner.setUseAlgorithmIdentifierProtectionAttribute(this.jCheckBoxUseAlgorithmIdentifierProtectionAttribute.isSelected());
                this.buttonOk.computeErrorState();
                this.informTreeModelNodeChanged();
            } catch (Exception e) {
                //nop
            }
        }
    }//GEN-LAST:event_jCheckBoxUseAlgorithmIdentifierProtectionAttributeActionPerformed

    private void jButtonTestConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestConnectionActionPerformed
        this.testConnection();
    }//GEN-LAST:event_jButtonTestConnectionActionPerformed

    private void jCheckBoxEnableDirPollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEnableDirPollActionPerformed
        if (this.partner != null) {
            try {
                this.partner.setEnableDirPoll(this.jCheckBoxEnableDirPoll.isSelected());
                this.disableEnableWidgets();
                this.informTreeModelNodeChanged();
            } catch (Exception e) {
                //nop
            }
        }
    }//GEN-LAST:event_jCheckBoxEnableDirPollActionPerformed

    private void jButtonAddEventOnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEventOnReceiptActionPerformed
        this.createProcess(PartnerEventInformation.TYPE_ON_RECEIPT);
    }//GEN-LAST:event_jButtonAddEventOnReceiptActionPerformed

    private void jButtonEditEventOnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditEventOnReceiptActionPerformed
        this.editEvent(PartnerEventInformation.TYPE_ON_RECEIPT,
                this.partner.getPartnerEvents().getProcess(PartnerEventInformation.TYPE_ON_RECEIPT));
    }//GEN-LAST:event_jButtonEditEventOnReceiptActionPerformed

    private void jButtonEditEventOnSendErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditEventOnSendErrorActionPerformed
        this.editEvent(PartnerEventInformation.TYPE_ON_SENDERROR,
                this.partner.getPartnerEvents().getProcess(PartnerEventInformation.TYPE_ON_SENDERROR));
    }//GEN-LAST:event_jButtonEditEventOnSendErrorActionPerformed

    private void jButtonAddEventOnSendErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEventOnSendErrorActionPerformed
        this.createProcess(PartnerEventInformation.TYPE_ON_SENDERROR);
    }//GEN-LAST:event_jButtonAddEventOnSendErrorActionPerformed

    private void jButtonEditEventOnSendSuccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditEventOnSendSuccessActionPerformed
        this.editEvent(PartnerEventInformation.TYPE_ON_SENDSUCCESS,
                this.partner.getPartnerEvents().getProcess(PartnerEventInformation.TYPE_ON_SENDSUCCESS));
    }//GEN-LAST:event_jButtonEditEventOnSendSuccessActionPerformed

    private void jButtonAddEventOnSendSuccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEventOnSendSuccessActionPerformed
        this.createProcess(PartnerEventInformation.TYPE_ON_SENDSUCCESS);
    }//GEN-LAST:event_jButtonAddEventOnSendSuccessActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupSyncAsyncMDN;
    private javax.swing.JButton jButtonAddEventOnReceipt;
    private javax.swing.JButton jButtonAddEventOnSendError;
    private javax.swing.JButton jButtonAddEventOnSendSuccess;
    private javax.swing.JButton jButtonEditEventOnReceipt;
    private javax.swing.JButton jButtonEditEventOnSendError;
    private javax.swing.JButton jButtonEditEventOnSendSuccess;
    private javax.swing.JButton jButtonHttpHeaderAdd;
    private javax.swing.JButton jButtonHttpHeaderRemove;
    private javax.swing.JButton jButtonTestConnection;
    private javax.swing.JCheckBox jCheckBoxCompress;
    private javax.swing.JCheckBox jCheckBoxEdiintFeaturesCEM;
    private javax.swing.JCheckBox jCheckBoxEdiintFeaturesCompression;
    private javax.swing.JCheckBox jCheckBoxEdiintFeaturesMA;
    private javax.swing.JCheckBox jCheckBoxEnableDirPoll;
    private javax.swing.JCheckBox jCheckBoxHttpAuth;
    private javax.swing.JCheckBox jCheckBoxHttpAuthAsyncMDN;
    private javax.swing.JCheckBox jCheckBoxKeepFilenameOnReceipt;
    private javax.swing.JCheckBox jCheckBoxLocalStation;
    private javax.swing.JCheckBox jCheckBoxNotifyReceive;
    private javax.swing.JCheckBox jCheckBoxNotifySend;
    private javax.swing.JCheckBox jCheckBoxNotifySendReceive;
    private javax.swing.JCheckBox jCheckBoxSignedMDN;
    private javax.swing.JCheckBox jCheckBoxUseAlgorithmIdentifierProtectionAttribute;
    private javax.swing.JCheckBox jCheckBoxUseEventOnReceipt;
    private javax.swing.JCheckBox jCheckBoxUseEventOnSendError;
    private javax.swing.JCheckBox jCheckBoxUseEventOnSendSuccess;
    private javax.swing.JComboBox jComboBoxContentTransferEncoding;
    private javax.swing.JComboBox jComboBoxCryptCert;
    private javax.swing.JComboBox jComboBoxEncryptionType;
    private javax.swing.JComboBox jComboBoxHTTPProtocolVersion;
    private javax.swing.JComboBox jComboBoxSignCert;
    private javax.swing.JComboBox jComboBoxSignType;
    private javax.swing.JLabel jLabelAS2Version;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelAsyncMDNDescription;
    private javax.swing.JLabel jLabelCertSignType;
    private javax.swing.JLabel jLabelContact;
    private javax.swing.JLabel jLabelContentTransferEncoding;
    private javax.swing.JLabel jLabelContentType;
    private javax.swing.JLabel jLabelCryptAlias;
    private javax.swing.JLabel jLabelEMail;
    private javax.swing.JLabel jLabelEncryptionType;
    private javax.swing.JLabel jLabelFeatures;
    private javax.swing.JLabel jLabelHTTPProtocolVersion;
    private javax.swing.JLabel jLabelHintKeepFilenameOnReceipt;
    private javax.swing.JLabel jLabelHttpAuth;
    private javax.swing.JLabel jLabelHttpAuthAsyncMDN;
    private javax.swing.JLabel jLabelHttpAuthDataInfo;
    private javax.swing.JLabel jLabelHttpPass;
    private javax.swing.JLabel jLabelHttpPassAsyncMDN;
    private javax.swing.JLabel jLabelIconAsyncMDN;
    private javax.swing.JLabel jLabelIconProcessTypeOnReceipt;
    private javax.swing.JLabel jLabelIconProcessTypeOnSendError;
    private javax.swing.JLabel jLabelIconProcessTypeOnSendSuccess;
    private javax.swing.JLabel jLabelIconSyncMDN;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelIgnorePollFilterList;
    private javax.swing.JLabel jLabelMDNDescription;
    private javax.swing.JLabel jLabelMDNSignatureDescription;
    private javax.swing.JLabel jLabelMDNURL;
    private javax.swing.JLabel jLabelMDNURLHint;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPartnerComment;
    private javax.swing.JLabel jLabelPollDir;
    private javax.swing.JLabel jLabelPollInterval;
    private javax.swing.JLabel jLabelPollIntervalSeconds;
    private javax.swing.JLabel jLabelPollMaxFiles;
    private javax.swing.JLabel jLabelProductName;
    private javax.swing.JLabel jLabelSendUrlHint;
    private javax.swing.JLabel jLabelSignAlias;
    private javax.swing.JLabel jLabelSubject;
    private javax.swing.JLabel jLabelSubjectHint;
    private javax.swing.JLabel jLabelSyncMDNDescription;
    private javax.swing.JLabel jLabelURL;
    private javax.swing.JPanel jPanelDirPoll;
    private javax.swing.JPanel jPanelEvents;
    private javax.swing.JPanel jPanelEventsMain;
    private javax.swing.JPanel jPanelHTTPAuth;
    private javax.swing.JPanel jPanelHTTPHeader;
    private javax.swing.JPanel jPanelHttpAuthData;
    private javax.swing.JPanel jPanelMDN;
    private javax.swing.JPanel jPanelMDNMain;
    private javax.swing.JPanel jPanelMisc;
    private javax.swing.JPanel jPanelMiscMain;
    private javax.swing.JPanel jPanelNotification;
    private javax.swing.JPanel jPanelNotificationMain;
    private javax.swing.JPanel jPanelPartnerSystem;
    private javax.swing.JPanel jPanelPartnerSystemMain;
    private javax.swing.JPanel jPanelPollOptions;
    private javax.swing.JPanel jPanelReceipt;
    private javax.swing.JPanel jPanelReceiptOptions;
    private javax.swing.JPanel jPanelSecurity;
    private javax.swing.JPanel jPanelSecurityMain;
    private javax.swing.JPanel jPanelSend;
    private javax.swing.JPanel jPanelSendMain;
    private javax.swing.JPanel jPanelSep;
    private javax.swing.JPanel jPanelSpace;
    private javax.swing.JPanel jPanelSpace123;
    private javax.swing.JPanel jPanelSpace124;
    private javax.swing.JPanel jPanelSpace14;
    private javax.swing.JPanel jPanelSpace199;
    private javax.swing.JPanel jPanelSpace2;
    private javax.swing.JPanel jPanelSpace23;
    private javax.swing.JPanel jPanelSpace456;
    private javax.swing.JPanel jPanelSpace99;
    private javax.swing.JPanel jPanelSpaceSpace;
    private javax.swing.JPanel jPanelSpaceX;
    private javax.swing.JPasswordField jPasswordFieldHttpPass;
    private javax.swing.JPasswordField jPasswordFieldHttpPassAsyncMDN;
    private javax.swing.JRadioButton jRadioButtonAsyncMDN;
    private javax.swing.JRadioButton jRadioButtonSyncMDN;
    private javax.swing.JScrollPane jScrollPaneHttpHeader;
    private javax.swing.JScrollPane jScrollPanePartnerAddress;
    private javax.swing.JScrollPane jScrollPanePartnerComment;
    private javax.swing.JScrollPane jScrollPanePartnerContact;
    private javax.swing.JScrollPane jScrollPaneTextAreaPartnerSystemInformation;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableHttpHeader;
    private javax.swing.JTextArea jTextAreaPartnerSystemInformation;
    private javax.swing.JTextField jTextFieldAS2Version;
    private javax.swing.JTextField jTextFieldContentType;
    private javax.swing.JTextField jTextFieldEMail;
    private javax.swing.JTextField jTextFieldEventInfoOnReceipt;
    private javax.swing.JTextField jTextFieldEventInfoOnSendError;
    private javax.swing.JTextField jTextFieldEventInfoOnSendSuccess;
    private javax.swing.JTextField jTextFieldHttpAuthAsyncMDNUser;
    private javax.swing.JTextField jTextFieldHttpAuthUser;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldIgnorePollFilterList;
    private javax.swing.JTextField jTextFieldMDNURL;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldNotifyReceive;
    private javax.swing.JTextField jTextFieldNotifySend;
    private javax.swing.JTextField jTextFieldNotifySendReceive;
    private javax.swing.JTextField jTextFieldPollDir;
    private javax.swing.JTextField jTextFieldPollInterval;
    private javax.swing.JTextField jTextFieldPollMaxFiles;
    private javax.swing.JTextField jTextFieldProductName;
    private javax.swing.JTextField jTextFieldSubject;
    private javax.swing.JTextField jTextFieldURL;
    private javax.swing.JTextPane jTextPanePartnerAddress;
    private javax.swing.JTextPane jTextPanePartnerComment;
    private javax.swing.JTextPane jTextPanePartnerContact;
    // End of variables declaration//GEN-END:variables
}
