package kinderuni.settings.levelSettings.objectSettings;

import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class CollectibleSettings {
    private GraphicsSettings graphics;
    private List<EffectSettings> effects;
    private double bounciness = 0;
    private Integer effect_duration = 0;
    private double gravity_factor = 1;
    private double drop_acceleration = 6;

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }

    public List<EffectSettings> getEffects() {
        return effects;
    }

    public double getBounciness() {
        return bounciness;
    }

    public int getEffectDuration() {
        return effect_duration;
    }

    public boolean hasEffectDuration(){
        return effect_duration!=null;
    }

    public Double getGravityFactor() {
        return gravity_factor;
    }

    public double getDropAcceleration() {
        return drop_acceleration;
    }
}
