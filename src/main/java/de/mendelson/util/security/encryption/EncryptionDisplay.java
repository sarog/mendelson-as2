//$Header: /as2/de/mendelson/util/security/encryption/EncryptionDisplay.java 1     16.09.21 15:24 Heller $
package de.mendelson.util.security.encryption;

import java.util.Objects;
import javax.swing.ImageIcon;

/**
 * Container superclass for the encryption rendering
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public abstract class EncryptionDisplay{

    private Object wrappedValue;
    
    public EncryptionDisplay( Object wrappedValue ){
        this.wrappedValue = wrappedValue;
    }
    
    
    public abstract ImageIcon getIcon();
    
    public abstract String getText();
    
    public Object getWrappedValue(){
        return( this.wrappedValue );
    }

/**
         * Overwrite the equal method of object
         *
         * @param anObject object ot compare
         */
        @Override
        public boolean equals(Object anObject) {
            if (anObject == this) {
                return (true);
            }
            if (anObject != null && anObject instanceof EncryptionDisplay) {
                EncryptionDisplay entry = (EncryptionDisplay) anObject;
                return (entry.wrappedValue.equals( this.wrappedValue));
            }
            return (false);
        }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.wrappedValue);
        return hash;
    }
    
}
