package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.PhysicsObject;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.solid.SolidObject;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class Collectible extends PhysicsObject {
    private Effect effect;
    private double gravityFactor;
    private double dropAcceleration;
    private double bounciness;
    public Collectible(GraphicsObject graphicsObject, DoubleTupel position, Effect effect,
                       double gravityFactor, double dropAcceleration, double bounciness) {
        super(position, graphicsObject);
        this.gravityFactor = gravityFactor;
        this.dropAcceleration = dropAcceleration;
        this.bounciness = bounciness;
        if(effect != null) {
            this.effect = effect;
            effect.setGraphics(graphicsObject);
        }
    }

    public Collectible(GraphicsObject graphicsObject, DoubleTupel position,
                       double gravityFactor, double dropAcceleration, double bounciness) {
        super(position, graphicsObject);
        this.gravityFactor = gravityFactor;
        this.dropAcceleration = dropAcceleration;
        this.bounciness = bounciness;
    }

    public void collect(Player player){
        effect.activate(player);
        destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
        getWorld().destroyCollectable(this);
    }

    @Override
    public void collidedWithSolid(SolidObject solidObject, Direction2D collisionSide) {
        DoubleTupel speed = getSpeed();
        super.collidedWithSolid(solidObject, collisionSide);
        accelerate(speed.mult(bounciness, -bounciness));
    }

    public Collectible copy(){
        if(effect!=null){
            return new Collectible(getGraphics(), getCenter(), effect.copy(), gravityFactor, dropAcceleration, bounciness);
        }else{
            return new Collectible(getGraphics(), getCenter(), gravityFactor, dropAcceleration, bounciness);
        }
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public double getGravityFactor() {
        return gravityFactor;
    }

    public double getDropAcceleration() {
        return dropAcceleration;
    }

    @Override
    public String toString() {
        return "Collectible{" +
                "effect=" + effect +
                '}';
    }
}
