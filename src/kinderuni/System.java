package kinderuni;

import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.Screen;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public abstract class System {
    public abstract GraphicsObject createGraphics(File path);

    public abstract GraphicsObject createBoxGraphics(double widht, double height);

    public abstract GraphicsObject createTextBoxGraphics(double width, double height, String text);

    public abstract Screen createScreen();
}
