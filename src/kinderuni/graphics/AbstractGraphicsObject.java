package kinderuni.graphics;

import functionalJava.data.Direction1D;
import functionalJava.data.tupel.DoubleTupel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Georg Plaz.
 */
public abstract class AbstractGraphicsObject implements GraphicsObject {
    private DoubleTupel dimensions;
    private boolean isBlinking = false;
    private long blinkStartTime;
    private int blinkDelta;
    private double timeOn;
    private Direction1D direction;
    private State state;
    private Map<State, AnimationLogic> animationLogicMap = new HashMap<>();

    public AbstractGraphicsObject(double width, double height) {
        this(new DoubleTupel(width, height));
    }
    public AbstractGraphicsObject(DoubleTupel dimensions) {
        this.dimensions = dimensions;
        state = State.STANDING;
    }

    public void addState(State state, AnimationLogic animationLogic){
        animationLogic.start();
        animationLogicMap.put(state, animationLogic);
    }
    public void addStillState(State state){
        addState(state, StillAnimationLogic.get());
    }
    public void addState(State state, double fps, int frameCount){
        AnimationLogic animationLogic = new AnimationLogicImpl(fps, frameCount);
        addState(state, animationLogic);
    }

    @Override
    public Direction1D getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction1D direction) {
        this.direction = direction;
    }

    @Override
    public DoubleTupel getDimensions() {
        return dimensions;
    }

    @Override
    public void blink(double blinkFrequency) {
        blink(blinkFrequency, 0.5);
    }

    @Override
    public void blink(double blinkFrequency, double timeOn) {
        this.timeOn = timeOn;
        this.blinkDelta = (int) Math.round(1000/blinkFrequency);
        blinkStartTime = System.currentTimeMillis();
        isBlinking = true;
    }

    @Override
    public void stopBlink(){
        isBlinking = false;
    }

    public boolean blinkShow(){
        long timeDelta = System.currentTimeMillis() - blinkStartTime;
        long interval = timeDelta % blinkDelta;
        return interval < blinkDelta*timeOn;
//        return intervalsPassed%2==0;
    }

    public boolean isBlinking() {
        return isBlinking;
    }

    @Override
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCurrentFrame(){
        return animationLogicMap.get(getState()).getCurrentFrame();
    }
}
