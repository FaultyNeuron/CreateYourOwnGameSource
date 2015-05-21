package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class DimensionsSettings {
    private Double width;
    private Double height;

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public boolean hasDimensions(){
        return width!=null && height!=null;
    }

    public DoubleTupel getDimensions() {
        return new DoubleTupel(width, height);
    }
}
