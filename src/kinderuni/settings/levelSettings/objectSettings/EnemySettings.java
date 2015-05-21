package kinderuni.settings.levelSettings.objectSettings;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class EnemySettings extends LivingSettings {
    public static final EnemySettings EMPTY_SETTINGS = new EnemySettings();

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
