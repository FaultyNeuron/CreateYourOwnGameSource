package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class GoalSettings{
    private double x;
    private double y;
    private GraphicsSettings graphics;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public DoubleTupel getPosition() {
        return new DoubleTupel(x, y);
    }

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }
}
