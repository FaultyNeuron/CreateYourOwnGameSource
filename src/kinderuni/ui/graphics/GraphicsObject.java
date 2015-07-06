package kinderuni.ui.graphics;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public interface GraphicsObject {
    public HorizontalDirection getDirection();
    public void setDirection(HorizontalDirection direction);
    public State getState();
    public void setState(State state);

    public DoubleTupel getDimensions();
//    public void blink(double blinkPerSecond, int blinks);
    public void blink(double blinkPerSecond, double timeOn);
    public void blink(double blinkPerSecond);

    public void stopBlink();

    public GraphicsObject copy();

    void painting(boolean toPaint);
//    public void draw(Screen screen);

    public enum State {
        IDLE, WALKING, JUMPING, FLYING,
    }
}
