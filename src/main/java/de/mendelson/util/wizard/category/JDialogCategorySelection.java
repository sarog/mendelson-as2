//$Header: /converteride/de/mendelson/util/wizard/category/JDialogCategorySelection.java 9     27.11.19 10:47 Heller $
package de.mendelson.util.wizard.category;

import de.mendelson.util.MecResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Main class for the category selection wizard
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class JDialogCategorySelection extends JDialog implements CategorySelectionListener {

    /**
     * Localize the GUI
     */
    private MecResourceBundle rb = null;

    /**
     * Synchronized structure that contains action listeners to be informed if a
     * selection occur
     */
    private final List<ActionListener> actionListenerList = Collections.synchronizedList(new ArrayList<ActionListener>());
    /**Close on ESC*/
    private ActionListener actionListenerESC = null;

    /**
     * Creates new form JDialogCategorySelection
     */
    public JDialogCategorySelection(JFrame parent) {
        super(parent, true);
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCategorySelection.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        initComponents();
        this.getRootPane().setDefaultButton(this.jButtonOk);     
        this.actionListenerESC = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        };
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getRootPane().registerKeyboardAction(this.actionListenerESC, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    
    public void addActionListener(ActionListener listener) {
        synchronized (this.actionListenerList) {
            this.actionListenerList.add(listener);
        }
    }

    public void removeActionListener(ActionListener listener) {
        synchronized (this.actionListenerList) {
            this.actionListenerList.remove(listener);
        }
    }

    /**
     * Adds a category to this wizard, this is a tab
     */
    public void addCategory(Category category) {
        JPanelCategory panel = new JPanelCategory(category, this);
        this.jTabbedPane.addTab(category.getTitle(), panel);
    }

    /**
     * Finally informs the listener of a selection
     *
     * @param category Selected category
     * @param index index of the selected subcategory in the category
     * @param actionCommand Action command of the selected subcategory
     */
    private void informListeners(Category category, int index, String actionCommand) {
        synchronized (this.actionListenerList) {
            for (int i = 0; i < this.actionListenerList.size(); i++) {
                ActionListener listener = (ActionListener) this.actionListenerList.get(i);
                listener.actionPerformed(
                        new ActionEvent(category, index, actionCommand));

            }
        }
    }

    /**
     * Informs the listeners that an item has been selected. This could happen
     * via a double click or the OK button
     */
    private synchronized void informListenerItemSelected() {
        //look for selected category
        int selectedTab = this.jTabbedPane.getSelectedIndex();
        JPanelCategory panel
                = (JPanelCategory) this.jTabbedPane.getComponentAt(selectedTab);
        int index = panel.getSelectedSubCategoryIndex();
        Category category = panel.getCategory();
        this.informListeners(category, index,
                category.getSubcategories()[index].getActionCommand());
    }

    /**
     * Makes this a CategorySelectionListener
     */
    @Override
    public void selectionPerformed(CategorySelectionEvent evt) {
        this.setVisible(false);
        this.informListeners(evt.getCategory(),
                evt.getIndex(), evt.getSubcategory().getActionCommand());
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane = new javax.swing.JTabbedPane();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTabbedPane, gridBagConstraints);

        jButtonOk.setText(this.rb.getResourceString( "button.ok"));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jButtonOk, gridBagConstraints);

        jButtonCancel.setText(this.rb.getResourceString( "button.cancel"));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jButtonCancel, gridBagConstraints);

        setSize(new java.awt.Dimension(601, 432));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible(false);
        this.informListenerItemSelected();
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        JDialogCategorySelection dialog
//                = new JDialogCategorySelection(new javax.swing.JFrame());
//        Category category = new Category();
//        category.setTitle("Test Test");
//        for (int i = 0; i < 10; i++) {
//            Subcategory sub = new Subcategory();
//            sub.setActionCommand(category.getTitle() + "_" + sub.getTitle());
//            sub.setTitle("TestSub" + i);
//            sub.setDescription("This is description #" + i);
//            category.addSubcategory(sub);
//        }
//        dialog.addCategory(category);
//        Category category2 = new Category();
//        category2.setTitle("Test2 Test2");
//        dialog.addCategory(category2);
//        dialog.setVisible(true);
//        System.exit(0);
//    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JTabbedPane jTabbedPane;
    // End of variables declaration//GEN-END:variables

}
