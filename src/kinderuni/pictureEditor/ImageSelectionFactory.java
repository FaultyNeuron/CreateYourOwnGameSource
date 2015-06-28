package kinderuni.pictureEditor;

import java.awt.image.BufferedImage;

/**
 * Created by markus on 27.06.15.
 */
public class ImageSelectionFactory {
    private static BufferedImage originalImage = null;

    public static synchronized void setOriginalImage(BufferedImage image) {
        originalImage = image;
    }

    public static ImageSnippet getImageSelection() {
        return new ImageSnippet(originalImage);
    }
}
