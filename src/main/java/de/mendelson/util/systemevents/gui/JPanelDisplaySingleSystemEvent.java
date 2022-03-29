//$Header: /oftp2/de/mendelson/util/systemevents/gui/JPanelDisplaySingleSystemEvent.java 5     26.09.19 9:37 Heller $
package de.mendelson.util.systemevents.gui;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.systemevents.SystemEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
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
 * Panel that takes a single system event and displays it
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class JPanelDisplaySingleSystemEvent extends JPanel {

    private MecResourceBundle rb;
    private DateFormat detailedDateTimeFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

    /**
     * Creates new form JPanelDisplaySingleSystemEvent
     */
    public JPanelDisplaySingleSystemEvent() {
        //Load resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleDialogSystemEvent.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        initComponents();
        //setup localized event label
        this.jLabelEventOwner.setText(this.rb.getResourceString("label.user"));
        this.jLabelEventOriginHost.setText(this.rb.getResourceString("label.host"));
        this.jLabelEventId.setText(this.rb.getResourceString("label.id"));
        this.jLabelEventDate.setText(this.rb.getResourceString("label.date"));
        this.jLabelEventType.setText(this.rb.getResourceString("label.type"));
    }

    /**
     * No event for the selected day or no selection
     */
    public void displayNoSelection() {
        this.jTextFieldSubjectContent.setText("");
        this.jEditorPaneEventBody.setText(null);
        this.jLabelOrigin.setIcon(null);
        this.jLabelSeverity.setIcon(null);
        this.jLabelEventDateContent.setText("--");
        this.jLabelEventTypeContent.setText("--");
        this.jTextFieldEventIdContent.setText("--");
        this.jTextFieldOriginHostContent.setText("--");
        this.jLabelEventOwnerContent.setText("--");
    }

    public void displayEvent(SystemEvent event) {
        this.jTextFieldSubjectContent.setText(event.getSubject());
        this.jEditorPaneEventBody.setText(event.getBody());
        this.jLabelOrigin.setIcon(event.getOriginIconMultiResolution(24));
        this.jLabelSeverity.setIcon(event.getSeverityIconMultiResolution(24));
        if (event.getUser().equals(SystemEvent.USER_SERVER_PROCESS)) {
            this.jLabelEventOwnerContent.setText(this.rb.getResourceString("user.server.process"));
        } else {
            this.jLabelEventOwnerContent.setText(event.getUser());
        }
        this.jTextFieldOriginHostContent.setText(event.getProcessOriginHost());
        this.jTextFieldEventIdContent.setText(event.getId());
        this.jLabelEventDateContent.setText(this.detailedDateTimeFormat.format(new Date(event.getTimestamp())));
        this.jLabelEventTypeContent.setText("[" + event.categoryToTextLocalized() + "] "
                + event.typeToTextLocalized());
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

        jScrollPaneBody = new javax.swing.JScrollPane();
        jEditorPaneEventBody = new javax.swing.JEditorPane();
        jTextFieldSubjectContent = new javax.swing.JTextField();
        jLabelSeverity = new javax.swing.JLabel();
        jLabelOrigin = new javax.swing.JLabel();
        jPanelAdditionalInfo = new javax.swing.JPanel();
        jLabelEventOwner = new javax.swing.JLabel();
        jLabelEventOriginHost = new javax.swing.JLabel();
        jLabelEventOwnerContent = new javax.swing.JLabel();
        jLabelEventId = new javax.swing.JLabel();
        jLabelEventDate = new javax.swing.JLabel();
        jLabelEventDateContent = new javax.swing.JLabel();
        jLabelEventType = new javax.swing.JLabel();
        jLabelEventTypeContent = new javax.swing.JLabel();
        jTextFieldOriginHostContent = new javax.swing.JTextField();
        jTextFieldEventIdContent = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        jEditorPaneEventBody.setEditable(false);
        jScrollPaneBody.setViewportView(jEditorPaneEventBody);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jScrollPaneBody, gridBagConstraints);

        jTextFieldSubjectContent.setEditable(false);
        jTextFieldSubjectContent.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.background"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        add(jTextFieldSubjectContent, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(jLabelSeverity, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 5);
        add(jLabelOrigin, gridBagConstraints);

        jPanelAdditionalInfo.setLayout(new java.awt.GridBagLayout());

        jLabelEventOwner.setText("Owner:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventOwner, gridBagConstraints);

        jLabelEventOriginHost.setText("Host:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventOriginHost, gridBagConstraints);

        jLabelEventOwnerContent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelEventOwnerContent.setText("<content>");
        jLabelEventOwnerContent.setPreferredSize(new java.awt.Dimension(140, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventOwnerContent, gridBagConstraints);

        jLabelEventId.setText("Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventId, gridBagConstraints);

        jLabelEventDate.setText("Date:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventDate, gridBagConstraints);

        jLabelEventDateContent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelEventDateContent.setText("<content>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventDateContent, gridBagConstraints);

        jLabelEventType.setText("Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventType, gridBagConstraints);

        jLabelEventTypeContent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelEventTypeContent.setText("<content>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jLabelEventTypeContent, gridBagConstraints);

        jTextFieldOriginHostContent.setEditable(false);
        jTextFieldOriginHostContent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextFieldOriginHostContent.setText("<content>");
        jTextFieldOriginHostContent.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jTextFieldOriginHostContent, gridBagConstraints);

        jTextFieldEventIdContent.setEditable(false);
        jTextFieldEventIdContent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextFieldEventIdContent.setText("<content>");
        jTextFieldEventIdContent.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelAdditionalInfo.add(jTextFieldEventIdContent, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        add(jPanelAdditionalInfo, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPaneEventBody;
    private javax.swing.JLabel jLabelEventDate;
    private javax.swing.JLabel jLabelEventDateContent;
    private javax.swing.JLabel jLabelEventId;
    private javax.swing.JLabel jLabelEventOriginHost;
    private javax.swing.JLabel jLabelEventOwner;
    private javax.swing.JLabel jLabelEventOwnerContent;
    private javax.swing.JLabel jLabelEventType;
    private javax.swing.JLabel jLabelEventTypeContent;
    private javax.swing.JLabel jLabelOrigin;
    private javax.swing.JLabel jLabelSeverity;
    private javax.swing.JPanel jPanelAdditionalInfo;
    private javax.swing.JScrollPane jScrollPaneBody;
    private javax.swing.JTextField jTextFieldEventIdContent;
    private javax.swing.JTextField jTextFieldOriginHostContent;
    private javax.swing.JTextField jTextFieldSubjectContent;
    // End of variables declaration//GEN-END:variables
}
