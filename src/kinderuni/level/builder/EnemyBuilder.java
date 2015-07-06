package kinderuni.level.builder;

import kinderuni.gameLogic.objects.Enemy;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropBuilder;
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
    public EnemyBuilder(kinderuni.System system, Random random) {
        super(system, random);
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

    public void setDropBuilder(DropBuilder dropBuilder) {
        this.dropBuilder = dropBuilder;
    }
}
