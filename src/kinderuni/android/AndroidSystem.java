package kinderuni.android;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.*;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Screen;
import kinderuni.settings.Settings;
import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class AndroidSystem implements kinderuni.System {

    @Override
    public GraphicsObject createGraphics(String id, double width, double height) {
        return null;
    }

    @Override
    public GraphicsObject createGraphics(String id, DoubleTupel dimensions) {
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
    public Screen getScreen() {
        return null;
    }

    @Override
    public InputRetriever getInputRetriever() {
        return null;
    }

    @Override
    public Settings getSettings() {
        return null;
    }

    @Override
    public GraphicsObject createGraphics(GraphicsSettings graphicsSettings) {
        return null;
    }

}
