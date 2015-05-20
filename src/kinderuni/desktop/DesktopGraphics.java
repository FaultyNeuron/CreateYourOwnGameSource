package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.AbstractGraphicsObject;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public abstract class DesktopGraphics extends AbstractGraphicsObject {
    public DesktopGraphics(double width, double height) {
        super(new DoubleTupel(width, height));
    }
    public DesktopGraphics(DoubleTupel dimensions) {
        super(dimensions);
    }
    public abstract void drawTo(Graphics drawTo, DoubleTupel center);

}
