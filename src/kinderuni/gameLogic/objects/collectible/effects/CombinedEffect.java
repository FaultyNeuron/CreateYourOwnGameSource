package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class CombinedEffect extends Effect{
    private List<Effect> effects;

    public CombinedEffect(List<Effect> effects) {
        this.effects = effects;
    }

    public CombinedEffect() {
        this.effects = new LinkedList<>();
    }

    @Override
    public void update(int time) {
        for(Effect effect : effects){
            effect.update(time);
        }
    }

    @Override
    public void activate(LivingObject target) {
        super.activate(target);
        for(Effect effect : effects){
            effect.activate(target);
        }
    }

    public void add(Effect effect){
        effects.add(effect);
    }
}
