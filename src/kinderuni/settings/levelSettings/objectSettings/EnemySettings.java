package kinderuni.settings.levelSettings.objectSettings;

import kinderuni.gameLogic.objects.collectible.effects.Effect;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class EnemySettings extends LivingSettings {
    public static final EnemySettings DEFAULT = new EnemySettings();

    static{
        DEFAULT.damage = 1;
        DEFAULT.jump_pause = 0;
    }

    private Integer damage;
    private List<Drop> drop = new LinkedList<>();
    private Integer jump_pause;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public List<Drop> getDrop() {
        return drop;
    }

    public int getJumpPause() {
        return jump_pause;
    }

    public boolean hasDamage() {
        return damage!=null;
    }

    public boolean hasJumpPause() {
        return jump_pause!=null;
    }

    public class Drop{
        private String id;
        private double probability;

        public String getId() {
            return id;
        }

        public double getProbability() {
            return probability;
        }
    }
}
