package kinderuni.graphics;

import kinderuni.gameLogic.objects.GameObject;

/**
 * Created by Georg Plaz.
 */
public interface Painter {
    public void paint(GameObject abstractGameObject);
    public Screen getRenderScreen();
}
