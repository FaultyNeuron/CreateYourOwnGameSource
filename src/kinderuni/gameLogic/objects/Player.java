package kinderuni.gameLogic.objects;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public class Player extends LivingObject {
    public static final int STARTING_HP = 3;

    private int lives = 3;
    private double jumpPower = 7; //todo set
    private double enemyJumpPower = 1; //todo set
    private double enemythrobackPower = 5; //todo set
    private double moveSpeed = 2; //todo set
    private int invincibleTimer;
    private boolean isInvincible = false;
    private DoubleTupel lastCenterOnSolid = null;
    private boolean jumping = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;

    public Player(DoubleTupel position, GraphicsObject graphicsObject) {
        super(position, graphicsObject, STARTING_HP);
        lastCenterOnSolid = position;
    }

    public Player(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld, STARTING_HP);
        lastCenterOnSolid = position;
    }

    public void jump(){
        jumping = true;
    }

    private void consumeJump(){
        if(jumping){
            if(!inAir()){
                accelerate(0, jumpPower);
            }
            jumping = false;
        }
    }

    private void consumeRight(){
        if(goingRight && !goingLeft){
            consumeMove(Direction2D.RIGHT);
        }
        goingRight = false;
    }

    private void consumeLeft(){
        if(!goingRight && goingLeft){
            consumeMove(Direction2D.LEFT);
        }
        goingLeft = false;
    }

    private void consumeMove(Direction2D direction){
        if (!inAir()) {
            accelerate(direction.toVector(moveSpeed * getStickingTo().getFriction()));
        } else {
            accelerate(direction.toVector(moveSpeed * getWorld().getAirFriction()));
        }
    }

    private void consumeMovement(){
        consumeRight();
        consumeLeft();
        consumeJump();
    }

    public void moveRight() {
        goingRight = true;
    }

    public void moveLeft(){
        goingLeft = true;
    }

    @Override
    public void update(int time) {
        consumeMovement();
        super.update(time);
        if(isInvincible){
            invincibleTimer--;
            if(invincibleTimer==0){
                isInvincible = false;
                getGraphics().stopBlink();
            }
        }
    }

    @Override
    public void checkCollision() {
        super.checkCollision();
        Set<Enemy> enemies = getWorld().collidesWith(getBoundingBox(), getWorld().getEnemiesActive());
        for(Enemy enemy : enemies){
            Tupel<DoubleTupel, Direction2D> collision = collideAfterMoving(getBoundingBox(), getLastDelta(), enemy);
            collidedWithEnemy(enemy, collision.getSecond());
        }
    }

    public void collidedWithEnemy(Enemy enemy, Direction2D collisionSide) {
        if(collisionSide == Direction2D.DOWN){
            stop(false);
            accelerate(0, 7);
            enemy.takeDamage(1, this);
        }else{
            takeDamage(enemy.damageDealt(), enemy);
        }
    }

    @Override
    public boolean takeDamage(int damage, LivingObject source) {
        if(!isInvincible){
            boolean killed = super.takeDamage(damage, source);
            if(!killed) {
                stop();
                DoubleTupel throwBack = getCenter().sub(source.getCenter()).add(0, 5);
                accelerate(throwBack.toLength(enemythrobackPower));
                setInvincible(200);
            }
            return killed;
        }
        return false;
    }

    @Override
    public void stickToBase(SolidObject base) {
        super.stickToBase(base);
        lastCenterOnSolid = getCenter();
    }

    @Override
    public void kill() {
        kill(null);
    }

    @Override
    public void kill(LivingObject source) {
        lives--;
        if (lives > 0) {
            if(source==null) {
                setCenter(lastCenterOnSolid);
            }
            setInvincible(200);
            setHp(STARTING_HP);
        } else {
            destroy();
        }
    }

    public void setInvincible(int time){
        invincibleTimer = time;
        getGraphics().blink(10);
        isInvincible = true;
    }

    public int getLives() {
        return lives;
    }
}
