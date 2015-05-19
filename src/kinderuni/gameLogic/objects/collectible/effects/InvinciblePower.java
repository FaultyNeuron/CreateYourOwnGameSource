package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
* Created by Georg Plaz.
*/
public class InvinciblePower extends ReversibleEffect {
    public static final String ID = "invincible";

    @Override
    public void activate(Player player) {
        super.activate(player);
        player.setInvincible();
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getPlayer().setVincible();
    }

    @Override
    public ReversibleEffect copy() {
        return new InvinciblePower();
    }

    @Override
    public String toString() {
        return "InvinciblePower{" +
                "}";
    }
}
