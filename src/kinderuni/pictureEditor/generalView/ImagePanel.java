package kinderuni.pictureEditor.generalView;

import kinderuni.pictureEditor.ImageSnippetFactory;
import kinderuni.pictureEditor.language.Language;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

/**
 * Created by markus on 25.06.15.
 */
public class ImagePanel extends JPanel implements FileDroppedCallback {
    private BufferedImage image;
    private DragAndDropComponent dragAndDropComponent;

    public ImagePanel() {
        this.setLayout(new GridBagLayout());
//        this.setBackground(Color.YELLOW);

        dragAndDropComponent = new DragAndDropComponent(Language.OPEN_IMAGE_SHORTCUT_HINT, this, 500, 500, this.getBackground());
        this.add(dragAndDropComponent);
        this.setBorder(BorderFactory.createBevelBorder(0));
    }

    public void setImage(String path) throws IOException {
        openImage(path);
        this.remove(dragAndDropComponent);
        this.repaint();
    }

    private void openImage(String path) throws IOException {
        System.err.println("Attempt to open image " + path);
        image = ImageIO.read(new File(path));
        if (image == null) {
            throw new IOException();
        }
        ImageSnippetFactory.setOriginalImage(image);
        System.err.println("File '" + path + "' read, size=" + image.getWidth() + "x" + image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (image == null) {
            return;
        }

        Graphics2D graphics2D = (Graphics2D) graphics;

        double xScaleFactor = (float) this.getWidth() / (float) image.getWidth();
        double yScaleFactor = (float) this.getHeight() / (float) image.getHeight();
        double scaleFactor = Math.min(xScaleFactor, yScaleFactor);

        ImageSnippetFactory.setScaleFactor(1/scaleFactor, scaleFactor);
        ImageSnippetFactory.setDisplaySpaceCoordinateOffset((int) ((this.getWidth() - image.getWidth()*scaleFactor)/2), (int) ((this.getHeight() - image.getHeight()*scaleFactor)/2));

        graphics2D.translate(this.getWidth() / 2, this.getHeight() / 2);
        graphics2D.translate(-image.getWidth() * scaleFactor / 2, -image.getHeight() * scaleFactor / 2);
        graphics2D.drawImage(image, AffineTransform.getScaleInstance(scaleFactor, scaleFactor), null);

        System.err.println("Image drawn.");
    }

    @Override
    public void filesDropped(File[] files) {
        if (files == null) {
            return;
        }
        if (files.length == 1) {
            try {
                setImage(files[0].getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, Language.COULD_NOT_OPEN_IMAGE);
            }
            return;
        }
        if (files.length == 0) {
            JOptionPane.showMessageDialog(this, Language.NO_FILE_DROPPED);
        } else if (files.length != 1) {
            JOptionPane.showMessageDialog(this, Language.DROP_ONLY_ONE_FILE);
        }
    }
}