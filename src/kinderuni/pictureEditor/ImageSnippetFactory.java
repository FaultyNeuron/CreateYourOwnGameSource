package kinderuni.pictureEditor;

import kinderuni.pictureEditor.generalView.ResizableContainerCallback;
import kinderuni.pictureEditor.generalView.ResizableProperties;

import java.awt.image.BufferedImage;

/**
 * Created by markus on 27.06.15.
 */
public class ImageSnippetFactory {
    private static BufferedImage originalImage = null;
    private static double displaySpaceToImageSpaceScaleFactor = 1;
    private static double imageSpaceToDisplaySpaceScaleFactor = 1;
    private static int xOffset = 0, yOffset = 0;
    private static ResizableProperties resizableProperties = null;

    public static synchronized void setOriginalImage(BufferedImage image) {
        originalImage = image;
    }

    public static synchronized void setResizableContainerCallback(ResizableProperties resizableProperties) {
        ImageSnippetFactory.resizableProperties = resizableProperties;
    }

    public static synchronized void setScaleFactor(double displaySpaceToImageSpaceScaleFactor, double imageSpaceToDisplaySpaceScaleFactor) {
        ImageSnippetFactory.displaySpaceToImageSpaceScaleFactor = displaySpaceToImageSpaceScaleFactor;
        ImageSnippetFactory.imageSpaceToDisplaySpaceScaleFactor = imageSpaceToDisplaySpaceScaleFactor;
        System.err.println("Display2ImageScaleFactor=" + displaySpaceToImageSpaceScaleFactor + " Image2DisplayScaleFactor=" + imageSpaceToDisplaySpaceScaleFactor);
    }

    public static synchronized void setDisplaySpaceCoordinateOffset(int xOffset, int yOffset) {
        ImageSnippetFactory.xOffset = xOffset;
        ImageSnippetFactory.yOffset = yOffset;
        System.err.println("Display coordinates offset is x=" + xOffset + " y=" + yOffset);
    }

    public static double getDisplaySpaceToImageSpaceScaleFactor() {
        return displaySpaceToImageSpaceScaleFactor;
    }

    public static double getImageSpaceToDisplaySpaceScaleFactor() {
        return imageSpaceToDisplaySpaceScaleFactor;
    }

    public static int getxOffset() {
        return xOffset;
    }

    public static int getyOffset() {
        return yOffset;
    }

    public static ImageSnippet getImageSnippet() {
        return new ImageSnippet(originalImage, resizableProperties);
    }
}
