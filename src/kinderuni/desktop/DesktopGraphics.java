package kinderuni.desktop;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.AbstractGraphicsObject;

import java.awt.*;
import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class DesktopGraphics extends AbstractGraphicsObject {
    public DesktopGraphics() {
    }

    public DesktopGraphics(File path) {

    }

    public void drawTo(Graphics drawTo, Box boundingBox){
        if(!isBlinking() || blinkShow()) {
            drawTo.drawRect((int) boundingBox.getLeft(),
                    -(int) boundingBox.getUpper(),
                    (int) boundingBox.getWidth(),
                    (int) boundingBox.getHeight());

        }
    }

}
