package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.PhysicsObject;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public abstract class Collectible extends PhysicsObject {
    public Collectible(GraphicsObject graphicsObject, DoubleTupel position, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld);
    }

    public Collectible(GraphicsObject graphicsObject, DoubleTupel position) {
        super(position, graphicsObject);
    }

    public abstract void collect();
}
