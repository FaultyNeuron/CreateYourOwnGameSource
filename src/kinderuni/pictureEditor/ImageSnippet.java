package kinderuni.pictureEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by markus on 27.06.15.
 */
public class ImageSnippet {
    private final BufferedImage originalImage;
    private Rectangle snippetRectangle = null;
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

    public synchronized void setSnippetRectangle(Rectangle snippetRectangle) {
        this.snippetRectangle = snippetRectangle;
        this.finalFrame = null;
        this.finalFrameRectangle = null;
        update();
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

    /**setSelection
     * Updates everything but finalFrame, because this is a bit costly.
     */
    private synchronized void update() {
        if (snippetRectangle != null) {
            subimage = originalImage.getSubimage((int) snippetRectangle.getX(), (int) snippetRectangle.getY(), (int) snippetRectangle.getWidth(), (int) snippetRectangle.getHeight());
            imageIcon = new ImageIcon(getSubimage());
            defaultSizeImageIcon = getImageIcon(maxImageIconWidthDefault, maxImageIconHeightDefault);
            resizable = new Resizable(null);
            resizable.setBounds(snippetRectangle);
        } else {
            System.err.println("ImageSnippet: No snippetRectangle available.");
        }
    }

    private int cutValue(int min, int max, int value) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     *
     * @param rectangle The snippet if the snippet, so x and y are relative to the snippet, not the whole image.
     */
    public synchronized void cropFinalFrame(Rectangle rectangle) {
        int maxWidth = (int)snippetRectangle.getWidth();
        int maxHeight = (int)snippetRectangle.getHeight();
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
//                System.err.println("finalFrameRectangle=" + finalFrameRectangle + "snippetRectangle=" + snippetRectangle + "w=" + w + " h=" + h + " x=" + x +" y=" + y);
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
        finalFrameRectangle = new Rectangle(0, 0, (int) snippetRectangle.getWidth(), (int) snippetRectangle.getHeight());
        rThreshold = rThresholdDefault;
        gThreshold = gThresholdDefault;
        bThreshold = bThresholdDefault;
        updateFinalFrame();
        System.err.println("resetFinalFrame called.");
    }
}
