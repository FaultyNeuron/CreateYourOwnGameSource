package kinderuni.gameLogic.objects;

import functionalJava.data.Direction2D;
import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.Axis;
import functionalJava.data.tupel.SymTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public abstract class PhysicsObject extends AbstractGameObject {
    private DoubleTupel speed = DoubleTupel.ZEROS;
    private double bounciness = 0;
    private double gravityFactor = 1;
    private double frictionCoefficient = 1;
    private float physicsMotionFactor = 1;
    private int physicsCoolDownCounter = 0;

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
        super.update(time);
//        if(physicsCoolDownCounter--<=0) {
        accelerate(0, -getWorld().getGravity() * gravityFactor*physicsMotionFactor);
        move(speed.mult(physicsMotionFactor));
        double friction = Math.pow(1 - (getWorld().getAirFriction() * getFrictionCoefficient()), physicsMotionFactor);
        setSpeed(getSpeed().mult(friction));
//            physicsCoolDownCounter = Math.round(1/ physicsMotionFactor)-1;
//        }
    }

    public void setPhysicsMotionFactor(double physicsMotionFactor) {
        this.physicsMotionFactor = (float) physicsMotionFactor;
    }

    public float getPhysicsMotionFactor() {
        return physicsMotionFactor;
    }

    public void accelerate(double x, double y){
        speed = speed.add(x, y);
    }

    public void accelerate(DoubleTupel delta, double friction){
        speed = speed.add(delta.mult(friction));
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
        double airFriction = getWorld().getAirFriction()*getFrictionCoefficient();
        if(isSticking()){
            double floorFriction = getStickingTo().getFriction()*getFrictionCoefficient() + airFriction;
            speed = speed.sub(speed.mult(floorFriction, airFriction));
        }else{
            speed = speed.sub(speed.mult(airFriction));
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
        DoubleTupel oldSpeed = getSpeed();
        if(collisionSide.getSign() * speed.get(collisionSide.toAxis()) > 0) {
            speed = speed.mult(collisionSide.toAxis(), 0);
        }
        if(collisionSide == Direction2D.DOWN){
            stickToBase(solidObject);
//            inAir = false;
        }
        if(isBouncy()) {
            DoubleTupel bouncyVec = DoubleTupel.ONES.mult(collisionSide.toAxis(), -bounciness);
            speed = oldSpeed.mult(bouncyVec);
        }

    }

    public boolean isBouncy(){
        return bounciness>0;
    }

    public boolean inAir() {
        return !isSticking();
    }

    public double getGravityFactor(){return gravityFactor;}

    public void setGravityFactor(Double gravityFactor) {
        this.gravityFactor = gravityFactor;
    }

    public double getBounciness() {
        return bounciness;
    }

    public void setBounciness(double bounciness) {
        this.bounciness = bounciness;
    }

    public void setFrictionCoefficient(double frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    public double getFrictionCoefficient() {
        return frictionCoefficient;
    }
}
