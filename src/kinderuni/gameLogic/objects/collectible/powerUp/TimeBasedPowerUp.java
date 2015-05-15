package kinderuni.gameLogic.objects.collectible.powerUp;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public abstract class TimeBasedPowerUp extends PowerUp {
    private int remainingTime;
    public TimeBasedPowerUp(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public void update() {
        remainingTime--;
        if(remainingTime==0){
            deActivate();
        }
    }
}
