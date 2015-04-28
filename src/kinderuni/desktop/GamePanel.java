package kinderuni.desktop;

import functionalJava.data.shape.box.*;
import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.Painter;
import kinderuni.graphics.Screen;
import kinderuni.gameLogic.GameWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

//import javax.swing.*;
//import java.awt.*;


/**
 * Created by Georg Plaz.
 */
public class GamePanel extends JPanel implements Screen {
    private DoubleTupel center;
    private DoubleTupel dimensions;
    private Dimension dimensionsAwt;
    private GameWorld gameWorld;
    private long lastPaintTime = 0;

    private int lives;
    private int hp;

    private boolean left;
    private boolean right;
    private boolean jump;

    public GamePanel(DoubleTupel dimensions) {
        this.center = DoubleTupel.ZEROS;
        this.dimensions = dimensions;
        dimensionsAwt = new Dimension((int) dimensions.getFirst().doubleValue(), (int) dimensions.getSecond().doubleValue());
        setFocusable(true);
        requestFocusInWindow();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (GamePanel.class) {
                    switch (ke.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            switch (ke.getKeyCode()){
                                case KeyEvent.VK_A:
                                    left = true;
                                    break;
                                case KeyEvent.VK_D:
                                    right = true;
                                    break;
                                case KeyEvent.VK_SPACE:
                                    jump = true;
                                    break;
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            switch (ke.getKeyCode()){
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

    public DoubleTupel getScreenDimensions(){
        return dimensions;
    }
    public double getScreenWidth(){
        return getScreenDimensions().getFirst();
    }

    public double getScreenHeight(){
        return getScreenDimensions().getSecond();
    }

    public DoubleTupel getCenter() {
        return center;
    }

    public void setCenter(DoubleTupel center) {
        this.center = center;
    }

    public Box getScreenArea(){
        return new ModifiableBox(center, dimensions);
    }

    public void render(){
        repaint();
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Painter painter = new DesktopPainter(g, this);
        if(gameWorld!=null){
            gameWorld.paint(painter);
        }
        long currentTime = System.currentTimeMillis();
        int cursorDelta = 15;
        int cursorHeight = getHeight() - cursorDelta/2;
        g.drawString("fps:   "+(1000/(currentTime - lastPaintTime)), cursorDelta, cursorHeight);
        g.drawString("lives: "+(lives), cursorDelta, cursorHeight-=cursorDelta);
        g.drawString("hp:    "+(hp), cursorDelta, cursorHeight-=cursorDelta);
        lastPaintTime = currentTime;
    }

    @Override
    public Dimension getPreferredSize() {
        return dimensionsAwt;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
