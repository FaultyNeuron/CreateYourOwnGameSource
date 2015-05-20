package kinderuni.level;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.Enemy;
import kinderuni.gameLogic.objects.Goal;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropFactory;
import kinderuni.gameLogic.objects.collectible.effects.*;
import kinderuni.gameLogic.objects.solid.MovingPlatform;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.ui.graphics.InputRetriever;
import kinderuni.settings.IdCountSettings;
import kinderuni.settings.levelSettings.*;
import kinderuni.settings.levelSettings.objectSettings.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class LevelBuilder {
    private static InputRetriever inputRetriever;
//    private final LevelSettings levelSettings;
    private final double screenWidth;
    private final kinderuni.System system;

    public LevelBuilder(double screenWidth, kinderuni.System system, InputRetriever retriever) {
//        this.levelSettings = levelSettings;
        this.screenWidth = screenWidth;
        this.system = system;
        this.inputRetriever = retriever;
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
//        EnvironmentSettings environmentSettings = levelSettings.getEnvironmentSettings();
        return new Level(levelSettings.getLevelName(), levelSettings.getDimensions(),
                screenWidth, inputRetriever,
                levelSettings.getAirFriction(), levelSettings.getGravity(),
                levelSettings.getPlayerInitPos(), system);
    }

    private void buildObjects(Level level, LevelSettings levelSettings, Random random) {
        //todo do stuff
//        ObjectSettings objectSettings = levelSettings.getObjectSettings();
        Box levelBox = level.getGameWorld().getBounds();

        FloorSettings floorSettings = levelSettings.getFloorSettings();
        double floor = levelBox.getLower();
        double lastEnd = levelBox.getLeft();
        while (lastEnd < levelBox.getRight()) {
            GraphicsObject graphicsObject;
            if(floorSettings.getGraphicsSettings()==null){
                graphicsObject = system.createTextBoxGraphics(500, 25, "floor");
            }else{
                graphicsObject = system.createGraphics(floorSettings.getGraphicsSettings());
            }
            double width = graphicsObject.getDimensions().getFirst();
            level.getGameWorld().add(new Platform(new DoubleTupel(lastEnd + width / 2, floor), graphicsObject, floorSettings.getFriction()));
            lastEnd += width + floorSettings.getGapWidth();
        }

        for (IdCountSettings enemyId : levelSettings.getEnemies()) {
            EnemySettings enemySettings = system.getSettings().getEnemySettings(enemyId.getId());
            DropFactory dropFactory = new DropFactory(random);
            GraphicsSettings graphicsSettings = enemyId.hasGraphicsSettings()?enemyId.getGraphicsSettings():enemySettings.getGraphicsSettings();
            for(EnemySettings.Drop drop : enemySettings.getDrop()){
                Collectible c = buildCollectible(system.getSettings().getCollectibleSettings(drop.getId()));
                dropFactory.addBluePrint(drop.getProbability(), c);
            }
            for (DoubleTupel tupel : createPoints(levelBox, enemyId.getCount(), random)) {
                Enemy enemy = buildEnemy(enemySettings, tupel, dropFactory, graphicsSettings);
                level.getGameWorld().add(enemy);
            }
        }
        for (IdCountSettings platformId : levelSettings.getPlatforms()) {
            PlatformSettings platformSettings = system.getSettings().getPlatformSettings(platformId.getId());
            GraphicsSettings graphicsSettings = platformId.hasGraphicsSettings()?platformId.getGraphicsSettings():platformSettings.getGraphicsSettings();
            for (DoubleTupel tupel : createPoints(level.getGameWorld().getBounds(), platformId.getCount(), random)) {
//                System.out.println("system: "+system);
//                System.out.println("platformSettings: "+platformSettings);
                level.getGameWorld().add(createMovingPlatform(tupel, platformSettings, graphicsSettings));

            }
        }

        GoalSettings goalSettings = levelSettings.getGoalSettings();
        level.set(new Goal(goalSettings.getPosition(), system.createGraphics(goalSettings.getGraphicsSettings())));
    }

    public MovingPlatform createMovingPlatform(DoubleTupel position, PlatformSettings platformSettings, GraphicsSettings graphicsSettings){
        return new MovingPlatform(
                position,
                system.createGraphics(graphicsSettings),
                platformSettings.getFriction(),
                platformSettings.getSpeed(),
                platformSettings.getDelta());
    }

    public Collection<DoubleTupel> createPoints(Box bounding, int count, Random random) {
        Collection<DoubleTupel> points = new LinkedList<DoubleTupel>();
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * bounding.getWidth() + bounding.getLeft();
            double y = random.nextDouble() * bounding.getHeight() + bounding.getLower();
            points.add(new DoubleTupel(x, y));
        }
        return points;
    }

    private Enemy buildEnemy(EnemySettings enemySettings, DoubleTupel position, DropFactory dropFactory, GraphicsSettings graphicsSettings) {
        Enemy toReturn = new Enemy(
                position, system.createGraphics(graphicsSettings),
                enemySettings.getHp(), enemySettings.getDamage(),
                enemySettings.getWalkingSpeed(), enemySettings.getJumpPower(), enemySettings.getJumpPause());
        if (!dropFactory.isEmpty()) {
            Collectible drop = dropFactory.create();
            toReturn.setDrop(drop);
        }
        return toReturn;
    }

    private Collectible buildCollectible(CollectibleSettings collectibleSettings) {
        return buildCollectible(collectibleSettings, DoubleTupel.ZEROS);
    }

    private Collectible buildCollectible(CollectibleSettings collectibleSettings, DoubleTupel position) {
        Effect effect = createEffects(collectibleSettings.getEffects(), collectibleSettings.getEffectDuration());
        if(effect!=null){
            return new Collectible(system.createGraphics(collectibleSettings.getGraphicsSettings()),
                    position, effect,
                    collectibleSettings.getGravityFactor(), collectibleSettings.getDropAcceleration(), collectibleSettings.getBounciness());
        }else{
            return new Collectible(system.createGraphics(collectibleSettings.getGraphicsSettings()),
                    position,
                    collectibleSettings.getGravityFactor(), collectibleSettings.getDropAcceleration(), collectibleSettings.getBounciness());
        }
    }

    public Effect createEffects(List<EffectSettings> effectSettings, Integer collectionDuration){
        if(effectSettings.size() > 1) {
            List<Effect> effects = new LinkedList<>();
            for (EffectSettings effectSetting : effectSettings) {
                effects.add(createEffect(effectSetting, collectionDuration));
            }
            return new CombinedEffect(effects);
        }else if(effectSettings.size()==1){
            return createEffect(effectSettings.get(0), collectionDuration);
        }else{
            return null;
        }
    }

    public Effect createEffect(EffectSettings effectSettings, Integer collectionDuration){
        Effect effect;
        switch (effectSettings.getId()){
            case PlusHp.ID:
                effect = new PlusHp(effectSettings.getValue());
                break;
            case PlusLife.ID:
                effect = new PlusLife(effectSettings.getValue());
                break;
            case PlusCoins.ID:
                effect = new PlusCoins(effectSettings.getValue());
                break;
            default:
                effect = createReversibleEffect(effectSettings, collectionDuration);
                break;
        }
        return effect;
    }

    public ReversibleEffect createReversibleEffect(EffectSettings effectSettings, Integer collectionDuration){
        ReversibleEffect effect;
        switch (effectSettings.getId()){
            case SpeedPower.ID:
                effect = new SpeedPower(effectSettings.getFactor());
                break;
            case InvinciblePower.ID:
                effect = new InvinciblePower();
                break;
            case GravityChangePower.ID:
                effect = new GravityChangePower(effectSettings.getFactor());
                break;
            default:
                throw new RuntimeException("no effect with id \""+effectSettings.getId()+"\" exists!");
        }
        if(effectSettings.hasDuration()){
            return new TimeBasedEffect(effectSettings.getDuration(), effect);
        }else if(collectionDuration!=null) {
            return new TimeBasedEffect(collectionDuration, effect);
        }else{
            return effect;
        }
    }
}
