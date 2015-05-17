package kinderuni;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Screen;
import kinderuni.settings.Settings;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.levelSettings.objectSettings.*;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public interface System {
    public GraphicsObject createGraphics(String id, double width, double height);

    public GraphicsObject createGraphics(String id, DoubleTupel dimensions);

    public GraphicsObject createBoxGraphics(double width, double height);

    public GraphicsObject createTextBoxGraphics(double width, double height, String text);

    public Screen getScreen();

    public InputRetriever getInputRetriever();

    public Settings getSettings();

    GraphicsObject createGraphics(GraphicsSettings graphicsSettings);
}
