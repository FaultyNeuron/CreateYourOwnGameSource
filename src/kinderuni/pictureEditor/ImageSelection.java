package kinderuni.pictureEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by markus on 27.06.15.
 */
public class ImageSelection {
    private final BufferedImage originalImage;
    private Rectangle selection = null;
    private Resizable resizable = null;
    private ImageIcon imageIcon = null;
    private BufferedImage subimage = null;

    public ImageSelection(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }

    public synchronized ImageIcon getImageIcon() {
        return imageIcon;
    }

    public synchronized Resizable getResizable() {
        return resizable;
    }

    public synchronized void setSelection(Rectangle selection) {
        this.selection = selection;
        update();
    }

    public synchronized void setSelection(int x, int y, int width, int height) {
        this.setSelection(new Rectangle(x, y, width, height));
    }

    public synchronized BufferedImage getSubimage() {
        return subimage;
    }

    private synchronized void update() {
        if (selection != null) {
            subimage = originalImage.getSubimage((int) selection.getX(), (int) selection.getY(), (int) selection.getWidth(), (int) selection.getHeight());
            imageIcon = new ImageIcon(getSubimage());
            resizable = new Resizable(null);
            resizable.setBounds(selection);
        }
    }
}
