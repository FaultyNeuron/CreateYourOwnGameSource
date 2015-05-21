package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.PhysicsObject;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.solid.SolidObject;
import kinderuni.ui.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class Collectible extends PhysicsObject {
    private Effect effect;
    private double dropAcceleration;
    public Collectible(Effect effect, double dropAcceleration) {
        this.dropAcceleration = dropAcceleration;
        if(effect != null) {
            this.effect = effect;
        }
    }

    public Collectible(double dropAcceleration) {
        this.dropAcceleration = dropAcceleration;
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

//    @Override
//    public void collidedWithSolid(SolidObject solidObject, Direction2D collisionSide) {
//        DoubleTupel speed = getSpeed();
//        super.collidedWithSolid(solidObject, collisionSide);
//        accelerate(speed.mult(bounciness, -bounciness));
//    }

    public Collectible copy(){
        if(effect!=null){
            return new Collectible(effect.copy(), dropAcceleration);
        }else{
            return new Collectible(dropAcceleration);
        }
        //todo copy other info
    }

    public Effect getEffect() {
        return effect;
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

    @Override
    public void setGraphics(GraphicsObject graphics) {
        super.setGraphics(graphics);
        effect.setGraphics(graphics);
    }
}
