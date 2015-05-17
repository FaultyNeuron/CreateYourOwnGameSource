package kinderuni.android;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.AbstractGraphicsObject;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class AndroidGraphics extends AbstractGraphicsObject {
    public AndroidGraphics(double width, double height) {
        super(width, height);
    }

    public AndroidGraphics(DoubleTupel dimensions) {
        super(dimensions);
    }

    @Override
    public DoubleTupel getDimensions() {
        return null;
    }

    @Override
    public GraphicsObject copy() {
        return new AndroidGraphics(getDimensions());
    }

}
