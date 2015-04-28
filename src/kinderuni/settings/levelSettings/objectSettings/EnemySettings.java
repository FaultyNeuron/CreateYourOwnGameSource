package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class EnemySettings {
    private boolean jumping;
    private int count;
    private int damage;
    private double height;

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
}
