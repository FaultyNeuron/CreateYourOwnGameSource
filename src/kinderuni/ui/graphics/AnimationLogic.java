package kinderuni.ui.graphics;

/**
 * Created by Georg Plaz.
 */
public interface AnimationLogic {
    void start();

    void pause();

    void pause(long pauseDelta);

    void stop();

    boolean loopsInfinitly();

    int getPlaybackTimesLeft();

    int getCurrentFrame();

    int getPauseDelta();

    int getPauseDelta(long currentTime);

    boolean isPaused();

    public enum LoopType{
        START_OVER, BACK_AND_FORTH
    }
}
