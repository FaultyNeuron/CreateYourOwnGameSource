package kinderuni.settings.levelSettings.objectSettings;

import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class CollectibleSettings extends PhysicsObjectSettings{
    private List<EffectSettings> effects;

    private Integer effect_duration = 0;

    private double drop_acceleration = 6;

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
}
