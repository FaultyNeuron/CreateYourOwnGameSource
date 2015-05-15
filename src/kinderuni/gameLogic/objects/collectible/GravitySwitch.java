package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.powerUp.TimeBasedPowerUp;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class GravitySwitch extends Collectible {
    public GravitySwitch(GraphicsObject graphicsObject, DoubleTupel position) {
        super(graphicsObject, position);
    }

    @Override
    public void collect() {
        GravitySwitchPower powerUp = new GravitySwitchPower(1000);
        powerUp.activate(getWorld().getPlayer());
        super.collect();
    }

    private static class GravitySwitchPower extends TimeBasedPowerUp {
        public GravitySwitchPower(int remainingTime) {
            super(remainingTime);
        }

        @Override
        public void activate(Player player) {
            super.activate(player);
            switchGravity(player.getWorld());
        }

        @Override
        public void deActivate() {
            super.deActivate();
            switchGravity(getPlayer().getWorld());
        }
        private void switchGravity(GameWorld gameWorld){
            gameWorld.setGravity(-gameWorld.getGravity());
        }
    }
}
