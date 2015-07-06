package kinderuni.pictureEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by markus on 26.06.15.
 */
public class ResizableRectanglePanel extends JPanel implements ResizableContainerCallback, KeyListener {
    private ArrayList<Resizable> resizables = new ArrayList<>();
    private Rectangle tmpRectangle = null;
    DrawRectangleMouseListener drawRectangleMouseListener = new DrawRectangleMouseListener(10, 10);
    private int resizableBorderWidth = 7;
    private TaskFinishedCallback taskFinishedCallback = null;
    private ArrayList<ImageSnippet> imageSnippets;

    public ResizableRectanglePanel(ArrayList<ImageSnippet> imageSnippets) {
        this.imageSnippets = imageSnippets;

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
        for (ImageSnippet selection: imageSnippets) {
            Resizable resizable = selection.getResizable();
            Rectangle tempRectangle = new Rectangle(resizable.getX(), resizable.getY(), resizable.getWidth(), resizable.getHeight());
            Rectangle pointRectangle = new Rectangle((int)clickPoint.getX(), (int)clickPoint.getY(), 1, 1);
            System.out.println("" + tempRectangle + pointRectangle);
            Rectangle result = SwingUtilities.computeIntersection((int)clickPoint.getX(), (int)clickPoint.getY(), 1, 1, tempRectangle);
            resizable.setActive(result.getWidth() > 0 && result.getHeight() > 0);
            resizable.repaint();
        }
    }

    private void addRectangle(Rectangle rectangle) {
        this.deactivateAllResizables();
        ImageSnippet selection = ImageSelectionFactory.getImageSelection();
        selection.setSnippetRectangle(new Rectangle((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight()));
        Resizable resizable = selection.getResizable();
        resizable.setResizableContainerCallback(this);
        resizable.addKeyListener(this);
        resizable.setActive(true);
        this.imageSnippets.add(selection);
        this.add(resizable);
        this.setTmpRectangle(null);
    }

    private void setTmpRectangle(Rectangle rectangle) {
        this.tmpRectangle = rectangle;
        this.repaint();
    }

    private void deactivateAllResizables() {
        for (ImageSnippet selection : imageSnippets) {
            selection.getResizable().setActive(false);
        }
    }

    private void removeActiveResizables() {
        Iterator<ImageSnippet> iterator = imageSnippets.iterator();
        while (iterator.hasNext()) {
            ImageSnippet selection = iterator.next();
            if (selection.getResizable().hasFocus()) {
                this.remove(selection.getResizable());
                iterator.remove();
            }
        }
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
        for (ImageSnippet selection : imageSnippets) {
            selection.getResizable().setActive(true);
        }
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
        }

        public void mousePressed(MouseEvent e) {
            x = origx = (int)e.getX();
            y = origy = (int)e.getY();
            System.err.println("New rectangle point: " + x + " " + y);
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
                System.err.println("Mouse moved. Rectangle: " + rectangle);
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
