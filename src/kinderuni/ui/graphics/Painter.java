package kinderuni.ui.graphics;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.GameObject;
import kinderuni.ui.GraphicsComponent;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public interface Painter {
    public void paint(GameObject gameObject);
    public void paint(GameObject gameObject, DoubleTupel center);

    public void paint(GraphicsObject graphics, DoubleTupel center);
    public void paint(GraphicsObject graphics, DoubleTupel center, double distanceFactor);

    public void paintCanvas(Color color);

    public GraphicsComponent getRenderScreen();
}
