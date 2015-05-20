package kinderuni.gameLogic.objects;

import functionalJava.data.Direction2D;
import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.Axis;
import functionalJava.data.tupel.SymTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public abstract class PhysicsObject extends AbstractGameObject {
    private DoubleTupel speed = DoubleTupel.ZEROS;
//    private boolean inAir = true;
//    private Direction2D movingDirection;
//    private DoubleTupel movingSpeed;

    public PhysicsObject(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld);
    }

    public PhysicsObject(DoubleTupel position, GraphicsObject graphicsObject) {
        super(position, graphicsObject);
    }

    public DoubleTupel getSpeed() {
        return speed;
    }

    public void stop(){
        speed=DoubleTupel.ZEROS;
    }

    public void stop(boolean horizontal){
        if(horizontal){
            speed = new DoubleTupel(0, speed.getSecond());
        }else{
            speed = new DoubleTupel(speed.getFirst(), 0);
        }
    }

    @Override
    public void update(int time) {
        accelerate(0, -getWorld().getGravity()*getGravityFactor());
        move(speed);
    }

    public void accelerate(double x, double y){
        speed = speed.add(x, y);
    }

    public void accelerate(DoubleTupel delta){
        speed = speed.add(delta);
    }

    public void setSpeed(DoubleTupel newSpeed) {
        speed = newSpeed;
    }

    public void setSpeed(double moveSpeed, double second) {
        setSpeed(new DoubleTupel(moveSpeed, second));
    }

    public void checkCollision(){
        Box newPosition = getBoundingBox();
        Set<SolidObject> colliding =
                getWorld().collidesWith(newPosition, getWorld().getSolidObjectsActive());
        if (!colliding.isEmpty()) {
            SymTupel<Tupel<SolidObject, Double>> closestCollidingXY =
                    firstCollideAfterMoving(getBoundingBox(), getLastDelta(), colliding);
            Axis axis = Axis.HORIZONTAL;
            DoubleTupel delta = DoubleTupel.ZEROS;
            for(Tupel<SolidObject, Double> tupel : closestCollidingXY){
                if(tupel!=null){
                    delta = delta.add(axis, tupel.getSecond());
                    Direction2D side = Direction2D.getDirection(axis, tupel.getSecond() <= 0);
                    collidedWithSolid(tupel.getFirst(), side);
                }
                axis = Axis.VERTICAL;
            }
            if(isSticking()) {
                if (!colliding.contains(getStickingTo())) {
                    unStick();
                }
            }
            super.move(delta);
        }else{
            unStick();
        }
        if(isSticking()){
//            speed = speed.div((1+getStickingTo().getFriction())*(1+getWorld().getAirFriction()), (1+getWorld().getAirFriction()));
            speed = speed.sub(speed.mult(getStickingTo().getFriction() + getWorld().getAirFriction(), getWorld().getAirFriction()));
        }else{
//            speed = speed.div(1+getWorld().getAirFriction());
            speed = speed.sub(speed.mult(getWorld().getAirFriction()));
        }
    }

    @Override
    public void unStick() {
        if(isSticking()){
            accelerate(getStickingTo().getLastDelta());
        }
        super.unStick();
//        inAir = true;
    }

    public void collidedWithSolid(SolidObject solidObject, Direction2D collisionSide){
        if(collisionSide.getSign() * speed.get(collisionSide.toAxis()) > 0) {
            speed = speed.mult(collisionSide.toAxis(), 0);
        }
        if(collisionSide == Direction2D.DOWN){
            stickToBase(solidObject);
//            inAir = false;
        }
    }

    public boolean inAir() {
        return !isSticking();
    }

    public double getGravityFactor(){return 1;}
}
