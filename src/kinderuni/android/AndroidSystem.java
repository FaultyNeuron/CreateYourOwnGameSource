package kinderuni.android;

import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.Screen;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class AndroidSystem extends kinderuni.System {
    @Override
    public GraphicsObject createGraphics(File path) {
        return null;
    }

    @Override
    public GraphicsObject createBoxGraphics(double widht, double height) {
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
