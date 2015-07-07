package kinderuni.pictureEditor;

import kinderuni.pictureEditor.generalView.Resizable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by markus on 27.06.15.
 */
public class ImageSnippet {
    private final BufferedImage originalImage;
    private Rectangle snippetRectangleImageSpace = null;
    private Rectangle finalFrameRectangle = null;
    private Resizable resizable = null;
    private ImageIcon imageIcon = null;
    private ImageIcon defaultSizeImageIcon = null;
    private BufferedImage subimage = null;
    private BufferedImage finalFrame = null;
    private int maxImageIconWidthDefault = 100;
    private int maxImageIconHeightDefault = 100;
    private int rThresholdDefault = 256, gThresholdDefault = 256, bThresholdDefault = 256; // Yes, 256, not 255.
    private int rThreshold = rThresholdDefault, gThreshold = gThresholdDefault, bThreshold = bThresholdDefault;
    private final Color transparencyColor = new Color(0, 0, 0, 0);

    public ImageSnippet(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }

    public synchronized ImageIcon getImageIcon() {
        return imageIcon;
    }

    public synchronized ImageIcon getDefaultSizeImageIcon() { return defaultSizeImageIcon; }

    public synchronized ImageIcon getImageIcon(int maxImageIconWith, int maxImageIconHeight) {
        int width, height;
        if (subimage.getWidth() > subimage.getHeight()) {
            width = maxImageIconWith;
            height = (int) (((float)maxImageIconWith / (float)subimage.getWidth()) * (float)subimage.getHeight());
        } else if (subimage.getHeight() > subimage.getWidth()) {
            height = maxImageIconHeight;
            width = (int) (((float)maxImageIconHeight / (float)subimage.getHeight()) * (float)subimage.getWidth());
        } else {
            width = maxImageIconWith;
            height = maxImageIconHeight;
        }
        return new ImageIcon(subimage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH));
    }

    public synchronized Resizable getResizable() {
        return resizable;
    }

    public synchronized boolean setSnippetRectangle(Rectangle snippetRectangle) {
        Rectangle scaledRectangle = getImageSpaceRectangle(snippetRectangle);
        if (isInvalidImageSpaceSnippet(scaledRectangle)) {
            return false;
        }
        if (!cropRectangle(scaledRectangle)) {
            return false;
        }

        this.snippetRectangleImageSpace = scaledRectangle;
        this.finalFrame = null;
        this.finalFrameRectangle = null;
        update();
        return true;
    }

    private boolean cropRectangle(Rectangle rectangle) {
        if (rectangle.getX() >= originalImage.getWidth()) {
            return false; // This should never happen.
        }
        if (rectangle.getX() < 0) {
            rectangle.width = rectangle.width + (int) rectangle.getX();
            if (rectangle.getWidth() <= 0) {
                return false; // This should never happen.
            }
            rectangle.x = 0;
        }
        if (rectangle.getY() >= originalImage.getHeight()) {
            return false; // This should never happen;
        }
        if (rectangle.getY() < 0) {
            rectangle.height = rectangle.height + (int) rectangle.getY();
            if (rectangle.getHeight() <= 0) {
                return false; // This should never happen.
            }
            rectangle.y = 0;
        }
        if (rectangle.getX() + rectangle.getWidth() > originalImage.getWidth()) {
            rectangle.width = originalImage.getWidth() - (int) rectangle.getX();
        }
        if (rectangle.getY() + rectangle.getHeight() > originalImage.getHeight()) {
            rectangle.height = originalImage.getHeight() - (int) rectangle.getY();
        }
        return true;
    }

    private boolean isInvalidImageSpaceSnippet(Rectangle rectangle) {
        boolean isNotValid = false;
        isNotValid = isNotValid || rectangle.getX() > originalImage.getWidth();
        isNotValid = isNotValid || rectangle.getY() > originalImage.getWidth();
        isNotValid = isNotValid || rectangle.getX() + rectangle.getWidth() < 0;
        isNotValid = isNotValid || rectangle.getY() + rectangle.getHeight() < 0;
        return isNotValid;
    }

    public synchronized void setSnippetBounds(int x, int y, int width, int height) {
        this.setSnippetRectangle(new Rectangle(x, y, width, height));
        this.finalFrame = null;
    }

    public synchronized BufferedImage getSubimage() {
        return subimage;
    }

    public synchronized BufferedImage getFinalFrame() {
        if (finalFrame == null) {
            resetFinalFrame();
        }
        return finalFrame;
    }

    private Rectangle getImageSpaceRectangle(Rectangle toScale) {
        double scaleFactor = ImageSnippetFactory.getDisplaySpaceToImageSpaceScaleFactor();
        int xOffset = ImageSnippetFactory.getxOffset();
        int yOffset = ImageSnippetFactory.getyOffset();
        Rectangle scaledRectangle = new Rectangle((int) ((toScale.getX() - (float) xOffset) * scaleFactor),
                (int) ((toScale.getY() - (float) yOffset) * scaleFactor),
                (int) (toScale.getWidth()* scaleFactor),
                (int) (toScale.getHeight()* scaleFactor));
        System.err.println("Rectangle scaled from display to image space: " + toScale + " --> " + scaledRectangle);
        return scaledRectangle;
    }

    private Rectangle getDisplaySpaceRectangle(Rectangle toScale) {
        double scaleFactor = ImageSnippetFactory.getImageSpaceToDisplaySpaceScaleFactor();
        int xOffset = ImageSnippetFactory.getxOffset();
        int yOffset = ImageSnippetFactory.getyOffset();
        double correctionOffset = 1.75;
        Rectangle scaledRectangle = new Rectangle((int) ((toScale.getX()*scaleFactor) + correctionOffset) + xOffset,
                (int) ((toScale.getY()*scaleFactor) + correctionOffset) + yOffset,
                (int) ((toScale.getWidth()*scaleFactor) + correctionOffset),
                (int) ((toScale.getHeight()*scaleFactor) + correctionOffset));
        System.err.println("Rectangle scaled from image to display space: " + toScale + " --> " + scaledRectangle);
        return scaledRectangle;
    }

    /**setSelection
     * Updates everything but finalFrame, because this is a bit costly.
     */
    private synchronized void update() {
        if (snippetRectangleImageSpace != null) {
            subimage = originalImage.getSubimage((int) snippetRectangleImageSpace.getX(), (int) snippetRectangleImageSpace.getY(), (int) snippetRectangleImageSpace.getWidth(), (int) snippetRectangleImageSpace.getHeight());
            imageIcon = new ImageIcon(getSubimage());
            defaultSizeImageIcon = getImageIcon(maxImageIconWidthDefault, maxImageIconHeightDefault);
            resizable = new Resizable(null);
            resizable.setBounds(getDisplaySpaceRectangle(snippetRectangleImageSpace));
        } else {
            System.err.println("ImageSnippet: No snippetRectangleImageSpace available.");
        }
    }

    private int cutValue(int min, int max, int value) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     *
     * @param rectangle The snippet of the snippet, so x and y are relative to the snippet, not the whole image.
     */
    public synchronized void cropFinalFrame(Rectangle rectangle) {
        int maxWidth = (int) snippetRectangleImageSpace.getWidth();
        int maxHeight = (int) snippetRectangleImageSpace.getHeight();
        finalFrameRectangle = new Rectangle(cutValue(0, maxWidth, (int) rectangle.getX()), cutValue(0, maxHeight, (int) rectangle.getY()), cutValue(0, maxWidth, (int) rectangle.getWidth()), cutValue(0, maxHeight, (int) rectangle.getHeight()));
        updateFinalFrame();
    }

    public synchronized void calculateFinalFrameTransparency(int rThreshold, int gThreshold, int bThreshold) {
        this.rThreshold = rThreshold;
        this.gThreshold = gThreshold;
        this.bThreshold = bThreshold;
        updateFinalFrame();
    }

    private synchronized void updateFinalFrame() {
        finalFrame = new BufferedImage((int)finalFrameRectangle.getWidth(), (int)finalFrameRectangle.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int w=0; w<finalFrame.getWidth(); w++ ) {
            for (int h=0; h<finalFrame.getHeight(); h++) {
                int x = w - (int)finalFrameRectangle.getX();
                int y = h - (int)finalFrameRectangle.getY();
//                System.err.println("finalFrameRectangle=" + finalFrameRectangle + "snippetRectangleImageSpace=" + snippetRectangleImageSpace + "w=" + w + " h=" + h + " x=" + x +" y=" + y);
                Color originalColor = new Color(subimage.getRGB(x, y));
                if (originalColor.getRed() > rThreshold && originalColor.getGreen() > gThreshold && originalColor.getBlue() > bThreshold) { // pixel is transparent
                    finalFrame.setRGB(w, h, transparencyColor.getRGB());
                } else {
                    finalFrame.setRGB(w, h, new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), 255).getRGB());
                }
            }
        }
    }

    public void resetFinalFrame() {
        finalFrameRectangle = new Rectangle(0, 0, (int) snippetRectangleImageSpace.getWidth(), (int) snippetRectangleImageSpace.getHeight());
        rThreshold = rThresholdDefault;
        gThreshold = gThresholdDefault;
        bThreshold = bThresholdDefault;
        updateFinalFrame();
        System.err.println("resetFinalFrame called.");
    }
}
