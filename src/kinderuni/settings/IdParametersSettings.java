package kinderuni.settings;

import kinderuni.settings.levelSettings.objectSettings.EnemySettings;
import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;
import kinderuni.settings.levelSettings.objectSettings.PlatformSettings;

/**
 * Created by Georg Plaz.
 */
public class IdParametersSettings {
    private String id;
    private int count = 1;
//    private GraphicsSettings graphics;
    private EnemySettings enemy;
    private PlatformSettings platform;
    private GraphicsSettings graphics;

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public EnemySettings getEnemy() {
        return enemy;
    }

    public PlatformSettings getPlatform() {
        return platform;
    }

    public boolean hasPlatform() {
        return platform!=null;
    }

    public boolean hasEnemy(){
        return enemy!=null;
    }

    public GraphicsSettings getGraphics() {
        return graphics;
    }

    public boolean hasGraphics() {
        return graphics!=null;
    }
}
