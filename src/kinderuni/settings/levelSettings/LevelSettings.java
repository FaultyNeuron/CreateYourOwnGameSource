package kinderuni.settings.levelSettings;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.settings.IdParametersSettings;
import kinderuni.settings.levelSettings.objectSettings.EffectSettings;
import kinderuni.settings.levelSettings.objectSettings.FloorSettings;
import kinderuni.settings.levelSettings.objectSettings.GoalSettings;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class LevelSettings {
    private String seed;
    private String levelName;

    private double width;
    private double height;

    private double gravity;
    private double airFriction;

    private double player_x;
    private double player_y;

    private int[] bg_colour;

    private double vertical_platform_distance;
    private double horizontal_platform_distance;

    private List<IdParametersSettings> enemies = new LinkedList<>();
    private List<IdParametersSettings> platforms = new LinkedList<>();
    private GoalSettings goal;
    private FloorSettings floor;
    private List<EffectSettings> active_effects = new LinkedList<>();
    private List<IdParametersSettings> back_ground_objects = new LinkedList<>();

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

    public List<IdParametersSettings> getEnemies() {
        return enemies;
    }

    public List<IdParametersSettings> getPlatforms() {
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

    public List<EffectSettings> getActiveEffects() {
        return active_effects;
    }

    public double getVerticalDistance() {
        return vertical_platform_distance;
    }

    public double getHorizontalDistance() {
        return horizontal_platform_distance;
    }

    public boolean hasBgColour(){
        return bg_colour!=null;
    }
    public int[] getBgColour() {
        return bg_colour;
    }

    public List<IdParametersSettings> getBackGroundObjects() {
        return back_ground_objects;
    }
}
