package kinderuni.gameLogic.objects;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
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


    public void takeDamage(int damage){
        takeDamage(damage, null);
    }
    public boolean takeDamage(int damage, LivingObject source){
        hp-=damage;
        if(hp<=0){
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
}
