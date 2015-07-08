package kinderuni.pictureEditor;

import kinderuni.pictureEditor.generalView.Resizable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by markus on 29.06.15.
 */
public class ThreadSaveImageSnippetContainer {
    private ArrayList<ImageSnippet> items = new ArrayList<>();
    private static ThreadSaveImageSnippetContainer instance = null;

    private ThreadSaveImageSnippetContainer() {}

    public static ThreadSaveImageSnippetContainer getInstance() {
        if (instance == null) {
            instance = new ThreadSaveImageSnippetContainer();
        }
        return instance;
    }

    public synchronized void add(ImageSnippet item) {
        items.add(item);
    }

    public synchronized void releaseFocus(Point clickPoint) {
        for (ImageSnippet selection: items) {
            Resizable resizable = selection.getResizable();
            Rectangle tempRectangle = new Rectangle(resizable.getX(), resizable.getY(), resizable.getWidth(), resizable.getHeight());
            Rectangle pointRectangle = new Rectangle((int)clickPoint.getX(), (int)clickPoint.getY(), 1, 1);
            System.out.println("" + tempRectangle + pointRectangle);
            Rectangle result = SwingUtilities.computeIntersection((int) clickPoint.getX(), (int) clickPoint.getY(), 1, 1, tempRectangle);
            resizable.setActive(result.getWidth() > 0 && result.getHeight() > 0);
            resizable.repaint();
            System.err.println("### Resizable deactivated");
        }
    }

    public synchronized void remove(ImageSnippet toRemove) {
        items.remove(toRemove);
    }

    public synchronized void remove(int index) {
        items.remove(index);
    }

    public synchronized void deactivateAllResizables() {
        for (ImageSnippet selection : items) {
            selection.getResizable().setActive(false);
        }
    }

    public synchronized ArrayList<ImageSnippet> removeSnippetsWithActiveResizables() {
        ArrayList<ImageSnippet> removedItems = new ArrayList<>();
        Iterator<ImageSnippet> iterator = items.iterator();
        while (iterator.hasNext()) {
            ImageSnippet selection = iterator.next();
            if (selection.getResizable().hasFocus()) {
                removedItems.add(selection);
                iterator.remove();
            }
        }
        return removedItems;
    }

    public synchronized void activateAllResizables() {
        for (ImageSnippet selection : items) {
            selection.getResizable().setActive(true);
        }
    }

    public synchronized void addResizablesToComponent(JComponent component) {
        for (ImageSnippet snippet : items) {
            component.add(snippet.getResizable());
        }
    }

    public synchronized int size() {
        return items.size();
    }

    public synchronized Rectangle getBorderOfFirstSnippet() {
        ImageSnippet snippet = items.get(0);
        if (snippet == null) {
            return null;
        }
        return snippet.getDisplaySpaceRectangle();
    }

    public synchronized ImageSnippet get(int index) throws NoSuchElementException {
        if (index < items.size()) {
            return items.get(index);
        }
        throw new NoSuchElementException();
    }

    public synchronized void set(int index, ImageSnippet snippet) throws NoSuchElementException {
        items.set(index, snippet);
    }

    private synchronized int getMinPictureWidth() {
        int min = Integer.MAX_VALUE;
        for (ImageSnippet snippet : items) {
            min = Math.min(snippet.getFinalFrame().getWidth(), min);
        }
        return min;
    }

    private synchronized int getMinPictureHeight() {
        int min = Integer.MAX_VALUE;
        for (ImageSnippet snippet : items) {
            min = Math.min(snippet.getFinalFrame().getHeight(), min);
        }
        return min;
    }

    public synchronized ArrayList<BufferedImage> getFinalFrames() {
        ArrayList<BufferedImage> images = new ArrayList<>(items.size());
        int minHeight = getMinPictureHeight();

        for (ImageSnippet snippet : items) {
            double scaleFactor = (double) minHeight / (double) snippet.getFinalFrame().getHeight();
            BufferedImage scaledImage = new BufferedImage((int) (snippet.getFinalFrame().getWidth()*scaleFactor), (int) (snippet.getFinalFrame().getHeight() * scaleFactor), BufferedImage.TYPE_4BYTE_ABGR);
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.scale(scaleFactor, scaleFactor);
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
            scaledImage = affineTransformOp.filter(snippet.getFinalFrame(), scaledImage);
            images.add(scaledImage);
        }

        return images;
    }






}
