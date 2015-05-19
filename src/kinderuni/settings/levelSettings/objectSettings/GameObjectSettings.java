package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class GameObjectSettings {
    private GraphicsSettings graphics;
    private Double x;
    private Double y;

    public boolean placeRandom(){
        return x==null || y==null;
    }

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }
}
