package kinderuni.gameLogic.objects.solid;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class Platform extends SolidObject{
    private double friction;

    public Platform(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld, double friction) {
        super(position, graphicsObject, gameWorld);
        this.friction = friction;
    }

    public Platform(DoubleTupel position, GraphicsObject graphicsObject, double friction) {
        super(position, graphicsObject);
        this.friction = friction;
    }

    @Override
    public void update(int time) {
        super.update(time);
    }

    @Override
    public double getFriction() {
        return friction;
    }
}
