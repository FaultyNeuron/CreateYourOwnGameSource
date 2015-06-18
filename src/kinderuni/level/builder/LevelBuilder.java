package kinderuni.level.builder;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.Goal;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropBuilder;
import kinderuni.gameLogic.objects.collectible.effects.*;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.level.Level;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.ui.graphics.InputRetriever;
import kinderuni.settings.IdParametersSettings;
import kinderuni.settings.levelSettings.*;
import kinderuni.settings.levelSettings.objectSettings.*;

import java.net.IDN;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class LevelBuilder {
//    private CollectibleBuilder collectibleBuilder;
//    private CollectibleBuilder collectibleBuilder;
    private static InputRetriever inputRetriever;
    private final double screenWidth;
    private final kinderuni.System system;

    public LevelBuilder(double screenWidth, kinderuni.System system, InputRetriever retriever) {
        this.screenWidth = screenWidth;
        this.system = system;
        this.inputRetriever = retriever;
//        collectibleBuilder = new CollectibleBuilder(system);
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
        EffectBuilder effectBuilder = new EffectBuilder(system, random);
        Effect effect = effectBuilder.buildEffects(levelSettings.getActiveEffects());
        return new Level(levelSettings.getLevelName(), levelSettings.getDimensions(),
                screenWidth, inputRetriever,
                levelSettings.getAirFriction(), levelSettings.getGravity(),
                levelSettings.getPlayerInitPos(), system, effect);
    }

    private void buildObjects(Level level, LevelSettings levelSettings, Random random) {
        PlatformBuilder platformBuilder = new PlatformBuilder(system, random);
        //todo: modify PlatformBuilder, so the Platforms get placed nicely.
        //todo: in order to do that, add methods and fields to PlatformBuilder as needed and call them here
        EnemyBuilder enemyBuilder = new EnemyBuilder(system, random);
        //todo: do the same for enemies. we dont want enemies to spawn inside of the user or have them
        //todo: falling onto the user at the beginning of the level
        CollectibleBuilder collectibleBuilder = new CollectibleBuilder(system, random);
        //todo: if we want to place collectibles at the beginning of the game, we have to add logic to this builder as well
        //todo: and call the code somewhere below..

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

        for (IdParametersSettings idSettings : levelSettings.getEnemies()) {
            if(!system.getSettings().hasEnemySettings(idSettings.getId())){
                throw new IdNotFoundException("enemy", idSettings.getId());
            }
            EnemySettings enemySettings = system.getSettings().getEnemySettings(idSettings.getId());
            DropBuilder dropBuilder = new DropBuilder(system, random);
            enemyBuilder.setDropBuilder(dropBuilder);
            for(EnemySettings.Drop drop : enemySettings.getDrop()){
//                Collectible collectible = collectibleBuilder.build();
                dropBuilder.addBluePrint(drop.getProbability(), system.getSettings().getCollectibleSettings(drop.getId()));
            }
            for (int i = 0; i < idSettings.getCount(); i++) {
                if(idSettings.hasEnemy()){
                    level.getGameWorld().add(enemyBuilder.build(enemySettings, idSettings.getEnemy()));
                }else{
                    level.getGameWorld().add(enemyBuilder.build(enemySettings));
                }
            }
        }
        for (IdParametersSettings idSettings : levelSettings.getPlatforms()) {
            if(!system.getSettings().hasPlatformSettings(idSettings.getId())){
                throw new IdNotFoundException("platform", idSettings.getId());
            }
            PlatformSettings platformSettings = system.getSettings().getPlatformSettings(idSettings.getId());
            for (int i = 0; i < idSettings.getCount(); i++) {
                PlatformSettings p = idSettings.getPlatform();
                Platform newPlatform;
                if(idSettings.hasPlatform()){
                    newPlatform = platformBuilder.build(platformSettings, idSettings.getPlatform());
                }else{
                    newPlatform = platformBuilder.build(platformSettings);
                }
                level.getGameWorld().add(newPlatform);
            }
        }

        for (Platform newPlatform : platformBuilder.buildAll(levelSettings, )) {
                level.getGameWorld().add(newPlatform);
            }

        GoalSettings goalSettings = levelSettings.getGoalSettings();
        Goal goal = new Goal(); //todo create goal builder
        platformBuilder.attach(goal, goalSettings);
        level.set(goal);
    }

    public class IdNotFoundException extends RuntimeException{
        public IdNotFoundException(String type, String id){
            super("no "+type+" with id \""+id+"\" was found!");
        }
    }


}
