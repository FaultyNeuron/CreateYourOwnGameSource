package kinderuni.gameLogic.objects;

import functionalJava.data.Direction1D;
import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public abstract class LivingObject extends PhysicsObject{
    private int hp;

    protected LivingObject(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        this(position, graphicsObject, gameWorld, 1);
    }

    protected LivingObject(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld, int hp) {
        super(position, graphicsObject, gameWorld);
        this.hp = hp;
    }

    public LivingObject(DoubleTupel position, GraphicsObject graphicsObject) {
        this(position, graphicsObject, 1);
    }

    public LivingObject(DoubleTupel position, GraphicsObject graphicsObject, int hp) {
        super(position, graphicsObject);
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

    public void walk(Direction1D direction, double speed){
        if(!inAir()) {
            consumeMove(direction.to2D(), speed);
            getGraphics().setState(GraphicsObject.State.WALKING);
        }
    }

    public void jump(double jumpPower){
        if(!inAir()){
            consumeMove(Direction2D.UP, jumpPower);
            getGraphics().setState(GraphicsObject.State.JUMPING);
        }
    }

    public void consumeMove(Direction2D direction, double speed){
        double friction;
        if(direction.isHorizontal()){
            if (inAir()) {
                friction = getWorld().getAirFriction();
            } else {
                friction = getStickingTo().getFriction();
            }
        }else{
            friction = 1;
        }
        if(direction.isHorizontal()){
            getGraphics().setDirection(direction.toDirection1D());
        }
        friction = Math.pow(friction, 0.65);
        Direction2D positiveDirection = direction.toAxis().toDirection(true);
        accelerate((direction.toVector(speed).sub(getSpeed().mult(positiveDirection.toVector()))).mult(friction));
//        accelerate(direction.toVector(moveSpeed * friction));
    }
}
