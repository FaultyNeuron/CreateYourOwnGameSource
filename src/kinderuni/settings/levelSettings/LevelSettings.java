package kinderuni.settings.levelSettings;

import kinderuni.settings.levelSettings.objectSettings.ObjectSettings;
import util.RandomHolder;

/**
 * Created by Georg Plaz.
 */
public class LevelSettings {
    public static final LevelSettings STANDARD = createStandardLevelSettings();
    public static final LevelSettings SPACE = createSpaceLevelSettings();
    public static final LevelSettings ICE = createIceLevelSettings();

    private String seed;
    private EnvironmentSettings environmentSettings;
    private ObjectSettings objectSettings;
    private String levelName;

    public EnvironmentSettings getEnvironmentSettings() {
        return environmentSettings;
    }

    public ObjectSettings getObjectSettings() {
        return objectSettings;
    }

    public String getSeed() {
        return seed;
    }

    public String getLevelName() {
        return levelName;
    }

    private static LevelSettings createStandardLevelSettings() {
        LevelSettings toReturn = new LevelSettings();
        toReturn.levelName = "standard";
        toReturn.environmentSettings = EnvironmentSettings.STANDARD;
        toReturn.objectSettings = ObjectSettings.STANDARD;
        toReturn.seed = String.valueOf(RandomHolder.random.nextLong());
        return toReturn;
    }

    private static LevelSettings createSpaceLevelSettings() {
        LevelSettings toReturn = new LevelSettings();
        toReturn.levelName = "space";
        toReturn.environmentSettings = EnvironmentSettings.SPACE;
        toReturn.objectSettings = ObjectSettings.SPACE;
        toReturn.seed = String.valueOf(RandomHolder.random.nextLong());
        return toReturn;
    }

    private static LevelSettings createIceLevelSettings() {
        LevelSettings toReturn = new LevelSettings();
        toReturn.levelName = "ice";
        toReturn.environmentSettings = EnvironmentSettings.STANDARD;
        toReturn.objectSettings = ObjectSettings.ICE;
        toReturn.seed = String.valueOf(RandomHolder.random.nextLong());
        return toReturn;
    }

}
