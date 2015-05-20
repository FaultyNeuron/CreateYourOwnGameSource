package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class GraphicsSettings {
    private String id;
    private double width;
    private double height;
    private int[] bg_colour;
    private int[] line_colour;
    private String text;

    public String getId() {
        return id;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
