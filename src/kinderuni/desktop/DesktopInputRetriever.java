package kinderuni.desktop;

import kinderuni.desktop.ui.GraphicsPanel;
import kinderuni.ui.graphics.InputRetriever;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Georg Plaz.
 */
public class DesktopInputRetriever implements InputRetriever {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean skipLevel;
    private boolean action;
//    private boolean skipLevelPressed;
    public DesktopInputRetriever(Container jPanel){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                synchronized (GraphicsPanel.class) {
                    switch (keyEvent.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            switch (keyEvent.getKeyCode()){
                                case KeyEvent.VK_LEFT:
                                case KeyEvent.VK_A:
                                    left = true;
                                    break;
                                case KeyEvent.VK_RIGHT:
                                case KeyEvent.VK_D:
                                    right = true;
                                    break;
                                case KeyEvent.VK_SPACE:
                                case KeyEvent.VK_UP:
                                case KeyEvent.VK_W:
                                    up = true;
                                    break;
                                case KeyEvent.VK_DOWN:
                                case KeyEvent.VK_S:
                                    down = true;
                                    break;
                                case KeyEvent.VK_CONTROL:
                                    action = true;
                                    break;
                                case KeyEvent.VK_F:
                                    System.out.println("pressed f..");
//                                    skipLevelPressed = true;
                                    break;
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            switch (keyEvent.getKeyCode()){
                                case KeyEvent.VK_LEFT:
                                case KeyEvent.VK_A:
                                    left = false;
                                    break;
                                case KeyEvent.VK_RIGHT:
                                case KeyEvent.VK_D:
                                    right = false;
                                    break;
                                case KeyEvent.VK_SPACE:
                                case KeyEvent.VK_UP:
                                case KeyEvent.VK_W:
                                    up = false;
                                    break;
                                case KeyEvent.VK_DOWN:
                                case KeyEvent.VK_S:
                                    down = false;
                                    break;
                                case KeyEvent.VK_CONTROL:
                                    action = false;
                                    break;
                                case KeyEvent.VK_F:
                                    System.out.println("released f..");
                                    skipLevel = true;
                                    break;
                            }
                            break;
                    }
                    return false;
                }
            }
        });
    }
    @Override
    public boolean skipLevelAndConsume() {
        boolean toReturn = skipLevel;
        skipLevel = false;
        return toReturn;
    }

    @Override
    public boolean right() {
        return right;
    }

    @Override
    public boolean left() {
        return left;
    }

    @Override
    public boolean up() {
        return up;
    }

    @Override
    public boolean down() {
        return up;
    }

    @Override
    public boolean action() {
        return action;
    }
}
