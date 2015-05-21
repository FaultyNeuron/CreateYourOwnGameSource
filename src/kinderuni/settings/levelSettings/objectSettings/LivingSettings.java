package kinderuni.settings.levelSettings.objectSettings;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class LivingSettings extends PhysicsObjectSettings {
    public static final LivingSettings DEFAULT = new LivingSettings();
    static{
        DEFAULT.flying_speed=0.;
        DEFAULT.walking_speed=0.;
        DEFAULT.jump_power=0.;
        DEFAULT.hp=1;
        DEFAULT.active_effects = new LinkedList<>();
    }

    private Integer hp;
    private Double walking_speed;
    private Double flying_speed;
    private Double jump_power;
    private List<EffectSettings> active_effects;


    public int getHp() {
        return hp;
    }

    public boolean hasHp() {
        return hp!=null;
    }

    public double getWalkingSpeed() {
        return walking_speed;
    }

    public void setWalkingSpeed(double walkingSpeed) {
        this.walking_speed = walkingSpeed;
    }

    public boolean hasWalkingSpeed() {
        return walking_speed != null;
    }

    public double getJumpPower() {
        return jump_power;
    }

    public void setJumpPower(double jumpPower) {
        this.jump_power = jumpPower;
    }

    public boolean hasJumpPower() {
        return jump_power !=null;
    }

    public double getFlyingSpeed() {
        return flying_speed;
    }

    public boolean hasFlyingSpeed() {
        return flying_speed!=null;
    }

    public List<EffectSettings> getActiveEffects() {
        return active_effects;
    }

    public void setActiveEffects(List<EffectSettings> active_effects) {
        this.active_effects = active_effects;
    }

    public boolean hasActiveEffects(){
        return active_effects!=null;
    }
}
