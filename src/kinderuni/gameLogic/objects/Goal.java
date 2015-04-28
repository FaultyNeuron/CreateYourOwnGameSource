package kinderuni.gameLogic.objects;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class Goal extends AbstractGameObject {
    protected Goal(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld);
        graphicsObject.blink(10, 0.8);
        gameWorld.set(this);
    }

    public Goal(DoubleTupel position, GraphicsObject graphicsObject) {
        super(position, graphicsObject);
        graphicsObject.blink(10, 0.8);
    }
}
