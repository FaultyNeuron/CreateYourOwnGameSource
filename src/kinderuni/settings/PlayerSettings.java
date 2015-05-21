package kinderuni.settings;

import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;
import kinderuni.settings.levelSettings.objectSettings.LivingSettings;

/**
 * Created by Georg Plaz.
 */
public class PlayerSettings extends LivingSettings{
    public static final PlayerSettings DEFAULT = new PlayerSettings();
    static{
        DEFAULT.life=1;
        DEFAULT.enemy_throwback_power=0.;
    }

    private Double enemy_throwback_power;
    private Integer life;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public double getEnemyThrowbackPower() {
        return enemy_throwback_power;
    }

    public boolean hasEnemyThrowbackPower() {
        return enemy_throwback_power!=null;
    }

    public boolean hasLife() {
        return life!=null;
    }
}
