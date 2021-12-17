//$Header: /as2/de/mendelson/util/uinotification/NotificationPanel.java 9     14.02.20 12:54 Heller $package de.mendelson.util.uinotification;
package de.mendelson.util.uinotification;

import de.mendelson.util.ColorUtil;
import de.mendelson.util.MendelsonMultiResolutionImage;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author heller
 */
public class NotificationPanel extends JPanel {

    protected static final Color DEFAULT_COLOR_BACKGROUND_SUCCESS = new Color(200, 255, 200);
    protected static final Color DEFAULT_COLOR_BACKGROUND_WARNING = new Color(255, 255, 200);
    protected static final Color DEFAULT_COLOR_BACKGROUND_ERROR = new Color(255, 200, 200);
    protected static final Color DEFAULT_COLOR_BACKGROUND_INFORMATION = new Color(222, 231, 251);
    
    protected static final Color DEFAULT_COLOR_FOREGROUND_TITLE = Color.BLACK;
    protected static final Color DEFAULT_COLOR_FOREGROUND_DETAILS = Color.BLACK;

    private int notificationType = UINotification.TYPE_SUCCESS;
    
    protected final static int CLOSE_CROSS_SIZE = 12;

    private MendelsonMultiResolutionImage image;
    private final int MAX_NOTIFICATION_DETAILS_LENGTH = 100;

    /**
     * @param image Image to display - there is a default if this is null
     * @param NOTIFICATION_TYPE One of UINotification.TYPE_OK,
     * UINotification.TYPE_WARNING, UINotification.TYPE_ERROR
     * @param notificationTitle The title of the notification - not folded -
     * means you have to ensure a short title. If this is null, the type OK,
     * WARNING, ERROR is displayed in the localized language of the current
     * locale
     * @param notificationDetails Details of the notification - folded, means
     * this could be some longer if required. If the length of the details
     * exceeds the defined MAX_NOTIFICATION_DETAILS_LENGTH it is simply cut off
     */
    public NotificationPanel(MendelsonMultiResolutionImage image,
            final int NOTIFICATION_TYPE, String notificationTitle, String notificationDetails, Rectangle bounds) {
        this.notificationType = NOTIFICATION_TYPE;
        this.image = image;

        initComponents();
        this.setMultiresolutionIcons();
        if (NOTIFICATION_TYPE == UINotification.TYPE_SUCCESS) {
            this.setBackground(DEFAULT_COLOR_BACKGROUND_SUCCESS);
        } else if (NOTIFICATION_TYPE == UINotification.TYPE_WARNING) {
            this.setBackground(DEFAULT_COLOR_BACKGROUND_WARNING);
        } else if (NOTIFICATION_TYPE == UINotification.TYPE_ERROR) {
            this.setBackground(DEFAULT_COLOR_BACKGROUND_ERROR);
        } else if (NOTIFICATION_TYPE == UINotification.TYPE_INFORMATION) {
            this.setBackground(DEFAULT_COLOR_BACKGROUND_INFORMATION);
        }
        this.jLabelNotificationTitle.setText(notificationTitle);
        if (notificationDetails != null) {
            if (notificationDetails.length() > MAX_NOTIFICATION_DETAILS_LENGTH) {
                notificationDetails = notificationDetails.substring(0, MAX_NOTIFICATION_DETAILS_LENGTH - 1);
            }
            this.jLabelNotificationDetails.setText("<HTML>" + notificationDetails + "</HTML>");
        } else {
            this.jLabelNotificationDetails.setText("");
        }        
        this.jLabelNotificationDetails.setForeground(DEFAULT_COLOR_FOREGROUND_DETAILS);
        this.jLabelNotificationTitle.setForeground(DEFAULT_COLOR_FOREGROUND_TITLE);
    }

    private void setMultiresolutionIcons() {
        this.image = UINotification.getMultiresolutionImage(this.image, this.notificationType);
        this.jLabelIcon.setIcon(new ImageIcon(this.image.toMinResolution(32)));
        this.jLabelCross.setIcon(new ImageIcon(UINotification.IMAGE_CROSS.toMinResolution(CLOSE_CROSS_SIZE)));
    }

    protected JLabel getCloseCrossLabel(){
        return( this.jLabelCross);
    }
    
    
    /**
     * Redefines the used background colors for the panels
     */
    public void setBackgroundColors(Color backgroundSuccess, Color backgroundWarning, Color backgroundError, Color backgroundInformation) {
        if (this.notificationType == UINotification.TYPE_SUCCESS) {
            this.setBackground(backgroundSuccess);
        } else if (this.notificationType == UINotification.TYPE_WARNING) {
            this.setBackground(backgroundWarning);
        } else if (this.notificationType == UINotification.TYPE_ERROR) {
            this.setBackground(backgroundError);
        } else if (this.notificationType == UINotification.TYPE_INFORMATION) {
            this.setBackground(backgroundInformation);
        }
    }

    /**
     * Redefines the used foreground colors for the panels
     */
    public void setForegroundColors(Color foregroundTitle, Color foregroundDetails) {        
        this.jLabelNotificationTitle.setForeground(foregroundTitle);
        this.jLabelNotificationDetails.setForeground(foregroundDetails);
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

        jLabelIcon = new javax.swing.JLabel();
        jLabelNotificationDetails = new javax.swing.JLabel();
        jLabelNotificationTitle = new javax.swing.JLabel();
        jLabelCross = new javax.swing.JLabel();
        jLabelSpace1 = new javax.swing.JLabel();
        jLabelSpace2 = new javax.swing.JLabel();
        jLabelSpace3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(222, 231, 251));
        setLayout(new java.awt.GridBagLayout());

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/uinotification/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        add(jLabelIcon, gridBagConstraints);

        jLabelNotificationDetails.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jLabelNotificationDetails.setText("Notification details");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 10);
        add(jLabelNotificationDetails, gridBagConstraints);

        jLabelNotificationTitle.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabelNotificationTitle.setText("Title");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        add(jLabelNotificationTitle, gridBagConstraints);

        jLabelCross.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/uinotification/missing_image16x16.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        add(jLabelCross, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(jLabelSpace1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(jLabelSpace2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(jLabelSpace3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelCross;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelNotificationDetails;
    private javax.swing.JLabel jLabelNotificationTitle;
    private javax.swing.JLabel jLabelSpace1;
    private javax.swing.JLabel jLabelSpace2;
    private javax.swing.JLabel jLabelSpace3;
    // End of variables declaration//GEN-END:variables
}
