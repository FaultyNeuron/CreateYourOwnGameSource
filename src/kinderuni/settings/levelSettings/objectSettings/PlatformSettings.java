package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class PlatformSettings{
    private double width;
    private int count;

    private double delta_x = 0;
    private double delta_y = 0;
    private double friction = ObjectSettings.DEFAULT_FLOOR_FRICTION;
    private double speed = 0;
    private GraphicsSettings graphics;

    public double getDeltaX() {
        return delta_x;
    }

    public void setDelta(double deltaX, double deltaY) {
        this.delta_x = deltaX;
        this.delta_y = deltaY;
    }

    public double getDeltaY() {
        return delta_y;
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

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }

    @Override
    public String toString() {
        return "PlatformSettings{" +
                "width=" + width +
                ", count=" + count +
                ", delta_x=" + delta_x +
                ", delta_y=" + delta_y +
                ", friction=" + friction +
                ", speed=" + speed +
                '}';
    }
}
