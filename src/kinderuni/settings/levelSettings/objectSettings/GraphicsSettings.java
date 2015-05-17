package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class GraphicsSettings {
    private String id;
    private double width;
    private double height;
    private boolean draw_box = false;
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

    public boolean drawBox() {
        return draw_box;
    }
}
