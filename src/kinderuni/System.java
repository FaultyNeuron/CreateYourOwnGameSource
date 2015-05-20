package kinderuni;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.Screen;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.settings.Settings;
import kinderuni.settings.levelSettings.objectSettings.*;

/**
 * Created by Georg Plaz.
 */
public interface System {
    public GraphicsObject createGraphics(String id, double width, double height);

    public GraphicsObject createGraphics(String id, DoubleTupel dimensions);

    public GraphicsObject createBoxGraphics(double width, double height, int[] bgColour, int[] lineColour);

    public GraphicsObject createTextBoxGraphics(double width, double height, String text);

    public Screen getScreen();

    public Settings getSettings();

    GraphicsObject createGraphics(GraphicsSettings graphicsSettings);
}
