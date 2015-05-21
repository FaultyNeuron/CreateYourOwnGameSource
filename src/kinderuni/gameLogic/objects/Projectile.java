package kinderuni.gameLogic.objects;

import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public abstract class Projectile extends Collectible {
    private Effect effect;

    protected Projectile(Effect effect) {
        super(effect, 0);
        this.effect = effect;
    }

    @Override
    public void update(int time) {
        super.update(time);
        if(getSpeed().length()<0.01){
            destroy();
        }
    }

    public abstract Collection<? extends LivingObject> getTargets();
}
