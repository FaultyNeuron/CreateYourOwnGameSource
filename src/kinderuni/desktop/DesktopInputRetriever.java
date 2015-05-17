package kinderuni.desktop;

import kinderuni.graphics.InputRetriever;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Georg Plaz.
 */
public class DesktopInputRetriever implements InputRetriever {
    private boolean left;
    private boolean right;
    private boolean jump;
    private boolean skipLevel;
    public DesktopInputRetriever(JPanel jPanel){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                synchronized (GamePanel.class) {
                    switch (keyEvent.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            switch (keyEvent.getKeyCode()){
                                case KeyEvent.VK_A:
                                    left = true;
                                    break;
                                case KeyEvent.VK_D:
                                    right = true;
                                    break;
                                case KeyEvent.VK_SPACE:
                                    jump = true;
                                    break;
                                case KeyEvent.VK_F:
                                    skipLevel = true;
                                    break;
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            switch (keyEvent.getKeyCode()){
                                case KeyEvent.VK_A:
                                    left = false;
                                    break;
                                case KeyEvent.VK_D:
                                    right = false;
                                    break;
                                case KeyEvent.VK_SPACE:
                                    jump = false;
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
    public boolean goRight() {
        return right;
    }

    @Override
    public boolean goLeft() {
        return left;
    }

    @Override
    public boolean jump() {
        return jump;
    }
}
