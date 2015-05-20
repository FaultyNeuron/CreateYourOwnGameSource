package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class FloorSettings {
    private double gap_width;
    private double friction = ObjectSettings.DEFAULT_FLOOR_FRICTION;
//    private double tile_width;
    private GraphicsSettings graphics;

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

//    public double getTileWidth() {
//        return tile_width;
//    }

//    public void setTileWidth(double tileWidth) {
//        this.tile_width = tileWidth;
//    }

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }
}
