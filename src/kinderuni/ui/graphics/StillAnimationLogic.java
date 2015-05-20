package kinderuni.ui.graphics;

/**
 * Created by Georg Plaz.
 */
public class StillAnimationLogic implements AnimationLogic {
    private static final StillAnimationLogic singleton = new StillAnimationLogic();
    private StillAnimationLogic(){}
    public static StillAnimationLogic get(){
        return singleton;
    }
    @Override
    public void start() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void pause(long pauseDelta) {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean loopsInfinitly() {
        return false;
    }

    @Override
    public int getPlaybackTimesLeft() {
        return -1;
    }

    @Override
    public int getCurrentFrame() {
        return 0;
    }

    @Override
    public int getPauseDelta() {
        return 0;
    }

    @Override
    public int getPauseDelta(long currentTime) {
        return 0;
    }

    @Override
    public boolean isPaused() {
        return false;
    }
}
