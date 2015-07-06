package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class Hp extends Effect {
    public static final String ID = "hp";
    private int hp;
    public Hp(int hp) {
        this.hp = hp;
    }

    @Override
    public void activate(LivingObject target) {
        target.heal(hp);
    }

    @Override
    public String toString() {
        return "PlusHp{" +
                "hp=" + hp +
                '}';
    }
}
