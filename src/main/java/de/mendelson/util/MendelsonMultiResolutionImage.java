//$Header: /as2/de/mendelson/util/MendelsonMultiResolutionImage.java 27    6.11.20 10:54 Heller $
package de.mendelson.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.AbstractMultiResolutionImage;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Mendelson implementation of the MultiResolution image
 *
 * @author S.Heller
 * @version $Revision: 27 $
 */
public class MendelsonMultiResolutionImage extends AbstractMultiResolutionImage implements Serializable {

    public static final long serialVersionUID = 1L;

    private int baseImageIndex;
    private List<BufferedImage> resolutionVariants;

    /**
     * Stores the global list of image operations that could be performed while
     * creating a rasted image from a SVG, e.g. darken the icon etc
     */
    private static final List<BufferedImageOp> svgImageOperations = Collections.synchronizedList(new ArrayList<BufferedImageOp>());
    /**
     * Stores a list of overlays for special SVG resources. The overlay is
     * displayed always in front if a resource is loaded Useful to address color
     * blindness UI design
     */
    private static final Map<String, String> svgImageOverlaysMap = Collections.synchronizedMap(new HashMap<String, String>());

    public static enum SVGScalingOption {
        KEEP_HEIGHT, KEEP_WIDTH
    }

    /**
     * Creates a multi-resolution image with the given base image index and
     * resolution variants.
     *
     * @param baseImageIndex the index of the base image in the resolution
     * variants array
     * @param resolutionVariantResource array of the resolution variants -
     * should be sorted by image size
     *
     */
    public MendelsonMultiResolutionImage(int baseImageIndex, String[] resolutionVariantResources) {
        if (resolutionVariantResources == null || resolutionVariantResources.length == 0) {
            System.out.println("ResolutionVariant resources must not be empty or null");
            throw new IllegalArgumentException("ResolutionVariant resources must not be empty or null");
        }
        if (baseImageIndex < 0 || baseImageIndex >= resolutionVariantResources.length) {
            System.out.println("Invalid base image index: " + baseImageIndex);
            throw new IndexOutOfBoundsException("Invalid base image index: "
                    + baseImageIndex);
        }
        this.baseImageIndex = baseImageIndex;
        List<BufferedImage> imageList = new ArrayList<BufferedImage>();
        for (String resourceStr : resolutionVariantResources) {
            try {
                URL resourceURL = MendelsonMultiResolutionImage.class.getResource(resourceStr);
                if (resourceURL == null) {
                    throw new RuntimeException("Resource missing: " + resourceStr);
                }
                ImageIcon icon = new ImageIcon(resourceURL);
                Image image = icon.getImage();
                //ensure to use BufferedImages only!
                imageList.add(toBufferedImage(image));
            } catch (Throwable e) {
                System.out.println("Resource missing: " + resourceStr);
                throw new RuntimeException("Resource missing: " + resourceStr);
            }
        }
        this.resolutionVariants = imageList;
        for (Image resolutionVariant : this.resolutionVariants) {
            Objects.requireNonNull(resolutionVariant,
                    "Resolution variants must not be null");
        }
    }

    /**
     * Creates a multi-resolution image with the given resolution variants. The
     * first resolution variant is used as the base image.
     *
     * @param resolutionVariantResources array of the resolution variants -
     * should be sorted by image size
     */
    public MendelsonMultiResolutionImage(String[] resolutionVariantResources) {
        this(0, resolutionVariantResources);
    }

    /**
     * Creates a multi-resolution image with the given resolution variants. The
     * first resolution variant is used as the base image.
     *
     * @param resolutionVariants array of the resolution variants - should be
     * sorted by image size
     */
    public MendelsonMultiResolutionImage(Image[] resolutionVariants) {
        this(0, resolutionVariants);
    }

    /**
     * Creates a multi-resolution image with the given base image index and
     * resolution variants.
     *
     * @param baseImageIndex the index of the base image in the resolution
     * variants array
     * @param resolutionVariants array of the resolution variants - should be
     * sorted by image size
     *
     */
    public MendelsonMultiResolutionImage(int baseImageIndex, Image[] resolutionVariants) {
        if (resolutionVariants == null || resolutionVariants.length == 0) {
            System.out.println("ResolutionVariants must not be empty or null");
            throw new IllegalArgumentException("ResolutionVariants must not be empty or null");
        }
        if (baseImageIndex < 0 || baseImageIndex >= resolutionVariants.length) {
            System.out.println("Invalid base image index: "
                    + baseImageIndex);
            throw new IndexOutOfBoundsException("Invalid base image index: "
                    + baseImageIndex);
        }
        this.baseImageIndex = baseImageIndex;
        this.resolutionVariants = new ArrayList<BufferedImage>();
        for (Image image : resolutionVariants) {
            this.resolutionVariants.add(toBufferedImage(image));
        }
        for (Image resolutionVariant : this.resolutionVariants) {
            Objects.requireNonNull(resolutionVariant,
                    "Resolution variants must not be null");
        }
    }

    private MendelsonMultiResolutionImage(int baseImageIndex, List<Image> resolutionVariants) {
        if (resolutionVariants == null || resolutionVariants.isEmpty()) {
            System.out.println("ResolutionVariants must not be empty or null");
        }
        this.baseImageIndex = baseImageIndex;
        this.resolutionVariants = new ArrayList<BufferedImage>();
        for (Image image : resolutionVariants) {
            this.resolutionVariants.add(toBufferedImage(image));
        }
        for (Image resolutionVariant : this.resolutionVariants) {
            Objects.requireNonNull(resolutionVariant,
                    "Resolution variants must not be null");
        }
    }

    /**
     * Generates multiple resolutions from a vector image and stores them as
     * bitmaps for the multi resolution image
     */
    public static MendelsonMultiResolutionImage fromSVG(String svgURLStr, int initialSize, int maxSize) {
        return (fromSVG(svgURLStr, initialSize, maxSize, SVGScalingOption.KEEP_WIDTH));
    }

    /**
     * A svgURLStr defines the resource String of the SVG file to be loaded,
     * e.g. "/de/mendelson/comm/as2/client/warning_sign.svg". This method
     * extracts the real filename which would be "warning_sign.svg" in this case
     *
     * @param svgURLStr
     * @return
     */
    private static String extractSVGFilenameFromSVGURLStr(String svgURLStr) {
        String[] parts = svgURLStr.split("/");
        if (parts.length == 1) {
            return (svgURLStr);
        } else {
            return (parts[parts.length - 1]);
        }
    }

    /**
     * Generates multiple resolutions from a vector image and stores them as
     * bitmaps for the multi resolution image
     */
    public static MendelsonMultiResolutionImage fromSVG(String svgURLStr, int initialSize, int maxSize, SVGScalingOption scale) {
        //check if an overlay is defined for this SVG resource
        String svgResourceFilename = extractSVGFilenameFromSVGURLStr(svgURLStr);
        MendelsonMultiResolutionImage overlayImage = null;
        synchronized (svgImageOverlaysMap) {
            if (svgImageOverlaysMap.containsKey(svgResourceFilename)) {
                //load overlay image in all required resolutions
                overlayImage = fromSVG(svgImageOverlaysMap.get(svgResourceFilename), initialSize, maxSize, scale);
            }
        }
        if (initialSize > maxSize) {
            System.out.println("MendelsonMultiResolutionImage:fromSVG(..): minWidth must be smaller than maxWidth");
            throw new IllegalArgumentException("MendelsonMultiResolutionImage:fromSVG(..): minResolution must be smaller than maxResolution");
        }
        List<Image> svgResolutionVariants = new ArrayList<Image>(maxSize - initialSize + 1);
        URL url = MendelsonMultiResolutionImage.class.getResource(svgURLStr);
        try {
            if (url == null) {
                throw new IllegalArgumentException("Resource missing: " + svgURLStr);
            }
            //find out the initial size of the SVG to compute the scale factor
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(
                    XMLResourceDescriptor.getXMLParserClassName());
            UserAgent agent = new UserAgentAdapter();
            DocumentLoader loader = new DocumentLoader(agent);
            BridgeContext context = new BridgeContext(agent, loader);
            context.setDynamic(true);
            GVTBuilder builder = new GVTBuilder();
            Document document = factory.createDocument(url.toExternalForm());
            GraphicsNode root = builder.build(context, document);
            double width = root.getBounds().getWidth();
            double height = root.getBounds().getHeight();
            //try to find out the canvas size. If this fails just take the bounds of the root node
            Element documentElement = document.getDocumentElement();
            if (documentElement.hasAttribute("width") && documentElement.hasAttribute("height")) {
                String canvasSizeWidthStr = document.getDocumentElement().getAttribute("width");
                String canvasSizeHeightStr = document.getDocumentElement().getAttribute("height");
                try {
                    Double canvasSizeWidth = Double.parseDouble(canvasSizeWidthStr);
                    Double canvasSizeHeight = Double.parseDouble(canvasSizeHeightStr);
                    if (canvasSizeWidth > 0 && canvasSizeHeight > 0) {
                        //both values seem to be valid -> take these values
                        width = canvasSizeWidth;
                        height = canvasSizeHeight;
                    }
                } catch (Exception e) {
                    //dont care, keep the bounds
                }
            }
            float scalingfactor = (float) (height / width);
            //transcode the SVG to a BufferedImage
            TranscoderInput transcoderInput = new TranscoderInput(document);
            BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
            int step = 1;
            //use larger steps if this is not just a small pixel perfect icon. Else the prerendering process
            //will take some time. Means the larger the image is the less resolutions are pre rendered
            if (initialSize > 64) {
                step = 2;
            }
            for (int i = initialSize; i <= maxSize; i += step) {
                float variantWidth = 0;
                float variantHeight = 0;
                if (scale == SVGScalingOption.KEEP_WIDTH) {
                    variantWidth = i;
                    variantHeight = i * scalingfactor;

                } else {
                    variantWidth = i / scalingfactor;
                    variantHeight = i;
                }
                transcoder.setDimensions(variantWidth, variantHeight);
                transcoder.transcode(transcoderInput, null);
                BufferedImage resolutionVariant = transcoder.getBufferedImage();
                //add the overlay if this is defined for this resource
                if (overlayImage != null) {
                    BufferedImage bufferedOverlayImage = overlayImage.getResolutionVariant(variantWidth, variantHeight);
                    Graphics g = resolutionVariant.getGraphics();
                    g.drawImage(bufferedOverlayImage, 0, 0, null);
                }
                //filter the created image if this is requested
                synchronized (svgImageOperations) {
                    for (BufferedImageOp imageOperation : svgImageOperations) {
                        resolutionVariant = imageOperation.filter(resolutionVariant, null);
                    }
                }
                svgResolutionVariants.add(resolutionVariant);
            }
        } catch (Throwable e) {
            System.out.println("MendelsonMultiResolutionImage:fromSVG(..): ["
                    + e.getClass().getSimpleName() + "] " + e.getMessage());
            throw new RuntimeException("MendelsonMultiResolutionImage:fromSVG(..): ["
                    + e.getClass().getSimpleName() + "] " + e.getMessage());
        }
        MendelsonMultiResolutionImage multiResolutionImage = new MendelsonMultiResolutionImage(0, svgResolutionVariants);
        return (multiResolutionImage);
    }

    /**
     * Allows to add global valid image operations that are performed every time
     * an image is created from a SVG source. e.g. if you like to darken the
     * image by 10%: MendelsonMultiresolutionImage.addSVGImageOperation(new
     * RescaleOp(.9f, 0, null));
     *
     * @param imageOperation
     */
    public static void addSVGImageOperation(BufferedImageOp imageOperation) {
        synchronized (svgImageOperations) {
            svgImageOperations.add(imageOperation);
        }
    }

    public static void addSVGOverlay(String svgOriginalResource, String svgOverlayResource) {
        synchronized (svgImageOverlaysMap) {
            svgImageOverlaysMap.put(svgOriginalResource, svgOverlayResource);
        }
    }

    /**
     * Returns a multi resolution image that has the passed min resolution
     */
    public MendelsonMultiResolutionImage toMinResolution(int minResolution) {
        int newBaseImageIndex = 0;
        //find start index of images that matches the min resolution        
        for (int i = 0; i < this.resolutionVariants.size(); i++) {
            Image resolutionVariantImage = this.resolutionVariants.get(i);
            if (minResolution <= resolutionVariantImage.getWidth(null)
                    && minResolution <= resolutionVariantImage.getHeight(null)) {
                newBaseImageIndex = i;
                break;
            }
        }
        //check if the base index is already at the right place for the requested resolution 
        //- then no resolution change is required and the current image could be returned without a change
        if (newBaseImageIndex == this.baseImageIndex) {
            return (this);
        } else {
            MendelsonMultiResolutionImage newImage
                    = new MendelsonMultiResolutionImage(newBaseImageIndex, 
                            new ArrayList<Image>(this.resolutionVariants));
            return (newImage);
        }
    }

    /**
     * Mainly this should return a normal Image class but the SurfaceManager
     * casts internal to a BufferedImage. If this fails it comes back with a
     * "Invalid Image variant". To have this problem already in our code and not
     * in the swing code the check is done already here
     *
     * @param destImageWidth
     * @param destImageHeight
     * @return
     */
    @Override
    public BufferedImage getResolutionVariant(double destImageWidth, double destImageHeight) {
        checkSize(destImageWidth, destImageHeight);
        for (Image resolutionVariantImage : this.resolutionVariants) {
            if (destImageWidth <= resolutionVariantImage.getWidth(null)
                    && destImageHeight <= resolutionVariantImage.getHeight(null)) {
                return (BufferedImage) resolutionVariantImage;
            }
        }
        //nothings fits: return the image with the hightest resolution
        return (BufferedImage) (this.resolutionVariants.get(this.resolutionVariants.size() - 1));
    }

    private static void checkSize(double width, double height) {
        if (width <= 0 || height <= 0) {
            System.out.println(String.format(
                    "MendelsonMultiResolutionImage: Width (%s) or height (%s) cannot be <= 0", width, height));
            throw new IllegalArgumentException(String.format(
                    "MendelsonMultiResolutionImage: Width (%s) or height (%s) cannot be <= 0", width, height));
        }

        if (!Double.isFinite(width) || !Double.isFinite(height)) {
            System.out.println(String.format("MendelsonMultiResolutionImage: Width (%s) or height (%s) is not finite", width, height));
            throw new IllegalArgumentException(String.format(
                    "MendelsonMultiResolutionImage: Width (%s) or height (%s) is not finite", width, height));
        }
    }

    @Override
    public List<Image> getResolutionVariants() {
        return Collections.unmodifiableList(this.resolutionVariants);
    }

    @Override
    protected Image getBaseImage() {
        return (this.resolutionVariants.get(this.baseImageIndex));
    }

    /**
     * Converts a given Image into a BufferedImage. It is possible that loaded
     * images are of the class sun.awt.image.ToolkitImage. The SWING
     * SurfaceManager expects an Object of the class BufferedImage even if all
     * SWING return values are defined as the BufferedImages Superclass Image -
     * if not all images of class BufferedImage SWING comes back with a "Invalid
     * Image variant". Looks like a bug in Swing.
     *
     * @param image The Image to be converted
     * @return The converted BufferedImage
     */
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        // Return the buffered image
        return bufferedImage;
    }

    /**
     * A transcoder that generates a BufferedImage instead of transcoding the
     * input to a transcoding output
     */
    protected static class BufferedImageTranscoder extends ImageTranscoder {

        /**
         * The BufferedImage that is generated from the SVG document input
         */
        private BufferedImage bufferedImage;

        public BufferedImageTranscoder() {
            super();
            //assumed the SVG is in the right structure...
            super.hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, Boolean.FALSE);
        }

        @Override
        protected ImageRenderer createRenderer() {
            ImageRenderer renderer = super.createRenderer();
            RenderingHints renderingHints = renderer.getRenderingHints();
            renderingHints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));
            renderingHints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC));
            renderingHints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON));
            renderingHints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
                    RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
            renderingHints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_QUALITY));
            renderingHints.add(new RenderingHints(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_NORMALIZE));
            renderer.setRenderingHints(renderingHints);
            return (renderer);
        }

        /**
         * Creates a new ARGB image with the specified dimension.
         *
         * @param width the image width in pixels
         * @param height the image height in pixels
         */
        @Override
        public BufferedImage createImage(int width, int height) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        /**
         * Writes the specified image to the specified output.
         *
         * @param image the image to write
         * @param output the output where to store the image
         * @param TranscoderException if an error occured while storing the
         * image
         */
        @Override
        public void writeImage(BufferedImage image, TranscoderOutput output)
                throws TranscoderException {
            this.bufferedImage = image;
        }

        /**
         * Returns the BufferedImage generated from the SVG document.
         */
        public BufferedImage getBufferedImage() {
            return (this.bufferedImage);
        }

        /**
         * Set the dimensions to be used for the image.
         */
        public void setDimensions(float width, float height) {
            super.hints.put(ImageTranscoder.KEY_WIDTH, width);
            super.hints.put(ImageTranscoder.KEY_HEIGHT, height);
            super.setImageSize(width, height);
        }
    }

}
