//$Header: /as2/de/mendelson/comm/as2/server/UpgradeRequiredException.java 1     18.01.12 9:53 Heller $
package de.mendelson.comm.as2.server;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Exception that is thrown if a database upgrade is required
 * @author  S.Heller
 */
public class UpgradeRequiredException extends Exception {

    public UpgradeRequiredException(String message) {
        super(message);
    }
}
