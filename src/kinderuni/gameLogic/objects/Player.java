package kinderuni.gameLogic.objects;

import functionalJava.data.Direction1D;
import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.effects.InvinciblePower;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.ReversibleEffect;
import kinderuni.gameLogic.objects.collectible.effects.TimeBasedEffect;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public class Player extends LivingObject {
    private int startingHp;
    private int lifeCount;
    private double jumpPower;
    private double enemyThrowbackPower;
    private double moveSpeed;
//    private int invincibleTimer;
    private int invincibleCounter = 0;
    private SpawnPoint spawnPoint = new SpawnPoint();
    private boolean jumping = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;
    private int coins;
    private List<ReversibleEffect> activeEffects = new LinkedList<>();
    private Set<Enemy> currentColliding = new HashSet<>();
    private Set<Enemy> previousColliding = new HashSet<>();

    public Player(GraphicsObject graphicsObject, double jumpPower, double moveSpeed, double enemyThrowbackPower, int hp, int lifeCount) {
        super(DoubleTupel.ZEROS, graphicsObject, hp);
        this.jumpPower = jumpPower;
        this.moveSpeed = moveSpeed;
        this.enemyThrowbackPower = enemyThrowbackPower;
        this.lifeCount = lifeCount;
        startingHp = hp;
    }

    @Override
    public void initUpdateCycle() {
        super.initUpdateCycle();
        previousColliding = currentColliding;
        currentColliding = new HashSet<>();
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
        for(Effect effect : new LinkedList<>(activeEffects)){
            effect.update();
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
        Set<Collectible> collectibles = getWorld().collidesWith(getBoundingBox(), getWorld().getCollectiblesActive());
        for(Collectible collectible : collectibles){
            Tupel<DoubleTupel, Direction2D> collision = collideAfterMoving(getBoundingBox(), getLastDelta(), collectible);
            collect(collectible, collision.getSecond());
        }
    }

    private void collect(Collectible collectible, Direction2D second) {
        collectible.collect(this);
    }

    public void collidedWithEnemy(Enemy enemy, Direction2D collisionSide) {
        currentColliding.add(enemy);
        if(collisionSide == Direction2D.DOWN){
            if(!previousColliding.contains(enemy)) {
//                System.out.println("doing damage.. hp: "+enemy.getHp());
                stop(false);
                enemy.takeDamage(1, this);
                if(enemy.isDestroyed()){
                    accelerate(0, jumpPower * 1.1);
                }else{
                    accelerate(0, jumpPower*0.75);
                }
//                System.out.println("damage done. hp left: "+enemy.getHp());
            }
        }else{
            takeDamage(enemy.damageDealt(), enemy);
        }
    }

    @Override
    public boolean takeDamage(int damage, LivingObject source) {
        if(invincibleCounter==0){
            boolean killed = super.takeDamage(damage, source);
            stop();
            if(!killed) {
                DoubleTupel throwBack = getCenter().sub(source.getCenter()).add(0, 5);
                accelerate(throwBack.toLength(enemyThrowbackPower));
                new TimeBasedEffect(200, new InvinciblePower()).activate(this);
            }
            return killed;
        }
        return false;
    }

    @Override
    public void stickToBase(SolidObject base) {
        super.stickToBase(base);
        spawnPoint.setCenter(getCenter());
        spawnPoint.stickToBase(base);
    }

    @Override
    public void kill() {
        killedBy(null);
    }

    @Override
    public void killedBy(LivingObject source) {
        lifeCount--;
        if (lifeCount > 0) {
            stop();
            for(ReversibleEffect effect : new LinkedList<>(activeEffects)){
                effect.deActivate();
            }
            if(source==null) {
                setCenter(spawnPoint.getCenter());
            }
            new TimeBasedEffect(200, new InvinciblePower()).activate(this);
//            setInvincible(200);
            setHp(startingHp);
        } else {
            destroy();
        }
    }

    public void setInvincible(){
//        invincibleTimer = time;
        getGraphics().blink(10);
        invincibleCounter++;
    }

    public void setVincible(){
        invincibleCounter--;
        if(invincibleCounter<=0){
            getGraphics().stopBlink();
        }
//        invincibleTimer = 0;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoin() {
        coins++;
    }
    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void setSpawnPoint(DoubleTupel spawnPoint) {
        this.spawnPoint.setCenter(spawnPoint);
    }

    public void addLife(int lifeToAdd) {
        lifeCount +=lifeToAdd;
    }

    public void speedUp(double speedFactor) {
        moveSpeed*=speedFactor;
    }

    public void applyEffect(ReversibleEffect effect) {
        activeEffects.add(effect);
        System.out.println("effect became active: " + effect);
    }

    public void removeEffect(Effect effect){
        activeEffects.remove(effect);
    }

    public List<ReversibleEffect> getActiveEffects() {
        return activeEffects;
    }

    private class SpawnPoint extends AbstractGameObject {
        protected SpawnPoint() {
            super(DoubleTupel.ZEROS);
        }

        @Override
        public void update(int time) {

        }
    }
}
