//$Header: /as2/de/mendelson/util/security/cert/TableModelCertificates.java 16    11.11.20 17:06 Heller $
package de.mendelson.util.security.cert;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
import de.mendelson.util.security.DNUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * table model to display a configuration grid
 *
 * @author S.Heller
 * @version $Revision: 16 $
 */
public class TableModelCertificates extends AbstractTableModel {

    public static final int ROW_HEIGHT = 20;
    protected static final int IMAGE_HEIGHT = ROW_HEIGHT-3;
    
    /**
     * Icons, multi resolution
     */
    public final static MendelsonMultiResolutionImage ICON_CERTIFICATE_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/certificate.svg", IMAGE_HEIGHT, IMAGE_HEIGHT*2);
    public final static MendelsonMultiResolutionImage ICON_KEY_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/key.svg", IMAGE_HEIGHT, IMAGE_HEIGHT*2);    
    public final static MendelsonMultiResolutionImage ICON_INVALID_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/cert_invalid.svg", IMAGE_HEIGHT, IMAGE_HEIGHT*2);
    public final static MendelsonMultiResolutionImage ICON_VALID_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/cert_valid.svg", IMAGE_HEIGHT, IMAGE_HEIGHT*2);
    public final static MendelsonMultiResolutionImage ICON_ROOT_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/cert_root.svg", IMAGE_HEIGHT, IMAGE_HEIGHT*2);
    public final static MendelsonMultiResolutionImage ICON_UNTRUSTED_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/cert_untrusted.svg", IMAGE_HEIGHT, IMAGE_HEIGHT*2);

    public static final ImageIcon ICON_CERTIFICATE = new ImageIcon(ICON_CERTIFICATE_MULTIRESOLUTION.toMinResolution(IMAGE_HEIGHT));
    public static final ImageIcon ICON_KEY = new ImageIcon(ICON_KEY_MULTIRESOLUTION.toMinResolution(IMAGE_HEIGHT));
    public static final ImageIcon ICON_VALID = new ImageIcon(ICON_VALID_MULTIRESOLUTION.toMinResolution(IMAGE_HEIGHT));
    public static final ImageIcon ICON_INVALID = new ImageIcon(ICON_INVALID_MULTIRESOLUTION.toMinResolution(IMAGE_HEIGHT));
    public static final ImageIcon ICON_CERTIFICATE_ROOT = new ImageIcon(ICON_ROOT_MULTIRESOLUTION.toMinResolution(IMAGE_HEIGHT));
    public static final ImageIcon ICON_CERTIFICATE_MISSING = new ImageIcon(ICON_UNTRUSTED_MULTIRESOLUTION.toMinResolution(IMAGE_HEIGHT));
    /*ResourceBundle to localize the headers*/
    private MecResourceBundle rb = null;
    private final List<KeystoreCertificate> data = Collections.synchronizedList(new ArrayList<KeystoreCertificate>());

    /**
     * Creates new table model
     */
    public TableModelCertificates() {
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleTableModelCertificates.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Passes data to the model and fires a table data update
     *
     * @param list new data to display
     */
    public void setNewData(List<KeystoreCertificate> data) {
        synchronized (this.data) {
            this.data.clear();
            this.data.addAll(data);
        }
        ((AbstractTableModel) this).fireTableDataChanged();
    }

    public List<KeystoreCertificate> getCurrentCertificateList(){
        List<KeystoreCertificate> copyList = new ArrayList<KeystoreCertificate>();
        synchronized( this.data ){
            copyList.addAll(this.data);
        }
        return( copyList );
    }
    
    
    /**
     * returns the number of rows in the table
     */
    @Override
    public int getRowCount() {
        synchronized (this.data) {
            return (this.data.size());
        }
    }

    /**
     * returns the number of columns in the table. should be const for a table
     */
    @Override
    public int getColumnCount() {
        return (7);
    }

    /**
     * Returns the name of every column
     *
     * @param col Column to get the header name of
     */
    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return (" ");
        }
        if (col == 1) {
            return ("  ");
        }
        if (col == 2) {
            return (this.rb.getResourceString("header.alias"));
        }
        if (col == 3) {
            return (this.rb.getResourceString("header.expire"));
        }
        if (col == 4) {
            return (this.rb.getResourceString("header.length"));
        }
        if (col == 5) {
            return (this.rb.getResourceString("header.organization"));
        }
        if (col == 6) {
            return (this.rb.getResourceString("header.ca"));
        }
        //should not happen
        return ("");
    }

    public ImageIcon getIconForCertificate(KeystoreCertificate certificate) {
        if (certificate.getIsKeyPair()) {
            return (ICON_KEY);
        }
        if (certificate.isRootCertificate()) {
            return (ICON_CERTIFICATE_ROOT);
        }
        return (ICON_CERTIFICATE);
    }

    /**
     * Returns the grid value
     */
    @Override
    public Object getValueAt(int row, int col) {
        KeystoreCertificate certificate = null;
        synchronized (this.data) {
            certificate = this.data.get(row);
        }
        if (col == 0) {
            return (this.getIconForCertificate(certificate));
        }
        if (col == 1) {
            try {
                certificate.getX509Certificate().checkValidity();
                return (ICON_VALID);
            } catch (Exception e) {
                return (ICON_INVALID);
            }
        }
        if (col == 2) {
            return (certificate.getAlias());
        }
        if (col == 3) {
            return (certificate.getNotAfter());
        }
        if (col == 4) {
            return (String.valueOf(certificate.getPublicKeyLength()));
        }
        if (col == 5) {
            return (DNUtil.getOrganization(certificate.getX509Certificate(), DNUtil.SUBJECT));
        }
        if (col == 6) {
            return (DNUtil.getCommonName(certificate.getX509Certificate(), DNUtil.ISSUER));
        }
        return ("");
    }

    /**
     * Swing GUI checks which cols are editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return (false);
    }

    /**
     * Set how to display the grid elements
     *
     * @param col requested column
     */
    @Override
    public Class getColumnClass(int col) {
        return (new Class[]{
            ImageIcon.class,
            ImageIcon.class,
            String.class,
            Date.class,
            String.class,
            String.class,
            String.class,}[col]);
    }

    /**
     * Returns the certificate at the passed row
     */
    public KeystoreCertificate getParameter(int row) {
        synchronized (this.data) {
            return (this.data.get(row));
        }
    }

    public KeystoreCertificate[] getCertificatesAsArray() {
        synchronized (this.data) {
            KeystoreCertificate[] certArray = new KeystoreCertificate[this.data.size()];
            certArray = this.data.toArray(certArray);
            return (certArray);
        }
    }
}
