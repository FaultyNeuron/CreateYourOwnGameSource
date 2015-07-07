package kinderuni.pictureEditor.generalView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
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

    public Resizable(Component comp) {
        this(comp, new ResizableBorder(8));
    }

    public Resizable(Component comp, ResizableBorder border) {
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
    }

    MouseInputListener resizeListener = new MouseInputAdapter() {
        public void mouseMoved(MouseEvent me) {
            System.err.println("Resizable mouseInputListener mouseMoved");
            if (hasFocus()) {
                ResizableBorder border = (ResizableBorder)getBorder();
                setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
                System.err.println("Resizable mouseInputListener mouseMoved and hasFocus");
            }
        }

        public void mouseExited(MouseEvent mouseEvent) {
            System.err.println("Resizable mouseInputListener mouseExited");
            setCursor(Cursor.getDefaultCursor());
        }

        private int cursor;
        private Point startPos = null;

        public void mousePressed(MouseEvent me) {
            Resizable.this.callReleaseFocusCallback(me.getPoint());
            ResizableBorder border = (ResizableBorder)getBorder();
            cursor = border.getCursor(me);
            startPos = me.getPoint();
            requestFocus();
            repaint();
            System.err.println("Resizable resizeListener mousePressed");
        }

        public void mouseDragged(MouseEvent me) {
            System.err.println("Resizable mouseInputListener mousePressed");
            if (startPos != null) {
                System.err.println("Resizable mouseInputListener mousePressed and startPos != null");
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
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < minsize)) {
                            setBounds(x, y, w, h + dy);
                            startPos = me.getPoint();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < minsize)) {
                            setBounds(x + dx, y, w - dx, h);
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < minsize)) {
                            setBounds(x, y, w + dx, h);
                            startPos = me.getPoint();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < minsize) && !(h - dy < minsize)) {
                            setBounds(x + dx, y + dy, w - dx, h - dy);
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < minsize) && !(h - dy < minsize)) {
                            setBounds(x, y + dy, w + dx, h - dy);
                            startPos = new Point(me.getX(), startPos.y);
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < minsize) && !(h + dy < minsize)) {
                            setBounds(x + dx, y, w - dx, h + dy);
                            startPos = new Point(startPos.x, me.getY());
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < minsize) && !(h + dy < minsize)) {
                            setBounds(x, y, w + dx, h + dy);
                            startPos = me.getPoint();
                        }
                        break;

                    case Cursor.MOVE_CURSOR:
                        Rectangle bounds = getBounds();
                        bounds.translate(dx, dy);
                        setBounds(bounds);
                }


                setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }

        public void mouseReleased(MouseEvent mouseEvent) {
            startPos = null;
            System.err.println("Resizable mouseInputListener mouseReleased");
        }
    };
}