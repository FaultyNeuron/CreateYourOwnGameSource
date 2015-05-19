package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class EffectSettings {
    private String id;
    private int value;
    private double factor;
    private Integer effect_duration;

    public String getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public double getFactor() {
        return factor;
    }

    public int getDuration() {
        return effect_duration;
    }

    public boolean hasDuration() {
        return effect_duration!=null;
    }
}
