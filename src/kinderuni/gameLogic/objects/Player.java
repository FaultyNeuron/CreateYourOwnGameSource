package kinderuni.gameLogic.objects;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.effects.InvinciblePower;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.PlusHp;
import kinderuni.gameLogic.objects.collectible.effects.TimeBasedReverser;
import kinderuni.level.builder.ProjectileBuilder;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.*;

/**
 * Created by Georg Plaz.
 */
public class Player extends LivingObject {
    private int startingHp;
    private int lifeCount;
    private double enemyThrowbackPower;
//    private int invincibleTimer;
    private SpawnPoint spawnPoint = new SpawnPoint();
    private boolean jumping = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;
    private int coins;
    private Set<Enemy> currentColliding = new HashSet<>();
    private Set<Enemy> previousColliding = new HashSet<>();
    private boolean hpSet = false;

    public Player(double enemyThrowbackPower, int lifeCount) {
        this.enemyThrowbackPower = enemyThrowbackPower;
        this.lifeCount = lifeCount;
    }

    @Override
    public void setHp(int hp) {
        if(!hpSet){
            startingHp = hp;
            hpSet = true;
        }
        super.setHp(hp);
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

    @Override
    public Collection<? extends LivingObject> getTargets() {
        return getWorld().getEnemiesActive();
    }

    private void consumeJump(){
        if(jumping){
            super.jump(true);
            jumping = false;
        }
    }

    private void consumeRight(){
        if(goingRight && !goingLeft){
            walk(HorizontalDirection.RIGHT);
        }
        goingRight = false;
    }

    private void consumeLeft(){
        if(!goingRight && goingLeft){
            walk(HorizontalDirection.LEFT);
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
        shoot();
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
                System.out.println("jumped on "+enemy);

//                System.out.println("doing damage.. hp: "+enemy.getHp());
                stop(false);
                enemy.takeDamage(1, this);
                if(enemy.isDestroyed()){
                    jump(getJumpPower() * 1.1); //todo to constants
                }else{
                    jump(getJumpPower() * 0.75);
                }
//                System.out.println("damage done. hp left: "+enemy.getHp());
            }
        }else{
            takeDamage(enemy.damageDealt(), enemy);
        }
    }

    @Override
    public boolean takeDamage(int damage, LivingObject source) {
        boolean killed = super.takeDamage(damage, source);
        if(damage>0){
            if(!killed) {
                if(source!=null) {
                    DoubleTupel throwBack = getCenter().sub(source.getCenter()).add(0, 5);
                    accelerate(throwBack.toLength(enemyThrowbackPower));
                }
                new InvinciblePower(new TimeBasedReverser(200)).activate(this);
            }
        }
        return killed;
    }

    @Override
    public void stickToBase(SolidObject base) {
        super.stickToBase(base);
        setSpawnPoint(getCenter());
        spawnPoint.stickToBase(base);
    }

    @Override
    public void kill() {
        killedBy(null);
    }

    @Override
    public void killedBy(LivingObject source) {
        lifeCount--;
        deActivateEffects();
        if (lifeCount > 0) {
            stop();
            if(source==null) {
                setCenter(spawnPoint.getCenter());
            }
            new InvinciblePower(new TimeBasedReverser(200)).activate(this);
            setHp(startingHp);
        } else {
            destroy();
        }
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

    private class SpawnPoint extends AbstractGameObject {
        @Override
        public void update(int time) {}
    }

    private class PlayerProjectile extends Projectile{
        protected PlayerProjectile(Effect effect) {
            super(effect);
        }

        @Override
        public Collection<? extends LivingObject> getTargets() {
            return getWorld().getEnemiesActive();
        }
    }
}
