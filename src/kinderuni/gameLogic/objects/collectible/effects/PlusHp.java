package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class PlusHp extends Effect {
    public static final String ID = "plus_hp";
    private int hp;
    public PlusHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void activate(Player player) {
        player.heal(hp);
    }

    @Override
    public PlusHp copy() {
        return new PlusHp(hp);
    }

    @Override
    public String toString() {
        return "PlusHp{" +
                "hp=" + hp +
                '}';
    }
}