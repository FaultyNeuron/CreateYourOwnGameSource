package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class TimeBasedEffect extends ReversibleEffect {
    private int remainingTime;
    private int totalTime;
    private ReversibleEffect effect;
    public TimeBasedEffect(int totalTime, ReversibleEffect effect) {
        this.remainingTime = totalTime;
        this.totalTime = totalTime;
        this.effect = effect;
    }

    @Override
    public void update() {
        effect.update();
        remainingTime--;
        if(remainingTime==0){
            deActivate();
        }
    }

    @Override
    public ReversibleEffect copy() {
        return new TimeBasedEffect(totalTime, effect.copy());
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    @Override
    public void activate(Player player) {
        super.activate(player);
        effect.activate(player);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        effect.deActivate();
    }

    @Override
    public String toString() {
        return "TimeBasedEffect{" +
                "remainingTime=" + remainingTime +
                ", effect=" + effect +
                '}';
    }
}
