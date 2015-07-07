package kinderuni.settings.levelSettings.objectSettings;


/**
 * Created by Georg Plaz.
 */
public class GraphicsSettings extends DimensionsSettings{
    private String id;
    private int[] bg_colour;
    private int[] line_colour;
    private String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int[] getBgColour() {
        return bg_colour;
    }

    public int[] getLine_colour() {
        return line_colour;
    }
}
