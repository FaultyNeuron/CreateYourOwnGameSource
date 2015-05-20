package kinderuni.ui.graphics;

import functionalJava.data.Direction1D;
import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public interface GraphicsObject {
    public Direction1D getDirection();
    public void setDirection(Direction1D direction);
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
        STANDING, WALKING, JUMPING, FLYING,
    }
}
