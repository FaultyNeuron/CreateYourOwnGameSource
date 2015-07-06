package kinderuni.pictureEditor;

/**
 * Created by markus on 25.06.15.
 */
public class Globals {
    private static String lastFileChooserPath = null;

    public static synchronized String getLastFileChooserPath() {
        return lastFileChooserPath;
    }

    public static synchronized void setLastFileChooserPath(String path) {
        lastFileChooserPath = path;
    }
}