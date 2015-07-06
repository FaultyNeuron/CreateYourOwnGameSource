package kinderuni.gameLogic.objects;

import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.effects.Effect;

import java.util.Collection;

/**
 * Created by Georg Plaz.
 */
public abstract class Projectile extends Collectible {
    private int notInAirCounter;

    @Override
    public void update(int time) {
        if(!inAir()){
            notInAirCounter++;
        }else{
            notInAirCounter = 0;
        }
        if((notInAirCounter>=3)){
            destroy();
        }
        super.update(time);
    }

    public abstract Collection<? extends LivingObject> getTargets();
}
