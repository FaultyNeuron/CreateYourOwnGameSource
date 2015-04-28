package kinderuni.gameLogic.objects.solid;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class MovingPlatform extends Platform {
    private int counter = 0;
    private DoubleTupel delta = Direction2D.RIGHT.toVector(0.5);
    public MovingPlatform(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld);
    }

    public MovingPlatform(DoubleTupel position, GraphicsObject graphicsObject) {
        super(position, graphicsObject);
    }

    @Override
    public void update(int time) { //TODO: calculate position from time, so platforms won't go out of sync
        super.update(time);
        if(counter++==200){
            delta = delta.mult(-1);
            counter = 0;
        }
        move(delta);
    }
}
