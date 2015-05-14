package kinderuni.gameLogic.objects;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public class Player extends LivingObject {
    private int startingHp;
    private int lives;
    private double jumpPower;
    private double enemyThrowbackPower = 5; //todo set
    private double moveSpeed;
    private int invincibleTimer;
    private boolean isInvincible = false;
    private DoubleTupel lastCenterOnSolid = null;
    private boolean jumping = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;

    public Player(DoubleTupel position, GraphicsObject graphicsObject, double jumpPower, double moveSpeed, int lives, int hp) {
        super(position, graphicsObject, hp);
        this.jumpPower = jumpPower;
        this.moveSpeed = moveSpeed;
        this.lives = lives;
        startingHp = hp;
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

    @Override
    public void setCenter(DoubleTupel position) {
        super.setCenter(position);
        if(lastCenterOnSolid==null){
            lastCenterOnSolid = position;
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
        double friction;
        if (inAir()) {
            friction = getWorld().getAirFriction();
        } else {
            friction = getStickingTo().getFriction();
        }
        friction = Math.pow(friction, 0.85);
        Direction2D positiveDirection = direction.toAxis().toDirection(true);
        accelerate((direction.toVector(moveSpeed).sub(getSpeed().mult(positiveDirection.toVector()))).mult(friction));
//        accelerate(direction.toVector(moveSpeed * friction));
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
//        System.out.println("in air: "+inAir());
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
            stop();
            if(!killed) {
                DoubleTupel throwBack = getCenter().sub(source.getCenter()).add(0, 5);
                accelerate(throwBack.toLength(enemyThrowbackPower));
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
        killedBy(null);
    }

    @Override
    public void killedBy(LivingObject source) {
        lives--;
        stop();
        if (lives > 0) {
            if(source==null) {
                setCenter(lastCenterOnSolid);
            }
            setInvincible(200);
            setHp(startingHp);
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
