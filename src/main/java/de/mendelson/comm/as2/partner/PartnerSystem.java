//$Header: /mec_as2/de/mendelson/comm/as2/partner/PartnerSystem.java 6     18.12.20 9:54 Heller $
package de.mendelson.comm.as2.partner;

import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Container that stores information about the system of a partner
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class PartnerSystem implements Serializable{

    public static final long serialVersionUID = 1L;
    private Partner partner = null;
    private String as2Version = "--";
    private String productName = "--";
    private boolean compression = false;
    private boolean ma = false;
    private boolean cem = false;

    /**
     * @return the partner
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    /**
     * @return the as2Version
     */
    public String getAS2Version() {
        return as2Version;
    }

    /**
     * @param as2Version the as2Version to set
     */
    public void setAS2Version(String as2Version) {
        this.as2Version = as2Version;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the compression
     */
    public boolean supportsCompression() {
        return compression;
    }

    /**
     * @param compression the compression to set
     */
    public void setCompression(boolean compression) {
        this.compression = compression;
    }

    /**
     * @return the ma
     */
    public boolean supportsMA() {
        return ma;
    }

    /**
     * @param ma the ma to set
     */
    public void setMa(boolean ma) {
        this.ma = ma;
    }

    /**
     * @return the cem
     */
    public boolean supportsCEM() {
        return cem;
    }

    /**
     * @param cem the cem to set
     */
    public void setCEM(boolean cem) {
        this.cem = cem;
    }




}
