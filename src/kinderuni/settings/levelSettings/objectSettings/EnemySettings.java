package kinderuni.settings.levelSettings.objectSettings;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class EnemySettings {
    private boolean jumping;
    private int damage;
    private double height;
    private int hp;
    private GraphicsSettings graphics;
    private List<Drop> drop = new LinkedList<>();
    private double walking_speed;
    private double jump_power;
    private int jump_pause;

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public List<Drop> getDrop() {
        return drop;
    }

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }

    public double getWalkingSpeed() {
        return walking_speed;
    }

    public double getJumpPower() {
        return jump_power;
    }

    public int getJumpPause() {
        return jump_pause;
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
