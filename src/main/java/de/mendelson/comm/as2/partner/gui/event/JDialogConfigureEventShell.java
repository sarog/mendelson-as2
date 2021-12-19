//$Header: /mec_as2/de/mendelson/comm/as2/partner/gui/event/JDialogConfigureEventShell.java 5     18.12.20 14:25 Heller $
package de.mendelson.comm.as2.partner.gui.event;

import de.mendelson.comm.as2.client.AS2Gui;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerEventInformation;
import de.mendelson.util.MecResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
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
 * Configure a shell execution command
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class JDialogConfigureEventShell extends JDialog {

    private final MecResourceBundle rb;
    private JFrame parent;
    private Partner partner;
    private int eventType;

    /**
     * Creates new form JDialogMigrateFromHSQLDB
     */
    public JDialogConfigureEventShell(JFrame parent,
            Partner partner, final int EVENT_TYPE) {
        super(parent, true);
        this.parent = parent;
        this.partner = partner;
        this.eventType = EVENT_TYPE;
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePartnerEvent.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.setTitle(this.rb.getResourceString("title.configuration.shell",
                new Object[]{
                    partner.getName(),
                    this.rb.getResourceString("type." + EVENT_TYPE)
                }
        ));
        initComponents();
        this.setMultiresolutionIcons();
        this.jLabelInfo.setText(this.rb.getResourceString("label.shell.info"));
        this.jLabelReplacement.setText(this.rb.getResourceString("shell.hint.replacement." + EVENT_TYPE));
        this.jLabelSamples.setText(this.rb.getResourceString("shell.hint.samples"));
        this.jLabelCommand.setText(this.rb.getResourceString("label.shell.command",
                this.rb.getResourceString("type." + EVENT_TYPE)));
        this.displayParameter();
        this.getRootPane().setDefaultButton(this.jButtonOk);
    }

    private void setMultiresolutionIcons() {
        this.jLabelImage.setIcon(new ImageIcon(PartnerEventResource.IMAGE_PROCESS_EXECUTE_SHELL.toMinResolution(AS2Gui.IMAGE_SIZE_DIALOG)));
    }

    /**
     * Just fill in the parameter if the partner processtype is the one of this
     * dialog - else this is a create call
     *
     */
    private void displayParameter() {
        if (this.partner.getPartnerEvents().getProcess(this.eventType)
                == PartnerEventInformation.PROCESS_EXECUTE_SHELL) {
            List<String> parameter = this.partner.getPartnerEvents().getParameter(this.eventType);
            if (parameter.size() > 0) {
                this.jTextFieldCommand.setText(parameter.get(0));
            }
        }
    }

    private void captureGUIValues() {
        List<String> newParameter = new ArrayList<String>();
        String command = this.jTextFieldCommand.getText();
        newParameter.add(command);
        this.partner.getPartnerEvents().setParameter(this.eventType, newParameter);
        this.partner.getPartnerEvents().setProcess(this.eventType, PartnerEventInformation.PROCESS_EXECUTE_SHELL);
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

        jPanelButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelImage = new javax.swing.JLabel();
        jLabelInfo = new javax.swing.JLabel();
        jPanelMain = new javax.swing.JPanel();
        jLabelCommand = new javax.swing.JLabel();
        jTextFieldCommand = new javax.swing.JTextField();
        jPanelSpace = new javax.swing.JPanel();
        jLabelReplacement = new javax.swing.JLabel();
        jLabelSamples = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jButtonOk.setText(this.rb.getResourceString( "button.ok"));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 5);
        jPanelButtons.add(jButtonOk, gridBagConstraints);

        jButtonCancel.setText(this.rb.getResourceString( "button.cancel")
        );
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        jPanelButtons.add(jButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanelButtons, gridBagConstraints);

        jLabelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/comm/as2/partner/gui/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabelImage, gridBagConstraints);

        jLabelInfo.setText("Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        getContentPane().add(jLabelInfo, gridBagConstraints);

        jPanelMain.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelMain.setLayout(new java.awt.GridBagLayout());

        jLabelCommand.setText("Command:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        jPanelMain.add(jLabelCommand, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanelMain.add(jTextFieldCommand, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelMain.add(jPanelSpace, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jPanelMain, gridBagConstraints);

        jLabelReplacement.setText("Replacement");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 20, 20);
        getContentPane().add(jLabelReplacement, gridBagConstraints);

        jLabelSamples.setText("Samples");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 20, 20);
        getContentPane().add(jLabelSamples, gridBagConstraints);

        setSize(new java.awt.Dimension(755, 418));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.captureGUIValues();
        this.setVisible(false);
    }//GEN-LAST:event_jButtonOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelCommand;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelReplacement;
    private javax.swing.JLabel jLabelSamples;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSpace;
    private javax.swing.JTextField jTextFieldCommand;
    // End of variables declaration//GEN-END:variables
}
