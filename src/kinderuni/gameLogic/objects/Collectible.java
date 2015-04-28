package kinderuni.gameLogic.objects;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class Collectible extends PhysicsObject{
    public Collectible(GraphicsObject graphicsObject, DoubleTupel position, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld);
    }

    public Collectible(GraphicsObject graphicsObject, DoubleTupel position) {
        super(position, graphicsObject);
    }
}
