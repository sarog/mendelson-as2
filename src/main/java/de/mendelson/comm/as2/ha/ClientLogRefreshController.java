//$Header: /mec_as2/de/mendelson/comm/as2/ha/ClientLogRefreshController.java 1     2/02/22 15:13 Heller $
package de.mendelson.comm.as2.ha;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Checks the number of transactions in the system and informs the attached
 * clients that there is a change. This will help synchronizing the log display
 * between all clients if there are multiple nodes in the network that work
 * together (HA)
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class ClientLogRefreshController {


    public ClientLogRefreshController(Object a, Object b,
            Object c, Object d) throws Exception {
    }

    public void startHALogRefreshControl() {
    }

  
}
