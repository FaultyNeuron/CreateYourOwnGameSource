package kinderuni.settings.levelSettings.objectSettings;

import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class CollectibleSettings extends PhysicsObjectSettings{
    public static final CollectibleSettings DEFAULT = new CollectibleSettings();
    static{
        DEFAULT.effect_duration = 0;
        DEFAULT.drop_acceleration = 6.;
    }

    private List<EffectSettings> effects;

    private Integer effect_duration;

    private Double drop_acceleration;

    public List<EffectSettings> getEffects() {
        return effects;
    }

    public int getEffectDuration() {
        return effect_duration;
    }

    public boolean hasEffectDuration(){
        return effect_duration!=null;
    }

    public double getDropAcceleration() {
        return drop_acceleration;
    }

    public boolean hasDropAcceleration() {
        return drop_acceleration!=null;
    }

    public void setEffects(List<EffectSettings> effects) {
        this.effects = effects;
    }

    public boolean hasEffects() {
        return effects!=null && effects.size()>0;
    }
}
