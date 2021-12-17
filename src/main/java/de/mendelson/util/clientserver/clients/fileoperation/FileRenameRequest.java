//$Header: /as2/de/mendelson/util/clientserver/clients/fileoperation/FileRenameRequest.java 2     4/06/18 12:21p Heller $
package de.mendelson.util.clientserver.clients.fileoperation;

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
 * Msg for the client server protocol
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class FileRenameRequest extends ClientServerMessage implements Serializable{

    public static final long serialVersionUID = 1L;
    private String oldName = null;
    private String newName = null;

    @Override
    public String toString(){
        return( "File rename request" );
    }

    /**
     * @return the oldName
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * @param oldName the oldName to set
     */
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * @return the newName
     */
    public String getNewName() {
        return newName;
    }

    /**
     * @param newName the newName to set
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }
        
}
