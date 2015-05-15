package kinderuni.desktop;

import functionalJava.data.shape.box.*;
import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Paintable;
import kinderuni.graphics.Painter;
import kinderuni.graphics.Screen;
import kinderuni.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Georg Plaz.
 */
public class GamePanel extends JPanel implements Screen, InputRetriever {
    private DoubleTupel center;
    private DoubleTupel dimensions;
    private Dimension dimensionsAwt;
    private long lastPaintTime = 0;
    private Thread renderThread;
    private Info fpsInfo;
    private List<Info> infos = new LinkedList<>();
    private List<Paintable> paintables = new LinkedList<>();
    private boolean running;

    private boolean left;
    private boolean right;
    private boolean jump;
    private boolean skipLevel;

    public GamePanel(DoubleTupel dimensions) {
        setBackground(Color.WHITE);
        fpsInfo = new Info("fps", "NA");
        infos.add(fpsInfo);
        this.center = DoubleTupel.ZEROS;
        this.dimensions = dimensions;
        dimensionsAwt = new Dimension((int) dimensions.getFirst().doubleValue(), (int) dimensions.getSecond().doubleValue());
        setFocusable(true);
        requestFocusInWindow();
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
                                    System.out.println("pressed f!");
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Painter painter = new DesktopPainter(g, this);
        int cursorDelta = 15;
        int cursorHeight = getHeight() + cursorDelta/2;
        long currentTime = System.currentTimeMillis();
        fpsInfo.setValue(String.valueOf(1000/(currentTime - lastPaintTime)));
        for(Info info : infos){
            paintInfo(g, info, cursorHeight-=cursorDelta);
        }
        for(Paintable paintable : paintables){
            paintable.paint(painter);
            for(Info info : paintable.getInfos()){
                paintInfo(g, info, cursorHeight-=cursorDelta);
            }
        }

        lastPaintTime = currentTime;
    }

    private void paintInfo(Graphics g, Info info, int cursorHeight){
        int valueDelta = 35;
        int cursorDelta = 15;
        g.drawString(info.getKey(), cursorDelta, cursorHeight);
        g.drawString(info.getValue(), cursorDelta + valueDelta, cursorHeight);
    }

    @Override
    public Dimension getPreferredSize() {
        return dimensionsAwt;
    }

    @Override
    public void start() {
        running = true;
        renderThread = new Thread(){
            @Override
            public void run() {
                while(running){
                    render();
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        renderThread.start();
    }

    @Override
    public boolean add(Paintable paintable) {
        return paintables.add(paintable);
    }

    @Override
    public boolean remove(Paintable paintable) {
        return paintables.remove(paintable);
    }

    public void stop(){
        running = false;
        renderThread.interrupt();
    }
}
