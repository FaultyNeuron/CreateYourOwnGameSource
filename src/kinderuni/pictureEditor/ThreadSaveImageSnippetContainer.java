package kinderuni.pictureEditor;

import kinderuni.pictureEditor.generalView.Resizable;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by markus on 29.06.15.
 */
public class ThreadSaveImageSnippetContainer {
    private ArrayList<ImageSnippet> items = new ArrayList<>();

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






}
