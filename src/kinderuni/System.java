package kinderuni;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Screen;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public interface System {
    public GraphicsObject createGraphics(String id, String fileType, double width, double height);

    public GraphicsObject createGraphics(String id, String fileType, DoubleTupel dimensions);

    public GraphicsObject createBoxGraphics(double width, double height);

    public GraphicsObject createTextBoxGraphics(double width, double height, String text);

    public Screen getScreen();

    public InputRetriever getInputRetriever();
}
