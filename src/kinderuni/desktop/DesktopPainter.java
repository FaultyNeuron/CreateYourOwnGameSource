package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
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
        DoubleTupel screenDelta = screen.getCenter();
        paint(abstractGameObject, abstractGameObject.getCenter().sub(screenDelta));
    }

    @Override
    public void paint(GameObject abstractGameObject, DoubleTupel center) {
        paint(abstractGameObject.getGraphics(), center);
    }

    @Override
    public void paint(GraphicsObject graphics, DoubleTupel center) {
        DoubleTupel screenDelta = screen.getScreenDimensions().div(2, -2);
        DesktopGraphics desktopGraphics = (DesktopGraphics) graphics;
//        DoubleTupel center = abstractGameObject.getBoundingBox().getCenter();
        desktopGraphics.drawTo(g, center.add(screenDelta));
    }

    @Override
    public Screen getRenderScreen() {
        return screen;
    }

}
