package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.PhysicsObject;
import kinderuni.gameLogic.objects.solid.SolidObject;
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

    public void collect(){
        destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
        getWorld().destroyCollectable(this);
    }

    @Override
    public void collidedWithSolid(SolidObject solidObject, Direction2D collisionSide) {
        DoubleTupel speed = getSpeed();
        super.collidedWithSolid(solidObject, collisionSide);
        accelerate(speed.mult(0.9,-0.9));
    }
}
