package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.PhysicsObject;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.solid.SolidObject;
import kinderuni.ui.graphics.GraphicsObject;

import java.util.Collection;
import java.util.Collections;

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

    @Override
    public void checkCollision() {
        super.checkCollision();
        Collection<? extends LivingObject> targets = getTargets();
        Collection<? extends LivingObject> collided = getWorld().collidesWith(getBoundingBox(), targets);
        if(!collided.isEmpty()){
            collect(collided.iterator().next());
        }
    }

    public void collect(LivingObject collector){
        effect.activate(collector);
        destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
        getWorld().destroyCollectable(this);
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

    public Collection<? extends LivingObject> getTargets() {
        return Collections.singleton(getWorld().getPlayer());
    }
}
