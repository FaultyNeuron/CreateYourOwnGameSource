package kinderuni.pictureEditor;

import java.awt.*;

/**
 * Created by markus on 26.06.15.
 */
public interface ResizableContainerCallback {
    public void releaseFocus(Point clickPoint);
    public void removeResizable(Resizable resizable);
}
