package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.powerUp.TimeBasedPowerUp;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class PlusSpeed extends Collectible {
    public PlusSpeed(GraphicsObject graphicsObject, DoubleTupel position) {
        super(graphicsObject, position);
    }

    @Override
    public void collect() {
        SpeedPower powerUp = new SpeedPower(2, 1000);
        powerUp.activate(getWorld().getPlayer());
        super.collect();
    }

    private static class SpeedPower extends TimeBasedPowerUp {
        private double factor;
        public SpeedPower(double factor, int remainingTime) {
            super(remainingTime);
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
    }
}
