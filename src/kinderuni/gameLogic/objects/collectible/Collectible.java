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
    private int lifeTime = -1;
    private Effect effect;
    private double dropAcceleration = 5;

    @Override
    public void checkCollision() {
        super.checkCollision();
        Collection<? extends LivingObject> targets = getTargets();
        Collection<? extends LivingObject> collided = getWorld().collidesWith(getBoundingBox(), targets);
        if(!collided.isEmpty()){
            collect(collided.iterator().next());
        }
    }

    @Override
    public void update(int time) {
        super.update(time);
        if(lifeTime--==0){
            destroy();
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

    public void setDropAcceleration(double dropAcceleration) {
        this.dropAcceleration = dropAcceleration;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
}
