package kinderuni.desktop;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopBoxGraphics extends DesktopGraphics {
    private static final int defaultSize = 50;
    private String text;
    public DesktopBoxGraphics() {
        super(defaultSize, defaultSize);
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

    public void drawTo(Graphics drawTo, DoubleTupel center){
        if(painting()) {
            Box boundingBox = new FastAccessBox(center, getDimensions());
            if (!isBlinking() || blinkShow()) {
                drawTo.drawRect((int) boundingBox.getLeft(),
                        -(int) boundingBox.getUpper(),
                        (int) boundingBox.getWidth(),
                        (int) boundingBox.getHeight());
                if (text != null) {
                    drawTo.drawString(text, (int) boundingBox.getLeft() + 2, -(int) boundingBox.getUpper() + 13);
                }
            }
        }
    }

    @Override
    public GraphicsObject copy() {
        return new DesktopBoxGraphics(getDimensions(), text);
    }
}
