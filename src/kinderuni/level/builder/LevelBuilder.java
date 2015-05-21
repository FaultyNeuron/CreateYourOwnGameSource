package kinderuni.level.builder;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.Goal;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropFactory;
import kinderuni.gameLogic.objects.collectible.effects.*;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.level.Level;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.ui.graphics.InputRetriever;
import kinderuni.settings.IdParametersSettings;
import kinderuni.settings.levelSettings.*;
import kinderuni.settings.levelSettings.objectSettings.*;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class LevelBuilder {
//    private CollectibleBuilder collectibleBuilder;
//    private CollectibleBuilder collectibleBuilder;
    private EffectBuilder effectBuilder;
    private static InputRetriever inputRetriever;
    private final double screenWidth;
    private final kinderuni.System system;

    public LevelBuilder(double screenWidth, kinderuni.System system, InputRetriever retriever) {
        this.screenWidth = screenWidth;
        this.system = system;
        this.inputRetriever = retriever;
//        collectibleBuilder = new CollectibleBuilder(system);
        effectBuilder = new EffectBuilder();
    }

    public static Level build(LevelSettings levelSettings, double screenWidth, InputRetriever inputRetriever, kinderuni.System system) {
        LevelBuilder.inputRetriever = inputRetriever;
        LevelBuilder builder = new LevelBuilder(screenWidth, system, inputRetriever);
        return builder.buildLevel(levelSettings);
    }

    public Level buildLevel(LevelSettings levelSettings) {
        String strSeed = levelSettings.getSeed();
        Random random;
        if(strSeed!=null) {
            long longSeed = 0;
            for (int i = 0; i < strSeed.length(); i++) {
                longSeed += (int) strSeed.charAt(i);
            }
            random = new Random(longSeed);
        }else{
            random = new Random();
        }
        Level level = buildEnvironment(levelSettings, random);
        buildObjects(level, levelSettings, random);
        return level;
    }

    private Level buildEnvironment(LevelSettings levelSettings, Random random) {
        Effect effect = EffectBuilder.createEffects(levelSettings.getActiveEffects());
        return new Level(levelSettings.getLevelName(), levelSettings.getDimensions(),
                screenWidth, inputRetriever,
                levelSettings.getAirFriction(), levelSettings.getGravity(),
                levelSettings.getPlayerInitPos(), system, effect);
    }

    private void buildObjects(Level level, LevelSettings levelSettings, Random random) {
        EnemyBuilder enemyBuilder = new EnemyBuilder(system, random);
        CollectibleBuilder collectibleBuilder = new CollectibleBuilder(system, random);
        PlatformBuilder platformBuilder = new PlatformBuilder(system, random);

        Box levelBox = level.getGameWorld().getBounds();

        enemyBuilder.setBoundingForRandomPlacement(levelBox);
        collectibleBuilder.setBoundingForRandomPlacement(levelBox);
        platformBuilder.setBoundingForRandomPlacement(levelBox);

        FloorSettings floorSettings = levelSettings.getFloorSettings();
        double floorY = levelBox.getLower();
        double lastEnd = levelBox.getLeft();
//        DoubleTupel bounding = new DoubleTupel(500, 25);
        while (lastEnd < levelBox.getRight()) {
            GraphicsObject graphicsObject;
            if(floorSettings.getGraphicsSettings()==null){
                graphicsObject = system.createTextBoxGraphics(500, 25, "floor");
            }else{
                graphicsObject = system.createGraphics(floorSettings.getGraphicsSettings());
            }
            double width = graphicsObject.getDimensions().getFirst();
            DoubleTupel position = new DoubleTupel(lastEnd + width / 2, floorY);
            Platform floor = platformBuilder.build(floorSettings);
            level.getGameWorld().add(floor);
            floor.setCenter(position);
            lastEnd += width + floorSettings.getGapWidth();
        }

        for (IdParametersSettings enemyId : levelSettings.getEnemies()) {
            EnemySettings enemySettings = system.getSettings().getEnemySettings(enemyId.getId());
            DropFactory dropFactory = new DropFactory(random);
            enemyBuilder.setDropFactory(dropFactory);
            for(EnemySettings.Drop drop : enemySettings.getDrop()){
                Collectible collectible = collectibleBuilder.build(system.getSettings().getCollectibleSettings(drop.getId()));
                dropFactory.addBluePrint(drop.getProbability(), collectible);
            }
            for (int i = 0; i < enemyId.getCount(); i++) {
                if(enemyId.hasEnemy()){
                    level.getGameWorld().add(enemyBuilder.build(enemySettings, enemyId.getEnemy()));
                }else{
                    level.getGameWorld().add(enemyBuilder.build(enemySettings));
                }
            }
        }
        for (IdParametersSettings platformId : levelSettings.getPlatforms()) {
            PlatformSettings platformSettings = system.getSettings().getPlatformSettings(platformId.getId());
            for (int i = 0; i < platformId.getCount(); i++) {
                if(platformId.hasPlatform()){
                    level.getGameWorld().add(platformBuilder.build(platformSettings, platformId.getPlatform()));
                }else{
                    level.getGameWorld().add(platformBuilder.build(platformSettings));
                }
            }
        }

        GoalSettings goalSettings = levelSettings.getGoalSettings();
        Goal goal = new Goal(); //todo create goal builder
        platformBuilder.attach(goal, goalSettings);
        level.set(goal);
    }







}
