package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class Coins extends Effect {
    public static final String ID = "coins";
    private int coins;
    public Coins(int coins) {
        this.coins = coins;
    }

    @Override
    public void activate(LivingObject target) {
        if(target instanceof Player){
            ((Player)target).addCoins(coins);
        }
    }

    @Override
    public String toString() {
        return "PlusCoins{" +
                "coins=" + coins +
                '}';
    }
}
