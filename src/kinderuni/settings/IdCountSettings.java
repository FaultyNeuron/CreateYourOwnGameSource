package kinderuni.settings;

import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;

/**
 * Created by Georg Plaz.
 */
public class IdCountSettings {
    private String id;
    private int count;
    private GraphicsSettings graphics;

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }

    public boolean hasGraphicsSettings(){
        return graphics!=null;
    }
}
