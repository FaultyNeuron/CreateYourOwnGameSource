package kinderuni.pictureEditor.generalView;

import kinderuni.pictureEditor.ImageSnippet;
import kinderuni.pictureEditor.ImageSnippetFactory;
import kinderuni.pictureEditor.TaskFinishedCallback;
import kinderuni.pictureEditor.ThreadSaveImageSnippetContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

/**
 * Created by markus on 26.06.15.
 */
public class ResizableRectanglePanel extends JPanel implements ResizableProperties {
    private Rectangle tmpRectangle = null;
    DrawRectangleMouseListener drawRectangleMouseListener = new DrawRectangleMouseListener(10, 10);
    private TaskFinishedCallback taskFinishedCallback = null;
//    private ArrayList<ImageSnippet> imageSnippets;
    private ThreadSaveImageSnippetContainer imageSnippetContainer;

    public ResizableRectanglePanel(ThreadSaveImageSnippetContainer imageSnippetContainer) {
//        this.imageSnippets = imageSnippets;
        this.imageSnippetContainer = imageSnippetContainer;

        this.setBackground(Color.BLUE);
        this.setOpaque(false);

        this.addMouseListener(drawRectangleMouseListener);
        this.addMouseMotionListener(drawRectangleMouseListener);
        this.addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        AffineTransform oldTransform = graphics2D.getTransform();
        graphics2D.setTransform(oldTransform);

        if (tmpRectangle != null) {
            graphics2D.drawRect((int) tmpRectangle.getX(), (int) tmpRectangle.getY(), (int) tmpRectangle.getWidth(), (int) tmpRectangle.getHeight());
        }
    }

    public void releaseFocus(Point clickPoint) {
        imageSnippetContainer.releaseFocus(clickPoint);
//        int size = imageSnippetContainer.size();
//        try {
//            for (int i = 0; i < size; i++) {
//                ImageSnippet selection = imageSnippetContainer.get(i);
//                Resizable resizable = selection.getResizable();
//                Rectangle tempRectangle = new Rectangle(resizable.getX(), resizable.getY(), resizable.getWidth(), resizable.getHeight());
//                Rectangle pointRectangle = new Rectangle((int)clickPoint.getX(), (int)clickPoint.getY(), 1, 1);
//                System.out.println("" + tempRectangle + pointRectangle);
//                Rectangle result = SwingUtilities.computeIntersection((int)clickPoint.getX(), (int)clickPoint.getY(), 1, 1, tempRectangle);
//                resizable.setActive(result.getWidth() > 0 && result.getHeight() > 0);
//                resizable.repaint();
//                size = imageSnippetContainer.size();
//            }
//        } catch (NoSuchElementException e) {}

//        for (ImageSnippet selection: imageSnippets) {
//            Resizable resizable = selection.getResizable();
//            Rectangle tempRectangle = new Rectangle(resizable.getX(), resizable.getY(), resizable.getWidth(), resizable.getHeight());
//            Rectangle pointRectangle = new Rectangle((int)clickPoint.getX(), (int)clickPoint.getY(), 1, 1);
//            System.out.println("" + tempRectangle + pointRectangle);
//            Rectangle result = SwingUtilities.computeIntersection((int)clickPoint.getX(), (int)clickPoint.getY(), 1, 1, tempRectangle);
//            resizable.setActive(result.getWidth() > 0 && result.getHeight() > 0);
//            resizable.repaint();
//        }
    }

    private void addRectangle(Rectangle rectangle) {
        ImageSnippet selection = ImageSnippetFactory.getImageSnippet();
        if (selection.setSnippetRectangle(new Rectangle((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight()))) {
            this.deactivateAllResizables();
            Resizable resizable = selection.getResizable();
//            resizable.setResizableContainerCallback(this);
//            resizable.addKeyListener(this);
            resizable.setActive(true);
            this.imageSnippetContainer.add(selection);
//            this.imageSnippets.add(selection);
            this.add(resizable);
        }
        this.setTmpRectangle(null);
    }

    public void replaceImageSnippet(ImageSnippet old, Rectangle newSnippet) {
        this.remove(old.getResizable());
        this.imageSnippetContainer.remove(old);
        addRectangle(newSnippet);
    }

    private void setTmpRectangle(Rectangle rectangle) {
        this.tmpRectangle = rectangle;
        this.repaint();
    }

    private void deactivateAllResizables() {
        imageSnippetContainer.deactivateAllResizables();
//        int size = imageSnippetContainer.size();
//        try {
//            for (int i = 0; i < size; i++) {
//                imageSnippetContainer.get(i).getResizable().setActive(false);
//                size = imageSnippetContainer.size();
//            }
//        } catch (NoSuchElementException e) {}
//        for (ImageSnippet selection : imageSnippets) {
//            selection.getResizable().setActive(false);
//        }
    }

    private void removeActiveResizables() {
        for(ImageSnippet snippet : imageSnippetContainer.removeSnippetsWithActiveResizables()) {
            this.remove(snippet.getResizable());
        }

//        int size = imageSnippetContainer.size();
//        try {
//            for (int i = 0; i < size; i++) {
//                ImageSnippet selection = imageSnippetContainer.get(i);
//                if (selection.getResizable().hasFocus()) {
//                    this.remove(selection.getResizable());
//                    imageSnippetContainer.remove(selection);
//                }
//                size = imageSnippetContainer.size();
//            }
//        } catch (NoSuchElementException e) {}


//        Iterator<ImageSnippet> iterator = imageSnippets.iterator();
//        while (iterator.hasNext()) {
//            ImageSnippet selection = iterator.next();
//            if (selection.getResizable().hasFocus()) {
//                this.remove(selection.getResizable());
//                iterator.remove();
//            }
//        }
//        Resizable resizable = null;
//        do {
//            resizable = null;
//            for (Resizable res : resizables) {
//                if (res.hasFocus()) {
//                    resizable = res;
//                    break;
//                }
//            }
//            removeResizable(resizable);
//        } while (resizable !=null);
    }

    private void activateAllResizables() {
        imageSnippetContainer.activateAllResizables();
//        int size = imageSnippetContainer.size();
//        try {
//            for (int i = 0; i < size; i++) {
//                ImageSnippet selection = imageSnippetContainer.get(i);
//                selection.getResizable().setActive(true);
//                size = imageSnippetContainer.size();
//            }
//        } catch (NoSuchElementException e) {}
//        for (ImageSnippet selection : imageSnippets) {
//            selection.getResizable().setActive(true);
//        }
    }

    public void setTaskFinishedCallback(TaskFinishedCallback taskFinishedCallback) {
        this.taskFinishedCallback = taskFinishedCallback;
    }

    private void callTaskFinishedCallback() { taskFinishedCallback.taskFinished(null); }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        boolean repaint = false;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.deactivateAllResizables();
            repaint = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            this.removeActiveResizables();
            repaint = true;
        } else if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_A)) {
            this.activateAllResizables();
            repaint = true;
        } else if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_ENTER)) {
            callTaskFinishedCallback();
        }
        if (repaint) {
            this.repaint();
        }
    }

    private class DrawRectangleMouseListener implements MouseListener, MouseMotionListener {
        private int x, y, origx, origy, width, height, minwidth, minheight;
        private boolean isMousePressed = false;

        public DrawRectangleMouseListener(int minwidth, int minheight) {
            this.minwidth = minwidth;
            this.minheight = minheight;
        }

        public void mouseClicked(MouseEvent e) {
            ResizableRectanglePanel.this.releaseFocus(e.getPoint());
            System.err.println("Releasing focus for point " + e.getPoint());
        }

        public void mousePressed(MouseEvent e) {
            x = origx = (int)e.getX();
            y = origy = (int)e.getY();
//            System.err.println("New rectangle point: " + x + " " + y);
            isMousePressed = true;
        }

        public void mouseReleased(MouseEvent e) {
            isMousePressed = false;
            calculateSize(e.getX(), e.getY());

            if (width < minwidth || height < minheight) {
                System.err.println("Rectangle too small to draw.");
                ResizableRectanglePanel.this.setTmpRectangle(null);
                return;
            }

            ResizableRectanglePanel.this.addRectangle(getRectangle());
            System.err.println("New rectangle");
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseDragged(MouseEvent e) {
            if (isMousePressed) {
                calculateSize(e.getX(), e.getY());
                Rectangle rectangle = getRectangle();
                ResizableRectanglePanel.this.setTmpRectangle(rectangle);
//                System.err.println("Mouse moved. Rectangle: " + rectangle);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        private Rectangle getRectangle() {
            return new Rectangle(x, y, width, height);
        }

        private void calculateSize(int currentX, int currentY) {
            if (origx > currentX) {
                width = origx - currentX;
                x = currentX;
            } else {
                width = currentX - x;
                x = origx;
            }
            if (origy > currentY) {
                height = origy - currentY;
                y = currentY;
            } else {
                height = currentY - origy;
                y = origy;
            }
        }
    }

}
