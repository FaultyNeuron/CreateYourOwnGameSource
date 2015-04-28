package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class FloorSettings {
    public static final FloorSettings DEFAULT = createDefaultFloor();

    private double gapWidth;
    private double friction = ObjectSettings.DEFAULT_FLOOR_FRICTION;
    private double tileWidth;

    public double getGapWidth() {
        return gapWidth;
    }

    public void setGapWidth(double gapWidth) {
        this.gapWidth = gapWidth;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(double tileWidth) {
        this.tileWidth = tileWidth;
    }

    public static FloorSettings createDefaultFloor() {
        FloorSettings toReturn = new FloorSettings();
        toReturn.setFriction(0.8);
        toReturn.setTileWidth(300);
        toReturn.setGapWidth(100);
        return toReturn;
    }
}
