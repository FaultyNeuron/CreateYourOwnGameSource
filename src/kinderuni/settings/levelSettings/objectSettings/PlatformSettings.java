package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class PlatformSettings extends GameObjectSettings{
    public static final PlatformSettings DEFAULT = new PlatformSettings();
    static{
        DEFAULT.delta_x = 0.;
        DEFAULT.delta_y = 0.;
        DEFAULT.friction = 0.8;
        DEFAULT.speed = 0.;
    }

    private int count;

    private Double delta_x;
    private Double delta_y;
    private Double friction;
    private Double speed;

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

    @Override
    public String toString() {
        return "PlatformSettings{" +
                ", count=" + count +
                ", delta_x=" + delta_x +
                ", delta_y=" + delta_y +
                ", friction=" + friction +
                ", speed=" + speed +
                '}';
    }

    public boolean hasFriction() {
        return friction!=null;
    }

    public boolean hasSpeed() {
        return speed!=null;
    }

    public boolean hasDelta() {
        return delta_x!=null && delta_y!=null;
    }
}
