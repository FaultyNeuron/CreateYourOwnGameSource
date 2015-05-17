package kinderuni.settings.levelSettings;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.settings.IdCountPair;
import kinderuni.settings.levelSettings.objectSettings.FloorSettings;
import kinderuni.settings.levelSettings.objectSettings.GoalSettings;
import kinderuni.settings.levelSettings.objectSettings.ObjectSettings;
import util.RandomHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class LevelSettings {
    public static final LevelSettings STANDARD = createStandardLevelSettings();
    public static final LevelSettings SPACE = createSpaceLevelSettings();
    public static final LevelSettings ICE = createIceLevelSettings();

    private String seed;
//    private EnvironmentSettings environmentSettings;
//    private ObjectSettings objectSettings;
    private String levelName;

    private double width;
    private double height;

    private double gravity;
    private double airFriction;

    private double player_x;
    private double player_y;

    private List<IdCountPair> enemies = new LinkedList<>();
    private List<IdCountPair> platforms = new LinkedList<>();
    private GoalSettings goal;
    private FloorSettings floor;


//    public EnvironmentSettings getEnvironmentSettings() {
//        return environmentSettings;
//    }
//
//    public ObjectSettings getObjectSettings() {
//        return objectSettings;
//    }

    public double getAirFriction() {
        return airFriction;
    }

    public DoubleTupel getDimensions() {
        return new DoubleTupel(width, height);
    }

    public double getGravity() {
        return gravity;
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
//        toReturn.environmentSettings = EnvironmentSettings.STANDARD;
//        toReturn.objectSettings = ObjectSettings.STANDARD;
        toReturn.seed = String.valueOf(RandomHolder.random.nextLong());
        return toReturn;
    }

    private static LevelSettings createSpaceLevelSettings() {
        LevelSettings toReturn = new LevelSettings();
        toReturn.levelName = "space";
//        toReturn.environmentSettings = EnvironmentSettings.SPACE;
//        toReturn.objectSettings = ObjectSettings.SPACE;
        toReturn.seed = String.valueOf(RandomHolder.random.nextLong());
        return toReturn;
    }

    private static LevelSettings createIceLevelSettings() {
        LevelSettings toReturn = new LevelSettings();
        toReturn.levelName = "ice";
//        toReturn.environmentSettings = EnvironmentSettings.STANDARD;
//        toReturn.objectSettings = ObjectSettings.ICE;
        toReturn.seed = String.valueOf(RandomHolder.random.nextLong());
        return toReturn;
    }

    public List<IdCountPair> getEnemies() {
        return enemies;
    }

    public List<IdCountPair> getPlatforms() {
        return platforms;
    }

    public DoubleTupel getPlayerInitPos() {
        return new DoubleTupel(player_x, player_y);
    }

    public GoalSettings getGoalSettings() {
        return goal;
    }

    public FloorSettings getFloorSettings() {
        return floor;
    }

    @Override
    public String toString() {
        return "LevelSettings{" +
                "seed='" + seed + '\'' +
                ", levelName='" + levelName + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", gravity=" + gravity +
                ", airFriction=" + airFriction +
                ", player_x=" + player_x +
                ", player_y=" + player_y +
                ", enemies=" + enemies +
                ", platforms=" + platforms +
                ", goal=" + goal +
                ", floor=" + floor +
                '}';
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
