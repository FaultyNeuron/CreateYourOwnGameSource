package kinderuni.gameLogic.objects;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public abstract class LivingObject extends PhysicsObject{
    private int hp;
    private double walkingSpeed;
    private double flyingSpeed;
    private double jumpPower;

    public LivingObject() {
        this(1);
    }

    public LivingObject(int hp) {
        this.hp = hp;
    }

    @Override
    public void checkCollision() {
        super.checkCollision();
        if(getBoundingBox().getUpper() < getWorld().getBounds().getLower()){
            kill();
        }

        //todo move object back into world, if moved out of it
    }

    public double getJumpPower() {
        return jumpPower;
    }

    public double getFlyingSpeed() {
        return flyingSpeed;
    }

    public double getWalkingSpeed() {
        return walkingSpeed;
    }

    public void heal(int hp){
        this.hp+=hp;
    }
    public void takeDamage(int damage){
        takeDamage(damage, null);
    }
    public boolean takeDamage(int damage, LivingObject source){
        hp-=damage;
        getGraphics().blink(10, 3);

        if(hp <= 0){
            killedBy(source);
            return true;
        }
        return false;
    }


    public void kill(){
        killedBy(null);
    }

    public void killedBy(LivingObject source){
        destroy();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public void jump(){
        jump(jumpPower);
    }

    public void jump(boolean mustBeStanding) {
        jump(jumpPower, mustBeStanding);
    }

    public void jump(double jumpPower){
        jump(jumpPower, false);
    }

    public void jump(double jumpPower, boolean mustBeStanding) {
        jump(DoubleTupel.UP.mult(jumpPower), mustBeStanding);
    }

    public void jump(DoubleTupel jumpDir) {
        jump(jumpDir, false);
    }

    public void jump(DoubleTupel jumpDir, boolean mustBeStanding) {
        if (!mustBeStanding || !inAir()){
            accelerate(jumpDir);
            getGraphics().setState(GraphicsObject.State.JUMPING);
        }
    }

//    public void walk(Direction1D direction){
//        walk(direction.to2D(), walkingSpeed);
//    }

//    public void walk(Direction2D direction){
//        walk(direction, walkingSpeed);
//    }

    public void walk(HorizontalDirection direction){
        walk(direction, walkingSpeed);
    }

    public void walk(HorizontalDirection direction, double speed){
        walk(direction, speed, false);
    }

    public void walk(HorizontalDirection direction, double speed, boolean mustBeStanding){
        if(!mustBeStanding || !inAir()) {
            double friction;
            if (inAir()) {
                friction = getWorld().getAirFriction();
            } else {
                getGraphics().setState(GraphicsObject.State.WALKING);
                friction = getStickingTo().getFriction();
            }
            getGraphics().setDirection(direction);
            friction = Math.pow(friction, 0.65);
            accelerate((direction.toVector(speed).sub(getSpeed().mult(1, 0))).mult(friction));
        }
    }

    public void fly(Direction2D direction, double speed){
        fly(direction.toVector(speed));
    }

    public void fly(DoubleTupel delta){
        move(delta);
        getGraphics().setState(GraphicsObject.State.FLYING);
        getGraphics().setDirection(HorizontalDirection.toDirection(delta));
    }

    public void move(Direction2D direction, double speed){
        move(direction.toVector(speed));
    }

    public void setWalkingSpeed(double walkingSpeed) {
        this.walkingSpeed = walkingSpeed;
    }

    public void setJumpPower(double jumpPower) {
        this.jumpPower = jumpPower;
    }

    public void setFlyingSpeed(double flyingSpeed) {
        this.flyingSpeed = flyingSpeed;
    }
}
