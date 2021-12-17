//$Header: /as2/de/mendelson/util/clientserver/messages/ServerLogMessage.java 5     4/06/18 12:22p Heller $
package de.mendelson.util.clientserver.messages;

import java.io.Serializable;
import java.util.logging.Level;
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
public class ServerLogMessage extends ClientServerMessage implements Serializable{

    public static final long serialVersionUID = 1L;
    private Level level = Level.INFO;
        
    private String message = null;
    
    private Object[] parameter = null;
    
    public ServerLogMessage(){
    }    
    
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }
 
        
    @Override
    public String toString(){
        return( "Server message '" + this.getMessage() + "'");
    }

    
}
