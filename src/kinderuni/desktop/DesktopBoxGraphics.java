package kinderuni.desktop;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.desktop.ui.Util;
import kinderuni.ui.graphics.GraphicsObject;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopBoxGraphics extends DesktopGraphics {
    private static final int defaultSize = 50;
    private String text;
    private int[] colour;

    public DesktopBoxGraphics() {
        super(defaultSize, defaultSize);
    }

    public DesktopBoxGraphics(DoubleTupel dimensions, int[] colour) {
        this(dimensions, null, colour);
    }

    public DesktopBoxGraphics(DoubleTupel dimensions) {
        super(dimensions);
    }

    public DesktopBoxGraphics(double width, double height, String text, int[] colour) {
        this(new DoubleTupel(width, height), text, colour);
    }

    public DesktopBoxGraphics(DoubleTupel dimensions, String text, int[] colour) {
        super(dimensions);
        this.text = text;
        this.colour = colour;
    }

    public void drawTo(Graphics drawTo, DoubleTupel center){
        if(painting()) {
            Box boundingBox = new FastAccessBox(center, getDimensions());
            if (!isBlinking() || blinkShow()) {
                if(colour==null) {
                    drawTo.drawRect((int) boundingBox.getLeft(),
                            -(int) boundingBox.getUpper(),
                            (int) boundingBox.getWidth(),
                            (int) boundingBox.getHeight());
                }else{
                    drawTo.setColor(Util.toColor(colour));
                    drawTo.fillRect((int) boundingBox.getLeft(),
                            -(int) boundingBox.getUpper(),
                            (int) boundingBox.getWidth(),
                            (int) boundingBox.getHeight());
                    drawTo.setColor(Color.BLACK);
                }
                if (text != null) {
                    drawTo.drawString(text, (int) boundingBox.getLeft() + 2, -(int) boundingBox.getUpper() + 13);
                }
            }
//            System.out.println("Drawing BoxGraphics to x=" + (int) boundingBox.getLeft() + " y=" +  (int) boundingBox.getUpper() + " width=" + (int) boundingBox.getWidth() + " height=" + (int) boundingBox.getLeft());
        }
    }



    @Override
    public GraphicsObject copy() {
        return new DesktopBoxGraphics(getDimensions(), text, colour);
    }
}
