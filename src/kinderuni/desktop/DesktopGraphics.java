package kinderuni.desktop;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.AbstractGraphicsObject;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopGraphics extends AbstractGraphicsObject {
    private String text;
    public DesktopGraphics() {
    }

    public DesktopGraphics(double width, double height) {
        super(width, height);
    }

    public DesktopGraphics(DoubleTupel dimensions) {
        super(dimensions);
    }

    public DesktopGraphics(double width, double height,  String text) {
        super(width, height);
        this.text = text;
    }

    public DesktopGraphics(DoubleTupel dimensions,  String text) {
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
