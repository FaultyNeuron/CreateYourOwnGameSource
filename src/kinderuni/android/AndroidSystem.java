package kinderuni.android;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.Screen;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class AndroidSystem extends kinderuni.System {

    @Override
    public GraphicsObject createGraphics(File path, double width, double height) {
        return null;
    }

    @Override
    public GraphicsObject createGraphics(File path, DoubleTupel dimensions) {
        return null;
    }

    @Override
    public GraphicsObject createBoxGraphics(double width, double height) {
        return null;
    }

    @Override
    public GraphicsObject createTextBoxGraphics(double width, double height, String text) {
        return null;
    }

    @Override
    public Screen createScreen() {
        return null;
    }

}
