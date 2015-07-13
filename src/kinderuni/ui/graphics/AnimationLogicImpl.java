package kinderuni.ui.graphics;

/**
 * Created by Georg Plaz.
 */
public class AnimationLogicImpl implements AnimationLogic {
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
    
    public AnimationLogicImpl(double fps, int framesCount) {
        this(fps, LoopType.BACK_AND_FORTH, framesCount);
    }

    public AnimationLogicImpl(double fps, LoopType loopType, int framesCount) {
        this(-1, fps, loopType, framesCount);
    }

    public AnimationLogicImpl(int playbackTimes, double fps, LoopType loopType, int framesCount) {
        this.playbackTimes = playbackTimes;
        this.loopType = loopType;
        this.framesCount = framesCount;
        frameDelta = (int) Math.round(1000/fps);
        reset();
    }

    @Override
    public void start(){
        if(!hasStarted) {
            animationStartingTime = System.currentTimeMillis();
            hasStarted = true;
        }else if(isPaused){
            breakPause();
        }
    }

    @Override
    public void pause() {
        this.pause(Long.MAX_VALUE);
    }

    @Override
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

    @Override
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

    @Override
    public boolean loopsInfinitly(){
        return playbackTimes == -1;
    }

    @Override
    public int getPlaybackTimesLeft(){
        return playbackTimesLeft;
    }

    @Override
    public int getCurrentFrame(){
        if(hasStarted){
            if(framesCount==1){
                return 0;
            }
            long currentTime = System.currentTimeMillis();
            int timeDelta = (int) (currentTime - (animationStartingTime + getPauseDelta(currentTime)));
//            int cyclePosition = timeDelta % (frameDelta * framesCount);
//            int cyclesPassed = timeDelta / (frameDelta * framesCount);
//            int framePos = cyclePosition / frameDelta;

            return handleLoopType(timeDelta);
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

    private int handleLoopType(int timeDelta){
        switch (loopType){
            case START_OVER: {
                int cyclePosition = timeDelta % (frameDelta * framesCount);
                int cyclesPassed = timeDelta / (frameDelta * framesCount);
                int framePos = cyclePosition / frameDelta;
                boolean looping = loopsInfinitly() || cyclesPassed < playbackTimes;
                if (looping) {
                    return framePos;
                } else {
                    return framesCount - 1;
                }
            }
            case BACK_AND_FORTH: {
                int cyclePosition = timeDelta % (frameDelta * (framesCount-1));
                int cyclesPassed = timeDelta / (frameDelta * (framesCount-1));
                int framePos = cyclePosition / frameDelta;
                int toReturn;
                boolean looping = loopsInfinitly() || cyclesPassed < playbackTimes;

                if (looping) {
                    if (cyclesPassed % 2 == 0) {
                        toReturn = framePos;
                    } else {
                        toReturn = framesCount - 1 - framePos;
                    }
                } else if (playbackTimes % 2 == 1) {
                    toReturn = framesCount - 1;
                } else {
                    toReturn = 0;
                }
                return toReturn;
            }
            default:
                return 0;
        }
    }

    @Override
    public int getPauseDelta() {
        return getPauseDelta(System.currentTimeMillis());
    }

    @Override
    public int getPauseDelta(long currentTime) {
        if (isPaused()) {
            return (int) (pauseDelta + (currentTime - pauseStartingTime));
        }
        return pauseDelta;
    }

    @Override
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

}
