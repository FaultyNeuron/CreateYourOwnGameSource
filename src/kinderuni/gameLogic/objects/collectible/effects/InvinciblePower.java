package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.Player;

/**
* Created by Georg Plaz.
*/
public class InvinciblePower extends ReversibleEffect {
    public static final String ID = "invincible";

    public InvinciblePower() {
    }

    public InvinciblePower(Reverser reverser) {
        super(reverser);
    }

    @Override
    public void activate(LivingObject target) {
        super.activate(target);
        target.setInvincible();
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getTarget().setVincible();
    }

    @Override
    public String toString() {
        return "InvinciblePower{" +
                "reverser=" + getReverser() +
                "}";
    }
}
