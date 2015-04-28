package kinderuni.gameLogic.objects.solid;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class Platform extends SolidObject{
    public Platform(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld);
    }

    public Platform(DoubleTupel position, GraphicsObject graphicsObject) {
        super(position, graphicsObject);
    }

    @Override
    public void update(int time) {
        super.update(time);
    }

    @Override
    public double getFriction() {
        return 0.3;
    }
}
