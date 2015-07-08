package kinderuni.pictureEditor.generalView;

import kinderuni.pictureEditor.ImageSnippet;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

/**
 * Created by markus on 26.06.15.
 * source: http://zetcode.com/tutorials/javaswingtutorial/resizablecomponent/
 */
public class Resizable extends JComponent {
    private ResizableContainerCallback resizableContainerCallback = null;
    private boolean isActive = false;
    private int minsize = 20;
    private ImageSnippet imageSnippet;

    public Resizable(ImageSnippet imageSnippet, Component comp) {
        this(imageSnippet, comp, new ResizableBorder(8));
    }

    public Resizable(ImageSnippet imageSnippet, Component comp, ResizableBorder border) {
        this.imageSnippet = imageSnippet;
        setLayout(new BorderLayout());
        if (comp != null) {
            add(comp);
        }
        addMouseListener(resizeListener);
        addMouseMotionListener(resizeListener);
        setBorder(border);
    }

    public void setResizableContainerCallback(ResizableContainerCallback callback) {
        this.resizableContainerCallback = callback;
    }

    private void callReleaseFocusCallback(Point clickPoint) {
        if (resizableContainerCallback != null) {
            clickPoint.setLocation(clickPoint.getX() + this.getX(), clickPoint.getY() + this.getY());
            resizableContainerCallback.releaseFocus(clickPoint);
        }
    }

    public boolean hasFocus() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        this.repaint();
    }

    MouseInputListener resizeListener = new MouseInputAdapter() {
        private Rectangle moveStart = null;
        private boolean moved = false, resized = false;
        private int cursor;
        private Point startPos = null;

        public void mouseMoved(MouseEvent me) {
//            System.err.println("Resizable mouseInputListener mouseMoved");
            if (hasFocus()) {
                ResizableBorder border = (ResizableBorder)getBorder();
                setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
//                System.err.println("Resizable mouseInputListener mouseMoved and hasFocus");
            }
        }

        public void mouseExited(MouseEvent mouseEvent) {
//            System.err.println("Resizable mouseInputListener mouseExited");
            setCursor(Cursor.getDefaultCursor());
        }

        public void mousePressed(MouseEvent me) {
            this.moveStart = new Rectangle(getBounds());
            Resizable.this.callReleaseFocusCallback(me.getPoint());
            ResizableBorder border = (ResizableBorder)getBorder();
            cursor = border.getCursor(me);
            startPos = me.getPoint();
            requestFocus();
            repaint();
//            System.err.println("Resizable resizeListener mousePressed");
        }

        public void mouseDragged(MouseEvent me) {
//            System.err.println("Resizable mouseInputListener mousePressed");
            if (startPos != null) {
//                System.err.println("Resizable mouseInputListener mousePressed and startPos != null");
                int x = getX();
                int y = getY();
                int w = getWidth();
                int h = getHeight();

                int dx = me.getX() - startPos.x;
                int dy = me.getY() - startPos.y;

                switch (cursor) {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < minsize)) {
                            setBounds(x, y + dy, w, h - dy);
                            resized = true;
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < minsize)) {
                            setBounds(x, y, w, h + dy);
                            startPos = me.getPoint();
                            resized = true;
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < minsize)) {
                            setBounds(x + dx, y, w - dx, h);
                            resized = true;
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < minsize)) {
                            setBounds(x, y, w + dx, h);
                            startPos = me.getPoint();
                            resized = true;
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < minsize) && !(h - dy < minsize)) {
                            setBounds(x + dx, y + dy, w - dx, h - dy);
                            resized = true;
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < minsize) && !(h - dy < minsize)) {
                            setBounds(x, y + dy, w + dx, h - dy);
                            startPos = new Point(me.getX(), startPos.y);
                            resized = true;
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < minsize) && !(h + dy < minsize)) {
                            setBounds(x + dx, y, w - dx, h + dy);
                            startPos = new Point(startPos.x, me.getY());
                            resized = true;
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < minsize) && !(h + dy < minsize)) {
                            setBounds(x, y, w + dx, h + dy);
                            startPos = me.getPoint();
                            resized = true;
                        }
                        break;

                    case Cursor.MOVE_CURSOR:
                        Rectangle bounds = getBounds();
                        bounds.translate(dx, dy);
                        setBounds(bounds);
                        moved = true;
//                        setBounds(imageSnippet.moveResizable(bounds));
//                        System.err.println("Resizable bounding rectangle: " + bounds);
//                        if (imageSnippet.moveResizable(bounds)) {
//                            setBounds(bounds);
//                            System.err.println("Resizable moved");
//                        } else {
//                            System.err.println("Resizable not moved.");
//                        }
                }


                setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }

        public void mouseReleased(MouseEvent mouseEvent) {
            startPos = null;
            handleMove();
            handleResize();


//            System.err.println("Resizable mouseInputListener mouseReleased");
        }

        private void handleMove() {
            if (moved) {
                imageSnippet.moveResizable(getBounds());
            }
            moveStart = null;
            moved = false;
        }

        private void handleResize() {
            if (resized) {
                imageSnippet.resizeResizable(getBounds());
//                setBounds(imageSnippet.getResizable().getBounds());
            }
            resized = false;
        }
    };
}