package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Screen;
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
    private GameFrame gameFrame;

    public DesktopSystem() throws FileNotFoundException {
        gameFrame = new GameFrame(new DoubleTupel(500));
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
    public GraphicsObject createBoxGraphics(double width, double height) {
        return new DesktopBoxGraphics(width, height);
    }

    @Override
    public GraphicsObject createTextBoxGraphics(double width, double height, String text) {
        return new DesktopBoxGraphics(width, height, text);
    }


    @Override
    public Screen getScreen() {
        return gameFrame.getGamePanel();
    }

    @Override
    public InputRetriever getInputRetriever(){
        return new DesktopInputRetriever(gameFrame.getGamePanel());
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public GraphicsObject createGraphics(GraphicsSettings graphicsSettings) {
        if(graphicsSettings.drawBox()){
            return createBoxGraphics(graphicsSettings.getWidth(), graphicsSettings.getHeight());
        }else{
            return createGraphics(graphicsSettings.getId(), graphicsSettings.getWidth(), graphicsSettings.getHeight());
        }
    }
}
