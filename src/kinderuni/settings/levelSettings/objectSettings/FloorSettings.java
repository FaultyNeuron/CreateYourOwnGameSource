package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class FloorSettings {
    public static final FloorSettings DEFAULT = createDefaultFloor();

    private double gap_width;
    private double friction = ObjectSettings.DEFAULT_FLOOR_FRICTION;
    private double tile_width;

    public double getGapWidth() {
        return gap_width;
    }

    public void setGapWidth(double gapWidth) {
        this.gap_width = gapWidth;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getTileWidth() {
        return tile_width;
    }

    public void setTileWidth(double tileWidth) {
        this.tile_width = tileWidth;
    }

    public static FloorSettings createDefaultFloor() {
        FloorSettings toReturn = new FloorSettings();
        toReturn.setFriction(0.8);
        toReturn.setTileWidth(300);
        toReturn.setGapWidth(100);
        return toReturn;
    }
}
