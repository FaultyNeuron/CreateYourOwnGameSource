package kinderuni.gameLogic.objects.solid;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class MovingPlatform extends Platform {
//    private int counter = 0;
//    private DoubleTupel frameDelta = Direction2D.RIGHT.toVector(0.5);
    private double speed;
    private DoubleTupel startingPosition;
    private DoubleTupel delta;
    private double cycleLength;
    public MovingPlatform(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld, double friction, double speed, DoubleTupel delta) {
        super(position, graphicsObject, gameWorld, friction);
        startingPosition = position;
        this.speed = speed;
        this.delta = delta;
        cycleLength = delta.length() / speed;
    }

    public MovingPlatform(DoubleTupel position, GraphicsObject graphicsObject, double friction, double speed, DoubleTupel delta) {
        super(position, graphicsObject, friction);
        startingPosition = position;
        this.speed = speed;
        this.delta = delta;
        cycleLength = delta.length() / speed;
    }

    @Override
    public void update(int time) { //TODO: calculate position from time, so platforms won't go out of sync
        if(speed*cycleLength>0) {
            int cycles = (int) (time / cycleLength);
            double pos = (time % cycleLength)/cycleLength;
            if(cycles%2==1){
                goToPosition(1 - pos);
            }else{
                goToPosition(pos);
            }
        }

    }

    private void goToPosition(double newPos){
        moveTo(startingPosition.add(delta.mult(newPos)));
    }
}
