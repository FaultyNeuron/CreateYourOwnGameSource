package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.Tupel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class ObjectsSettings {
//    public static final ObjectSettings STANDARD = createStandardLevelSettings();
//    public static final ObjectSettings SPACE = createSpaceLevelSettings();
//    public static final ObjectSettings ICE = createIceLevelSettings();


    private List<Tupel<EnemySettings, Integer>> enemies;
    private List<PlatformSettings> platformSettings;
    private GoalSettings goalSettings;
    private FloorSettings floorSettings;

    public List<Tupel<EnemySettings, Integer>> getEnemySettings() {
        return enemies;
    }

    public List<PlatformSettings> getPlatformSettings() {
        return platformSettings;
    }


    public GoalSettings getGoalSettings() {
        return goalSettings;
    }

    public void setGoalSettings(GoalSettings goalSettings) {
        this.goalSettings = goalSettings;
    }

    public FloorSettings getFloorSettings() {
        return floorSettings;
    }


//    private static ObjectSettings createStandardLevelSettings() {
//        ObjectSettings toReturn = new ObjectSettings();
//        EnemySettings jumping = new EnemySettings();
//        toReturn.enemies = new LinkedList<>();
//
//        jumping.setJumping(true);
//        jumping.setHeight(40);
//        jumping.setDamage(1);
//        jumping.setHp(1);
//        toReturn.enemies.add(new Tupel<>(jumping, 5));
//
//        EnemySettings nonJumping = new EnemySettings();
//        nonJumping.setJumping(false);
//        nonJumping.setHeight(50);
//        nonJumping.setDamage(1);
//        nonJumping.setHp(1);
//        toReturn.enemies.add(new Tupel<>(nonJumping, 5));
//
//        EnemySettings boss = new EnemySettings();
//        boss.setJumping(false);
//        boss.setHeight(80);
//        boss.setDamage(1);
//        boss.setHp(5);
//        toReturn.enemies.add(new Tupel<>(boss, 1));
//
//        PlatformSettings moving = new PlatformSettings();
//        moving.setDelta(100, 0);
//        moving.setSpeed(1);
//        moving.setWidth(200);
//        moving.setCount(10);
//        PlatformSettings nonMoving = new PlatformSettings();
//        nonMoving.setWidth(300);
//        nonMoving.setCount(10);
//        toReturn.platformSettings = new LinkedList<PlatformSettings>();
//        toReturn.platformSettings.add(moving);
//        toReturn.platformSettings.add(nonMoving);
//
//        toReturn.goalSettings = new GoalSettings();
////        toReturn.goalSettings.setHeight(25);
//
//        toReturn.floorSettings = FloorSettings.createDefaultFloor();
//        //todo..
//        return toReturn;
//    }
//
//    private static ObjectSettings createSpaceLevelSettings() {
//        ObjectSettings toReturn = createStandardLevelSettings();
//        toReturn.platformSettings = new LinkedList<PlatformSettings>();
//        for (int i = 0; i < 10; i++) {
//            PlatformSettings moving = new PlatformSettings();
//            moving.setDelta(0, 200*Math.random()+100);
//            moving.setSpeed(Math.random()+0.5);
//            moving.setWidth(200);
//            moving.setCount(1);
//            toReturn.platformSettings.add(moving);
//        }
//
//        PlatformSettings nonMoving = new PlatformSettings();
//        nonMoving.setWidth(300);
//        nonMoving.setCount(5);
//        toReturn.platformSettings.add(nonMoving);
//        return toReturn;
//    }
//
//    private static ObjectSettings createIceLevelSettings() {
//        double iceFriction = 0.01;
//        ObjectSettings toReturn = createStandardLevelSettings();
//        PlatformSettings nonMoving = new PlatformSettings();
//        nonMoving.setWidth(300);
//        nonMoving.setCount(20);
//        nonMoving.setFriction(iceFriction);
//        toReturn.platformSettings = new LinkedList<PlatformSettings>();
//        toReturn.platformSettings.add(nonMoving);
//
//        toReturn.floorSettings = FloorSettings.createDefaultFloor();
//        toReturn.floorSettings.setFriction(iceFriction);
//        return toReturn;
//    }
}
