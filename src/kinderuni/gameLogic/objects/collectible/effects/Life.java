package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class Life extends Effect {
    public static final String ID = "life";
    private int life;
    public Life(int life) {
        this.life = life;
    }

    @Override
    public void activate(LivingObject target) {
        if(target instanceof Player){
            ((Player)target).addLife(life);
        }
    }

    @Override
    public String toString() {
        return "PlusLife{" +
                "life=" + life +
                '}';
    }
}
