package kinderuni.ui.graphics;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.GameObject;
import kinderuni.ui.GraphicsComponent;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public interface Painter {
    public void paint(GameObject abstractGameObject);

    void paint(GameObject abstractGameObject, DoubleTupel center);

    void paint(GraphicsObject graphics, DoubleTupel center);
    void paintCanvas(Color color);

    public GraphicsComponent getRenderScreen();
}
