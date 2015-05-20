package kinderuni.gameLogic.objects;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class Goal extends AbstractGameObject {
    public Goal(DoubleTupel position, GraphicsObject graphicsObject) {
        super(position, graphicsObject);
        graphicsObject.blink(10, 0.8);
    }

    @Override
    public void update(int time) {

    }
}
