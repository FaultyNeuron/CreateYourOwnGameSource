package kinderuni.desktop;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
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
    public void paint(GameObject gameObject) {
        DoubleTupel screenDelta = graphicsComponent.getCenter();
        if(gameObject.hasGraphics()){
            paint(gameObject, gameObject.getCenter().sub(screenDelta));
        }
    }

    @Override
    public void paint(GameObject abstractGameObject, DoubleTupel center) {
        paint(abstractGameObject.getGraphics(), center, abstractGameObject.getDistanceFactor());
    }

    @Override
    public void paint(GraphicsObject graphics, DoubleTupel center) {
        paint(graphics, center, 1);
    }

    @Override
    public void paint(GraphicsObject graphics, DoubleTupel center, double distanceFactor) {
        DoubleTupel screenDim = graphicsComponent.getCompSize();
        DoubleTupel screenDelta = screenDim.div(2, -2);
        DesktopGraphics desktopGraphics = (DesktopGraphics) graphics;
//        DoubleTupel center = abstractGameObject.getBoundingBox().getCenter();
        DoubleTupel newCenter = center.mult(distanceFactor).add(screenDelta);
//        Box graphicsBounding = new FastAccessBox(newCenter, graphics.getDimensions());
//        Box screenArea = new FastAccessBox(screenDim.div(2), screenDim);
//        if(newCenter.getFirst()<600){
//            System.out.println("bla");
//        }
//        if(screenArea.collides(graphicsBounding)) {
            desktopGraphics.drawTo(g, newCenter);
//            g.setColor(Color.BLUE);
            //g.fillRect((int)screenArea.getLeft()+10, (int)screenArea.getLower()+10, (int)screenArea.getWidth()-20, (int)screenArea.getHeight()-20);
//        }else{
//            desktopGraphics.drawTo(g, newCenter);
//            int i = 0;
//            desktopGraphics.drawTo(g, newCenter);
//        }
    }

    @Override
    public void paintCanvas(Color color) {
        Box screenArea = getRenderScreen().getScreenArea();
        g.setColor(color);
        g.fillRect(0, 0, (int) screenArea.getWidth() + 1, (int) screenArea.getHeight() + 1);
    }

    @Override
    public GraphicsComponent getRenderScreen() {
        return graphicsComponent;
    }

}
