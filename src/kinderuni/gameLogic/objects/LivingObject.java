package kinderuni.gameLogic.objects;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.InvinciblePower;
import kinderuni.gameLogic.objects.collectible.effects.ReversibleEffect;
import kinderuni.gameLogic.objects.collectible.effects.TimeBasedReverser;
import kinderuni.gameLogic.objects.solid.SolidObject;
import kinderuni.ui.graphics.GraphicsObject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public abstract class LivingObject extends PhysicsObject{
    private int hp;
    private double walkingSpeed;
    private double flyingSpeed;
    private double jumpPower;
    private int invincibleCounter = 0;
    private List<ReversibleEffect> activeEffects = new LinkedList<>();
    private ProjectileGun gun;

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

    @Override
    public void initUpdateCycle() {
        super.initUpdateCycle();
        if(!inAir()){
            getGraphics().setState(GraphicsObject.State.IDLE);
        }
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
        takeDamage(-hp);
    }

    public void takeDamage(int damage){
        takeDamage(damage, null);
    }

    public boolean takeDamage(int damage, LivingObject source){
        if(invincibleCounter==0 || damage<0){
            hp-=damage;
            if(damage>0) {
                getGraphics().blink(5);

                if (hp <= 0) {
                    killedBy(source);
                    return true;
                }
                stop();
            }
        }
        return false;
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

    @Override
    public void update(int time) {

        super.update(time);
        for(Effect effect : new LinkedList<>(activeEffects)){
            effect.update(time);
        }
        if(hasGun()){
            gun.update(time);
        }
    }

    @Override
    public void stickToBase(SolidObject base) {
        super.stickToBase(base);

    }

    public void kill(){
        killedBy(null);
    }

    public void deActivateEffects(){
        for(ReversibleEffect effect : new LinkedList<>(activeEffects)){
            effect.deActivate();
        }
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

    public void killedBy(LivingObject source){
        deActivateEffects();
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

    public void shoot(){
        if(hasGun()){
            gun.shoot(getTargets());
        }
    }

    public abstract Collection<? extends LivingObject> getTargets();

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
            setDirection(direction);
            friction = Math.pow(friction, 0.65);
            accelerate((direction.toVector(speed).sub(getSpeed().mult(1, 0))), friction);
        }
    }

    public void fly(Direction2D direction, double speed){
        fly(direction.toVector(speed));
    }

    public void fly(DoubleTupel delta){
        double friction = Math.pow(getWorld().getAirFriction(), 0.65);
        accelerate(delta, friction);
        getGraphics().setState(GraphicsObject.State.FLYING);
        setDirection(HorizontalDirection.toDirection(delta));
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

    public ProjectileGun getGun() {
        return gun;
    }

    public boolean hasGun(){
        return gun!=null;
    }

    public void setGun(ProjectileGun gun) {
        this.gun = gun;
        gun.setHolder(this);
    }

    public void removeGun(ProjectileGun givenGun) {
        if(gun==givenGun){
            gun.setHolder(null);
            gun = null;
        }
    }
}
