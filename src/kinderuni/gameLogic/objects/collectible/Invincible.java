package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.powerUp.TimeBasedPowerUp;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class Invincible extends Collectible {
    public Invincible(GraphicsObject graphicsObject, DoubleTupel position) {
        super(graphicsObject, position);
    }

    @Override
    public void collect() {
        InvinciblePower powerUp = new InvinciblePower(1000);
        powerUp.activate(getWorld().getPlayer());
        super.collect();
    }

    public static class InvinciblePower extends TimeBasedPowerUp {
        public InvinciblePower(int remainingTime) {
            super(remainingTime);
        }

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
    }
}
