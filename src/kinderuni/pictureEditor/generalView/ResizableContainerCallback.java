package kinderuni.pictureEditor.generalView;

import kinderuni.pictureEditor.ImageSnippet;

import java.awt.*;

/**
 * Created by markus on 26.06.15.
 */
public interface ResizableContainerCallback {
    public void releaseFocus(Point clickPoint);
    public void replaceImageSnippet(ImageSnippet old, Rectangle newSnippet);
}
