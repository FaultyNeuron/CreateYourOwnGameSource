package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class PlusLife extends Effect {
    public static final String ID = "plus_life";
    private int life;
    public PlusLife(int life) {
        this.life = life;
    }

    @Override
    public void activate(Player player) {
        player.addLife(life);
    }

    @Override
    public PlusLife copy() {
        return new PlusLife(life);
    }

    @Override
    public String toString() {
        return "PlusLife{" +
                "life=" + life +
                '}';
    }
}
