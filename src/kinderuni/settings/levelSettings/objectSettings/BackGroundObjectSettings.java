package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

import java.io.ObjectStreamClass;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class BackGroundObjectSettings extends GameObjectSettings {
    public static final BackGroundObjectSettings DEFAULT = new BackGroundObjectSettings();

    static{
        DEFAULT.distance_factor = 1.;
    }

    private double[] y_range;
    private Double distance_factor;

    public DoubleTupel getYRange() {
        return new DoubleTupel(y_range);
    }

    public boolean hasDistanceFactor(){
        return distance_factor!=null;
    }

    public double getDistanceFactor() {
        return distance_factor;
    }
}
