package kinderuni.gameLogic.objects;

import functionalJava.data.Direction1D;
import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.Invincible;
import kinderuni.gameLogic.objects.collectible.powerUp.PowerUp;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public class Player extends LivingObject {
    private int startingHp;
    private int lifes;
    private double jumpPower;
    private double enemyThrowbackPower = 5; //todo set
    private double moveSpeed;
//    private int invincibleTimer;
    private boolean isInvincible = false;
    private DoubleTupel spawnPoint = null;
    private boolean jumping = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;
    private int coins;
    private List<PowerUp> activePowerUps = new LinkedList<>();

    public Player(DoubleTupel position, GraphicsObject graphicsObject, double jumpPower, double moveSpeed, int lifes, int hp) {
        super(position, graphicsObject, hp);
        this.jumpPower = jumpPower;
        this.moveSpeed = moveSpeed;
        this.lifes = lifes;
        startingHp = hp;
    }

    public void jump(){
        jumping = true;
    }

    private void consumeJump(){
        if(jumping){
            if(!inAir()){
                jump(jumpPower);
            }
            jumping = false;
        }
    }

    @Override
    public void setCenter(DoubleTupel position) {
        super.setCenter(position);
    }

    private void consumeRight(){
        if(goingRight && !goingLeft){
            if(!inAir()){
                walk(Direction1D.RIGHT, moveSpeed);
            }else{
                consumeMove(Direction2D.RIGHT, moveSpeed);
            }
        }
        goingRight = false;
    }

    private void consumeLeft(){
        if(!goingRight && goingLeft){
            if(!inAir()){
                walk(Direction1D.LEFT, moveSpeed);
            }else{
                consumeMove(Direction2D.LEFT, moveSpeed);
            }
        }
        goingLeft = false;
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
        if(!inAir()){
            getGraphics().setState(GraphicsObject.State.STANDING);
        }
        consumeMovement();
        super.update(time);
//        if(isInvincible){
//            invincibleTimer--;
//            if(invincibleTimer==0){
//                isInvincible = false;
//                getGraphics().stopBlink();
//            }
//        }
        for(PowerUp powerUp : new LinkedList<>(activePowerUps)){
            powerUp.update();
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
        Set<Collectible> collectibles = getWorld().collidesWith(getBoundingBox(), getWorld().getCollectiblesActive());
        for(Collectible collectible : collectibles){
            Tupel<DoubleTupel, Direction2D> collision = collideAfterMoving(getBoundingBox(), getLastDelta(), collectible);
            collect(collectible, collision.getSecond());
        }
    }

    private void collect(Collectible collectible, Direction2D second) {
        collectible.collect();
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
                new Invincible.InvinciblePower(200).activate(this);
            }
            return killed;
        }
        return false;
    }

    @Override
    public void stickToBase(SolidObject base) {
        super.stickToBase(base);
        spawnPoint = getCenter();
    }

    @Override
    public void kill() {
        killedBy(null);
    }

    @Override
    public void killedBy(LivingObject source) {
        lifes--;
        if (lifes > 0) {
            stop();
            for(PowerUp powerUp : new LinkedList<>(activePowerUps)){
                powerUp.deActivate();
            }
            if(source==null) {
                setCenter(spawnPoint);
            }
            new Invincible.InvinciblePower(200).activate(this);
//            setInvincible(200);
            setHp(startingHp);
        } else {
            destroy();
        }
    }

    public void setInvincible(){
//        invincibleTimer = time;
        getGraphics().blink(10);
        isInvincible = true;
    }

    public void setVincible(){
        isInvincible = false;
        getGraphics().stopBlink();
//        invincibleTimer = 0;
    }

    public int getLifes() {
        return lifes;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoin() {
        coins++;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setSpawnPoint(DoubleTupel spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public void addLife(int lifeToAdd) {
        lifes +=lifeToAdd;
    }

    public void speedUp(double speedFactor) {
        moveSpeed*=speedFactor;
    }

    public void addPowerUp(PowerUp powerUp) {
        activePowerUps.add(powerUp);
    }

    public void removePowerUp(PowerUp powerUp){
        activePowerUps.remove(powerUp);
    }
}
