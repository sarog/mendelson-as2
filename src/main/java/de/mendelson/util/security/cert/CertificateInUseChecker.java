//$Header: /as2/de/mendelson/util/security/cert/CertificateInUseChecker.java 3     11.11.20 17:06 Heller $
package de.mendelson.util.security.cert;

import java.util.List;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Checks if a certificate is in use
 * @author S.Heller
 * @version $Revision: 3 $
 */
public interface CertificateInUseChecker {

    /**Returns null or an empty list if this certificate is not in use*/
    public List<CertificateInUseInfo> checkUsed(KeystoreCertificate cert); 
    
}
