//$Header: /as2/de/mendelson/comm/as2/clientserver/message/ModuleLockResponse.java 3     4/03/18 12:27p Heller $
package de.mendelson.comm.as2.clientserver.message;

import de.mendelson.util.modulelock.LockClientInformation;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
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
 * @version $Revision: 3 $
 */
public class ModuleLockResponse extends ClientServerResponse implements Serializable {

    private LockClientInformation lockKeeper = null;
    private boolean success = false;
    
    public ModuleLockResponse(ModuleLockRequest request) {
        super(request);
    }

    
    @Override
    public String toString() {
        return ("Module lock response");
    }

    /**
     * @return the clientInformation
     */
    public LockClientInformation getLockKeeper() {
        return lockKeeper;
    }

    /**
     * @param lockKeeper the clientInformation to set
     */
    public void setLockKeeper(LockClientInformation lockKeeper) {
        this.lockKeeper = lockKeeper;
    }

    /**
     * @return the success
     */
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
