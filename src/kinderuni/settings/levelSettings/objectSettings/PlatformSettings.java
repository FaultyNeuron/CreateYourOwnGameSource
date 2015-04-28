package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class PlatformSettings{
    private double width;
    private int count;

    private double deltaX = 0;
    private double deltaY = 0;
    private double friction = ObjectSettings.DEFAULT_FLOOR_FRICTION;
    private double speed = 0;

    public double getDeltaX() {
        return deltaX;
    }

    public void setDelta(double deltaX, double deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DoubleTupel getDelta() {
        return new DoubleTupel(getDeltaX(), getDeltaY());
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
