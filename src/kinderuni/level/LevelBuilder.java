package kinderuni.level;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.Enemy;
import kinderuni.gameLogic.objects.Goal;
import kinderuni.gameLogic.objects.solid.MovingPlatform;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.graphics.Screen;
import kinderuni.settings.levelSettings.*;
import kinderuni.settings.levelSettings.objectSettings.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class LevelBuilder {
    private final LevelSettings levelSettings;
    private final Screen screen;
    private final kinderuni.System system;
    private Random random;
    private Level level;
    private LevelBuilder(LevelSettings levelSettings, Screen screen, kinderuni.System system){
        this.levelSettings = levelSettings;
        this.screen = screen;
        this.system = system;
        String strSeed = levelSettings.getSeed();
        long longSeed = 0;
        for (int i=0;i<strSeed.length();i++)
        {
            longSeed+=(int)strSeed.charAt(i);
        }
        random = new Random(longSeed);
    }

    public static Level build(LevelSettings levelSettings, Screen screen, kinderuni.System system){
        LevelBuilder builder = new LevelBuilder(levelSettings, screen, system);
        return builder.buildLevel();
    }

    private Level buildLevel(){
        EnvironmentSettings environmentSettings = levelSettings.getEnvironmentSettings();
        buildEnvironment();
        buildObjects();
        return level;
    }

    private void buildEnvironment() {
        EnvironmentSettings environmentSettings = levelSettings.getEnvironmentSettings();
        level = new Level(levelSettings.getLevelName(), environmentSettings.getDimensions(),
                screen, environmentSettings.getAirFriction(),
                environmentSettings.getGravity(), environmentSettings.getPlayerInitPos());
    }

    private void buildObjects(){
        //todo do stuff
        ObjectSettings objectSettings = levelSettings.getObjectSettings();
        Box levelBox = level.getGameWorld().getBounds();

        FloorSettings floorSettings = objectSettings.getFloorSettings();
        double floor = levelBox.getLower();
        double lastEnd = levelBox.getLeft();
        while(lastEnd<levelBox.getRight()){
            double width = floorSettings.getTileWidth();
            level.getGameWorld().add(new Platform(new DoubleTupel(lastEnd + width / 2, floor), system.createTextBoxGraphics(width, 25, "floor"), floorSettings.getFriction()));
            lastEnd += width + floorSettings.getGapWidth();
        }

        for(EnemySettings enemySettings : objectSettings.getEnemySettings()){
            for(DoubleTupel tupel : createPoints(levelBox, enemySettings.getCount())) {
                level.getGameWorld().add(
                        new Enemy(
                                tupel,
                                system.createBoxGraphics(50, enemySettings.getHeight()),
                                enemySettings.getDamage(),
                                enemySettings.isJumping()));
            }
        }
        for(PlatformSettings platformSettings : objectSettings.getPlatformSettings()){
            for(DoubleTupel tupel : createPoints(level.getGameWorld().getBounds(), platformSettings.getCount())) {
                level.getGameWorld().add(
                        new MovingPlatform(
                                tupel,
                                system.createBoxGraphics(platformSettings.getWidth(), 25),
                                platformSettings.getFriction(),
                                platformSettings.getSpeed(),
                                platformSettings.getDelta()));
            }
        }

        GoalSettings goalSettings = objectSettings.getGoalSettings();
        level.set(new Goal(new DoubleTupel(levelBox.getRight() - 50, levelBox.getLower() + 100),
                system.createTextBoxGraphics(20, goalSettings.getHeight(), "goal")));
    }

    public Collection<DoubleTupel> createPoints(Box bounding, int count){
        Collection<DoubleTupel> points = new LinkedList<DoubleTupel>();
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * bounding.getWidth() + bounding.getLeft();
            double y = random.nextDouble() * bounding.getHeight() + bounding.getLower();
            points.add(new DoubleTupel(x, y));
        }
        return points;
    }
}
