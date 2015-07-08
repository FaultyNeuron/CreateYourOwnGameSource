package kinderuni.level.builder;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.Enemy;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropBuilder;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.settings.IdParametersSettings;
import kinderuni.settings.levelSettings.objectSettings.EnemySettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class EnemyBuilder extends LivingObjectBuilder {
    private DropBuilder dropBuilder;
    public EnemyBuilder(kinderuni.System system, long seed) {
        super(system, new Random(seed));
    }

    public Enemy build(EnemySettings enemySettings) {
        return build(enemySettings, EnemySettings.DEFAULT, false);
    }

    public Enemy build(EnemySettings enemySettings, EnemySettings defaultSettings) {
        return build(enemySettings, defaultSettings, true);
    }

    public List<Enemy> build(IdParametersSettings idSettings, EnemySettings enemySettings) {
        List<Enemy> toReturn = new LinkedList<>();
        for (int i = 0; i < idSettings.getCount(); i++) {
            if(idSettings.hasEnemy()){
                toReturn.add(build(enemySettings, idSettings.getEnemy()));
            }else{
                toReturn.add(build(enemySettings));
            }
        }
        return toReturn;
    }


    public Enemy build(EnemySettings enemySettings, EnemySettings defaultSettings, boolean keepDefault) {
        int damage = enemySettings.hasDamage()?enemySettings.getDamage():defaultSettings.getDamage();
        int jumpPause = enemySettings.hasJumpPause()?enemySettings.getJumpPause():defaultSettings.getJumpPause();

        Enemy toReturn = new Enemy(damage, jumpPause);
        if (dropBuilder !=null && !dropBuilder.isEmpty()) {
            Collectible drop = dropBuilder.create();
            toReturn.setDrop(drop);
        }
        if(keepDefault){
            attach(toReturn, enemySettings, defaultSettings);
        }else{
            attach(toReturn, enemySettings);
        }
        return toReturn;
    }

    public List<Enemy> buildForPlatform(Platform platform, EnemySettings enemySettings) {
        List<Enemy> enemyList = new LinkedList<>();
        DoubleTupel center = platform.getCenter();
        DoubleTupel dim = platform.getDimensions();
        Enemy enemy = build(enemySettings);

        double yEnemy = center.getSecond() + dim.getSecond()/2 + enemy.getDimensions().getSecond()/2;
        enemy.setCenter(new DoubleTupel(center.getFirst(), yEnemy));
        enemyList.add(enemy);
        return enemyList;
    }


    public void setDropBuilder(DropBuilder dropBuilder) {
        this.dropBuilder = dropBuilder;
    }
}
