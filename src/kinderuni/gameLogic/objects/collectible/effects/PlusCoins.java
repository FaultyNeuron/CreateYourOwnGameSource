package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class PlusCoins extends Effect {
    public static final String ID = "plus_coins";
    private int coins;
    public PlusCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public void activate(Player player) {
        player.addCoins(coins);
    }

    @Override
    public PlusCoins copy() {
        return new PlusCoins(coins);
    }

    @Override
    public String toString() {
        return "PlusCoins{" +
                "coins=" + coins +
                '}';
    }
}
