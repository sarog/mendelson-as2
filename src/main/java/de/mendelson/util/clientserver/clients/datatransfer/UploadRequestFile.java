//$Header: /oftp2/de/mendelson/util/clientserver/clients/datatransfer/UploadRequestFile.java 5     17.02.21 11:49 Heller $
package de.mendelson.util.clientserver.clients.datatransfer;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol: Upload a file to the server, to a specified file name
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class UploadRequestFile extends ClientServerMessage implements Serializable {

    public static final long serialVersionUID = 1L;
    private String targetFilename = null;
    /**The upload hash that referes the file that is already transferred for this request*/
    private String uploadHash = null;


    @Override
    public String toString() {
        return ("Upload request file");
    }

    /**
     * @return the targetFilename
     */
    public String getTargetFilename() {
        return targetFilename;
    }

    /**
     * @param targetFilename the targetFilename to set
     */
    public void setTargetFilename(String targetFilename) {
        this.targetFilename = targetFilename;
    }

    /**
     * @return the uploadHash
     */
    public String getUploadHash() {
        return uploadHash;
    }

    /**
     * @param uploadHash the uploadHash to set
     */
    public void setUploadHash(String uploadHash) {
        this.uploadHash = uploadHash;
    }

}
