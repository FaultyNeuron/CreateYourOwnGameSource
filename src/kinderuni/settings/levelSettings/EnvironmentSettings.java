package kinderuni.settings.levelSettings;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class EnvironmentSettings {
    public static final EnvironmentSettings STANDARD = createStandardLevelSettings();
    public static final EnvironmentSettings SPACE = createSpaceLevelSettings();

    private DoubleTupel dimensions;
    private int goalType; //0=physicalGoal, 1=killEnemies, 2=collectX
    private double gravity;
    private double airFriction;
    private double playerInitPosX;
    private double playerInitPosY;

    public int getGoalType() {
        return goalType;
    }

    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    public DoubleTupel getDimensions() {
        return dimensions;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getAirFriction() {
        return airFriction;
    }

    public void setAirFriction(double airFriction) {
        this.airFriction = airFriction;
    }

    private static EnvironmentSettings createStandardLevelSettings(){
        EnvironmentSettings toReturn = new EnvironmentSettings();
        toReturn.dimensions = new DoubleTupel(5000, 500);
        toReturn.playerInitPosX = 50;
        toReturn.playerInitPosY = 50;
        toReturn.goalType = 0;
        toReturn.gravity = 0.1;
        toReturn.airFriction = 0.005;

        //todo create standard settings..

        return toReturn;
    }

    private static EnvironmentSettings createSpaceLevelSettings() {
        EnvironmentSettings toReturn = createStandardLevelSettings();
        toReturn.gravity = 0.03;

        return toReturn;
    }

    public DoubleTupel getPlayerInitPos() {
        return new DoubleTupel(playerInitPosX, playerInitPosY);
    }

    public void setPlayerInitPos(double playerInitPosX, double playerInitPosY) {
        this.playerInitPosX = playerInitPosX;
        this.playerInitPosY = playerInitPosY;
    }
}
