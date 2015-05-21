package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class EffectSettings {
    public static final EffectSettings DEFAULT = new EffectSettings();
    static{
        DEFAULT.value = 0;
        DEFAULT.factor = 0.;
        DEFAULT.effect_duration = 0;
    }
    private String id;
    private Integer value;
    private Double factor;
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

    public boolean hasFactor() {
        return factor!=null;
    }

    public boolean hasValue() {
        return value!=null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
