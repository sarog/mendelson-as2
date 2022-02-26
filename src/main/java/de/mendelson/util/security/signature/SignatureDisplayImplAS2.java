//$Header: /as2/de/mendelson/util/security/signature/SignatureDisplayImplAS2.java 3     16.09.21 15:04 Heller $
package de.mendelson.util.security.signature;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;

/**
 * Container superclass for the signature rendering
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class SignatureDisplayImplAS2 extends SignatureDisplay{
    
    /**
     * Icons, multi resolution
     */
    public final static MendelsonMultiResolutionImage IMAGE_SIGNATURE_STRONG
            = MendelsonMultiResolutionImage.fromSVG("/util/security/signature/signature_strong.svg",
                    ListCellRendererSignature.IMAGE_HEIGHT, ListCellRendererSignature.IMAGE_HEIGHT * 2);
    public final static MendelsonMultiResolutionImage IMAGE_SIGNATURE_WEAK
            = MendelsonMultiResolutionImage.fromSVG("/util/security/signature/signature_weak.svg",
                    ListCellRendererSignature.IMAGE_HEIGHT, ListCellRendererSignature.IMAGE_HEIGHT * 2);
    public final static MendelsonMultiResolutionImage IMAGE_SIGNATURE_BROKEN
            = MendelsonMultiResolutionImage.fromSVG("/util/security/signature/signature_broken.svg",
                    ListCellRendererSignature.IMAGE_HEIGHT, ListCellRendererSignature.IMAGE_HEIGHT * 2);
    
    private MecResourceBundle rb;
    
    
    public SignatureDisplayImplAS2( Integer wrappedValue ){
        super(wrappedValue );
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSignatureAS2.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    @Override
    public ImageIcon getIcon() {   
        Integer signatureInt = (Integer)this.getWrappedValue();
        return( this.getRenderImage(signatureInt.intValue()));
    }

    @Override
    public String getText() {
        return( this.rb.getResourceString("signature." + this.getWrappedValue().toString()));
    }
    
    /**
     * Computes the render image by the given signature constant
     *
     * @param signature
     */
    private ImageIcon getRenderImage(int signature) {
        if (signature == SignatureConstantsAS2.SIGNATURE_NONE) {
            return (new ImageIcon(IMAGE_SIGNATURE_BROKEN.toMinResolution(ListCellRendererSignature.IMAGE_HEIGHT)));
        } else if (signature == SignatureConstantsAS2.SIGNATURE_MD5
                || signature == SignatureConstantsAS2.SIGNATURE_SHA1) {
            return (new ImageIcon(IMAGE_SIGNATURE_WEAK.toMinResolution(ListCellRendererSignature.IMAGE_HEIGHT)));
        } else {
            return (new ImageIcon(IMAGE_SIGNATURE_STRONG.toMinResolution(ListCellRendererSignature.IMAGE_HEIGHT)));
        }
    }
    
    
    
}
