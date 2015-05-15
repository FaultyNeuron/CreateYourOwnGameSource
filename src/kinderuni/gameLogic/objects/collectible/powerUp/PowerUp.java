package kinderuni.gameLogic.objects.collectible.powerUp;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public abstract class PowerUp {
    private Player player;
    public void activate(Player player){
        this.player = player;
        player.addPowerUp(this);
    }
    public abstract void update();
    public void deActivate() {
        player.removePowerUp(this);
    }

    public Player getPlayer() {
        return player;
    }
}
