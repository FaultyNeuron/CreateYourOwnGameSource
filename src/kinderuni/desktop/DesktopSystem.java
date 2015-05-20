package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.Screen;
import kinderuni.desktop.ui.DesktopScreen;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.settings.Settings;
import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Georg Plaz.
 */
public class DesktopSystem implements kinderuni.System {
    private File resources = new File("resources");
    private Settings settings;
    private File animations = new File(resources, "animations");
    private DesktopScreen desktopScreen;

    public DesktopSystem() throws FileNotFoundException {
        desktopScreen = new DesktopScreen(new DoubleTupel(500), this);
        settings = Settings.read(resources);
    }

    @Override
    public GraphicsObject createGraphics(String id, double width, double height) {
        return createGraphics(id, new DoubleTupel(width, height));
    }

    @Override
    public GraphicsObject createGraphics(String id, DoubleTupel dimensions) {
        return new DesktopFileGraphics(new File(animations, id), dimensions);
    }

    @Override
    public GraphicsObject createBoxGraphics(double width, double height, int[] bgColour, int[] lineColour) {
        return new DesktopFileGraphics(bgColour, lineColour, new DoubleTupel(width, height));
    }

    @Override
    public GraphicsObject createTextBoxGraphics(double width, double height, String text) {
        return new DesktopFileGraphics(text, new DoubleTupel(width, height));
    }

    @Override
    public Screen getScreen() {
        return desktopScreen;
    }


    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public GraphicsObject createGraphics(GraphicsSettings graphicsSettings) {
        File path = graphicsSettings.getId()==null?null:new File(animations, graphicsSettings.getId());
        return new DesktopFileGraphics(
                path,
                graphicsSettings.getBgColour(), graphicsSettings.getLine_colour(),
                graphicsSettings.getText(),
                new DoubleTupel(graphicsSettings.getWidth(), graphicsSettings.getHeight()));
//        if(graphicsSettings.drawBox()){
//            return createBoxGraphics(graphicsSettings.getWidth(), graphicsSettings.getHeight(), graphicsSettings.getBgColour());
//        }else{
//            return createGraphics(graphicsSettings.getId(), graphicsSettings.getWidth(), graphicsSettings.getHeight());
//        }
    }
}
