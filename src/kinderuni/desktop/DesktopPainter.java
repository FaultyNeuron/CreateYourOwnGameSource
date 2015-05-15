package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.Painter;
import kinderuni.graphics.Screen;
import kinderuni.gameLogic.objects.GameObject;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopPainter implements Painter{
    private final Graphics g;
    private Screen screen;

    public DesktopPainter(Graphics g, Screen screen) {
        this.g = g;
        this.screen = screen;
    }

    @Override
    public void paint(GameObject abstractGameObject) {
        DesktopGraphics graphics = (DesktopGraphics) abstractGameObject.getGraphics();
//        DoubleTupel center = abstractGameObject.getBoundingBox().getCenter();
        DoubleTupel screenDelta = screen.getScreenDimensions().div(2, -2).sub(screen.getCenter());
        graphics.drawTo(g, abstractGameObject.getCenter().add(screenDelta));
    }

    @Override
    public Screen getRenderScreen() {
        return screen;
    }

}
