package kinderuni.gameLogic.objects.collectible.effects;

/**
 * Created by Georg Plaz.
 */
public class TimeBasedReverser implements ReversibleEffect.Reverser{
    private int startTime = -1;
    private int duration;
    public TimeBasedReverser(int duration) {
        this.duration = duration;
//        startTime = currentTime;
    }

    @Override
    public void update(int time) {
        if(startTime<0){
            startTime = time;
        }
    }

    @Override
    public boolean reverse(int time) {
        return startTime+duration < time;
    }

    @Override
    public ReversibleEffect.Reverser copy() {
        return new TimeBasedReverser(duration);
    }

    @Override
    public String toString() {
        return "TimeBasedReverser{" +
                "duration=" + duration +
                '}';
    }

}
