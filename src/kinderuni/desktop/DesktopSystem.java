package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Screen;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class DesktopSystem implements kinderuni.System {
    private File resources = new File("resources");
    private File animations = new File(resources, "animations");
    private GameFrame gameFrame;

    public DesktopSystem(){
        gameFrame = new GameFrame(new DoubleTupel(500));
    }

    @Override
    public GraphicsObject createGraphics(String id, String fileType, double width, double height) {
        return createGraphics(id, fileType, new DoubleTupel(width, height));
    }

    @Override
    public GraphicsObject createGraphics(String id, String fileType, DoubleTupel dimensions) {
        return new DesktopFileGraphics(new File(animations, id), fileType, dimensions);
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
        return gameFrame.getGamePanel();
    }
}
