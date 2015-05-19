package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

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
    public void update() {
        for(Effect effect : effects){
            effect.update();
        }
    }

    @Override
    public void activate(Player player) {
        super.activate(player);
        for(Effect effect : effects){
            effect.activate(player);
        }
    }

    @Override
    public void deActivate() {
        super.deActivate();
        for(ReversibleEffect effect : effects){
            effect.deActivate();
        }
    }

    @Override
    public ReversibleEffect copy() {
        List<ReversibleEffect> effectsCopied = new LinkedList<>();
        for(ReversibleEffect effect : effects){
            effectsCopied.add(effect.copy());
        }
        return new CombinedReversibleEffect();
    }
    public void add(ReversibleEffect effect){
        effects.add(effect);
    }
}
