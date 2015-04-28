package kinderuni.graphics;

import functionalJava.data.Direction1D;
import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public interface GraphicsObject {
    public Direction1D getDirection();
    public void setDirection(Direction1D direction);

    public DoubleTupel getDimensions();
    public void blink(double blinkPerSecond, double timeOn);
    public void blink(double blinkPerSecond);

    void stopBlink();
//    public void draw(Screen screen);

    public enum STATE{
        STANDING, WALKING, JUMPING, FLYING,
    }
}
