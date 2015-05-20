package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.ui.graphics.Painter;
import kinderuni.ui.GraphicsComponent;
import kinderuni.gameLogic.objects.GameObject;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopPainter implements Painter{
    private final Graphics g;
    private GraphicsComponent graphicsComponent;

    public DesktopPainter(Graphics g, GraphicsComponent graphicsComponent) {
        this.g = g;
        this.graphicsComponent = graphicsComponent;
    }

    @Override
    public void paint(GameObject abstractGameObject) {
        DoubleTupel screenDelta = graphicsComponent.getCenter();
        paint(abstractGameObject, abstractGameObject.getCenter().sub(screenDelta));
    }

    @Override
    public void paint(GameObject abstractGameObject, DoubleTupel center) {
        paint(abstractGameObject.getGraphics(), center);
    }

    @Override
    public void paint(GraphicsObject graphics, DoubleTupel center) {
        DoubleTupel screenDelta = graphicsComponent.getCompSize().div(2, -2);
        DesktopGraphics desktopGraphics = (DesktopGraphics) graphics;
//        DoubleTupel center = abstractGameObject.getBoundingBox().getCenter();
        desktopGraphics.drawTo(g, center.add(screenDelta));
    }

    @Override
    public GraphicsComponent getRenderScreen() {
        return graphicsComponent;
    }

}
