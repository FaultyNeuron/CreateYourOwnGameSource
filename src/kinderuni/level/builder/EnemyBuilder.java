package kinderuni.level.builder;

import kinderuni.gameLogic.objects.Enemy;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropFactory;
import kinderuni.settings.levelSettings.objectSettings.EnemySettings;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class EnemyBuilder extends LivingObjectBuilder {
    private DropFactory dropFactory;
    public EnemyBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }

    public Enemy build(EnemySettings enemySettings) {
        return build(enemySettings, EnemySettings.EMPTY_SETTINGS);
    }

    public Enemy build(EnemySettings enemySettings, EnemySettings defaultSettings) {
        int damage = enemySettings.hasDamage()?enemySettings.getDamage():defaultSettings.getDamage();
        int jumpPause = enemySettings.hasJumpPause()?enemySettings.getJumpPause():defaultSettings.getJumpPause();

        Enemy toReturn = new Enemy(damage, jumpPause);
        if (dropFactory!=null && !dropFactory.isEmpty()) {
            Collectible drop = dropFactory.create();
            toReturn.setDrop(drop);
        }
        attach(toReturn, enemySettings, enemySettings);
        return toReturn;
    }

    public void setDropFactory(DropFactory dropFactory) {
        this.dropFactory = dropFactory;
    }
}
