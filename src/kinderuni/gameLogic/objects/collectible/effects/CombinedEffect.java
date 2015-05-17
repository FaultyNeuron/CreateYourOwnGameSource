package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

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
    public Effect copy() {
        List<Effect> effectsCopied = new LinkedList<>();
        for(Effect effect : effects){
            effectsCopied.add(effect.copy());
        }
        return new CombinedEffect();
    }
    public void add(Effect effect){
        effects.add(effect);
    }
}
