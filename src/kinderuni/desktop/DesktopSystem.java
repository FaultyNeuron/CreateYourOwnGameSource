package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.Screen;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class DesktopSystem extends kinderuni.System {
    @Override
    public GraphicsObject createGraphics(File path) {
        return new DesktopGraphics();
    }

    @Override
    public GraphicsObject createBoxGraphics(double width, double height) {
        return new DesktopGraphics(width, height);
    }
    @Override
    public GraphicsObject createTextBoxGraphics(double width, double height, String text) {
        return new DesktopGraphics(width, height, text);
    }


    @Override
    public Screen createScreen() {
        GameFrame gameFrame = new GameFrame(new DoubleTupel(500));
        return gameFrame.getGamePanel();
    }

}
