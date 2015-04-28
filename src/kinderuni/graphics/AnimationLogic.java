package kinderuni.graphics;

/**
 * Created by Georg Plaz.
 */
public class AnimationLogic {
    private int playbackTimes;
    private int playbackTimesLeft;
    private long animationStartingTime;
    private int frameDelta;
    private LoopType loopType;
    private boolean hasStarted;
    private boolean isPaused;
    private long pauseStartingTime;
    private long pauseEndingTime;
    private int pauseDelta;
    private int framesCount;
    private boolean paused;

    public AnimationLogic(int fps, int framesCount) {
        this(fps, LoopType.BACK_AND_FORTH, framesCount);
    }

    public AnimationLogic(int fps, LoopType loopType, int framesCount) {
        this(-1, fps, loopType, framesCount);
    }

    public AnimationLogic(int playbackTimes, int fps, LoopType loopType, int framesCount) {
        this.playbackTimes = playbackTimes;
        this.loopType = loopType;
        this.framesCount = framesCount;
        frameDelta = 1000/fps;
        reset();
    }

    public void start(){
        if(!hasStarted) {
            animationStartingTime = System.currentTimeMillis();
            hasStarted = true;
        }else if(isPaused){
            breakPause();
        }
    }

    public void pause() {
        this.pause(Long.MAX_VALUE);
    }

    public void pause(long pauseDelta) {
        long currentTime = System.currentTimeMillis();
        pause(currentTime, currentTime + pauseDelta);
    }

    private void pause(long pauseStartingTime, long pauseEndTime){
        pauseEndingTime = pauseEndTime;
        if(!isPaused){
            isPaused = true;
            this.pauseStartingTime = pauseStartingTime;
        }
    }

    private void breakPause(){
        if(isPaused) {
            pauseDelta += System.currentTimeMillis() - pauseStartingTime;
            isPaused = false;
        }
    }

    public void stop(){
        reset();
        //todo set everything back to default
    }

    private void reset(){
        this.playbackTimesLeft = playbackTimes;
        isPaused = false;
        hasStarted = false;
        pauseDelta = 0;
    }

    public boolean loopsInfinitly(){
        return playbackTimes == -1;
    }

    public int getPlaybackTimesLeft(){
        return playbackTimesLeft;
    }

    public int getCurrentFrame(){
        if(hasStarted){
            long currentTime = System.currentTimeMillis();
            int timeDelta = (int) (currentTime - (animationStartingTime + getPauseDelta(currentTime)));
            int cyclePosition = timeDelta % (frameDelta * framesCount);
            int cyclesPassed = timeDelta / (frameDelta * framesCount);
            int framePos = cyclePosition / frameDelta;
            return handleLoopType(cyclesPassed, framePos);
//            if(loopsInfinitly() || cyclesPassed < playbackTimes) {
//                int framePos = cyclePosition / frameDelta;
//                if (loopType == LoopType.START_OVER || (cyclesPassed) % 2 == 0) {
//                    return framePos;
//                }else{
//                    return framesCount - 1 - framePos;
//                }
//            }else{
//                if (loopType == LoopType.START_OVER || (cyclesPassed) % 2 == 1) {
//                    return framesCount - 1;
//                } else {
//                    return 0;
//                }
//            }
        }else{
            return 0;
        }
    }

    private int handleLoopType(int cyclesPassed, int framePos){
        boolean looping = loopsInfinitly() || cyclesPassed < playbackTimes;
        switch (loopType){
            case START_OVER:
                if(looping){
                    return framePos;
                }else{
                    return framesCount - 1;
                }
            case BACK_AND_FORTH:
                framePos = (framePos+cyclesPassed)%framesCount;
                if(looping){
                    if(cyclesPassed % 2 == 0) {
                        return framePos;
                    }else{
                        return framesCount - 1 - framePos;
                    }
                }else if(playbackTimes % 2 == 1){
                    return framesCount - 1;
                }else{
                    return 0;
                }
            default:
                return 0;
        }
    }

    public int getPauseDelta() {
        return getPauseDelta(System.currentTimeMillis());
    }

    public int getPauseDelta(long currentTime) {
        if (isPaused()) {
            return (int) (pauseDelta + (currentTime - pauseStartingTime));
        }
        return pauseDelta;
    }

    public boolean isPaused() {
        if(paused){
            long currentTime = System.currentTimeMillis();
            if(pauseEndingTime<currentTime){
                paused=false;
                pauseDelta+=pauseEndingTime-pauseStartingTime;
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public enum LoopType{
        START_OVER, BACK_AND_FORTH
    }
}
