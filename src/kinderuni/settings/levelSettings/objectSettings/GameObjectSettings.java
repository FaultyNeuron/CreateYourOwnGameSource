package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class GameObjectSettings extends DimensionsSettings{
    private GraphicsSettings graphics;
    private Double x;
    private Double y;

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }

    public DoubleTupel getPosition(){
        return new DoubleTupel(x, y);
    }

    public boolean hasPosition() {
        return x!=null && y!=null;
    }

    public boolean hasGraphicsSettings() {
        return graphics!=null;
    }
}
