package kinderuni.pictureEditor.detailView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by markus on 08.07.15.
 */
public class SaveKeyListener implements KeyListener {
    private static SaveKeyListener instance;

    private SaveKeyListener() {}

    public static SaveKeyListener getInstance() {
        if (instance == null) {
            instance = new SaveKeyListener();
        }
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.err.println("Saving .......................");
        if (keyEvent.isControlDown() && (keyEvent.getKeyCode() == KeyEvent.VK_S)) {
            System.err.println("Saving.");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
