package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class CombinedReversibleEffect extends ReversibleEffect {
    private List<ReversibleEffect> effects;

    public CombinedReversibleEffect(List<ReversibleEffect> effects) {
        this.effects = effects;
    }

    public CombinedReversibleEffect() {
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

    @Override
    public void deActivate() {
        super.deActivate();
        for(ReversibleEffect effect : effects){
            effect.deActivate();
        }
    }

    public void add(ReversibleEffect effect){
        effects.add(effect);
    }
}
