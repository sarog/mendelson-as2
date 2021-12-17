//$Header: /as2/de/mendelson/util/clientserver/messages/ServerInfo.java 5     4/06/18 12:22p Heller $
package de.mendelson.util.clientserver.messages;

import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class ServerInfo extends ClientServerMessage implements Serializable {

    public static final long serialVersionUID = 1L;
    private String productname = null;

    /**
     * @return the productname
     */
    public String getProductname() {
        return productname;
    }

    /**
     * @param productname the productname to set
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }

    @Override
    public String toString() {
        return ("Server info " + this.productname != null ? this.productname : "");
    }
}
