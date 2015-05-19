package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
* Created by Georg Plaz.
*/
public class SpeedPower extends ReversibleEffect {
    public static final String ID = "speed";
    private double factor;
    public SpeedPower(double factor) {
        this.factor = factor;
    }

    @Override
    public void activate(Player player) {
        super.activate(player);
        player.speedUp(factor);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getPlayer().speedUp(1/factor);
    }

    @Override
    public ReversibleEffect copy() {
        return new SpeedPower(factor);
    }

    @Override
    public String toString() {
        return "SpeedPower{" +
                "factor=" + factor +
                '}';
    }


}
