package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public abstract class ReversibleEffect extends Effect{
    @Override
    public void activate(Player player) {
        super.activate(player);
        player.applyEffect(this);
    }

    public void deActivate() {
        getPlayer().removeEffect(this);
    }

    @Override
    public abstract ReversibleEffect copy();
}
