package kinderuni.desktop;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.AbstractGraphicsObject;

import java.awt.*;
import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class DesktopBoxGraphics extends AbstractGraphicsObject {
    private String text;
    public DesktopBoxGraphics() {
    }

    public DesktopBoxGraphics(double width, double height) {
        super(width, height);
    }

    public DesktopBoxGraphics(DoubleTupel dimensions) {
        super(dimensions);
    }

    public DesktopBoxGraphics(double width, double height, String text) {
        super(width, height);
        this.text = text;
    }

    public DesktopBoxGraphics(DoubleTupel dimensions, String text) {
        super(dimensions);
        this.text = text;
    }

    public void drawTo(Graphics drawTo, Box boundingBox){
        if(!isBlinking() || blinkShow()) {
            drawTo.drawRect((int) boundingBox.getLeft(),
                    -(int) boundingBox.getUpper(),
                    (int) boundingBox.getWidth(),
                    (int) boundingBox.getHeight());
            if(text!=null){
                drawTo.drawString(text, (int) boundingBox.getLeft()+2, -(int) boundingBox.getUpper()+13);
            }
        }
    }

}
