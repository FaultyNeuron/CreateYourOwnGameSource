package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class TimeBasedReverser implements ReversibleEffect.Reverser{
    private int remainingTime;
    private int totalTime;
    public TimeBasedReverser(int totalTime) {
        this.remainingTime = totalTime;
        this.totalTime = totalTime;
    }

    @Override
    public void update() {
        remainingTime--;
    }

    @Override
    public boolean reverse() {
        return remainingTime<=0;
    }

    public TimeBasedReverser copy() {
        return new TimeBasedReverser(totalTime);
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    @Override
    public String toString() {
        return "TimeBasedReverser{" +
                "remainingTime=" + remainingTime +
                '}';
    }

}
