package kinderuni.desktop;

import functionalJava.data.shape.box.*;
import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Paintable;
import kinderuni.graphics.Painter;
import kinderuni.graphics.Screen;
import kinderuni.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Georg Plaz.
 */
public class GamePanel extends JPanel implements Screen{
    private DoubleTupel center;
    private DoubleTupel dimensions;
    private Dimension dimensionsAwt;
    private long lastPaintTime = 0;
    private Thread renderThread;
    private Info fpsInfo;
    private List<Info> infos = new LinkedList<>();
    private List<Tupel<Paintable, Long>> paintables = new LinkedList<>();
    private boolean running;



    public GamePanel(DoubleTupel dimensions) {
        setBackground(Color.WHITE);
        fpsInfo = new Info("fps", "NA");
        infos.add(fpsInfo);
        this.center = DoubleTupel.ZEROS;
        this.dimensions = dimensions;
        dimensionsAwt = new Dimension((int) dimensions.getFirst().doubleValue(), (int) dimensions.getSecond().doubleValue());
        setFocusable(true);
        requestFocusInWindow();

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
    public void paint(Graphics g) {
        super.paint(g);
        Painter painter = new DesktopPainter(g, this);
        int cursorDelta = 15;
        int cursorHeight = getHeight() + cursorDelta/2;
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastPaintTime;
        if (delta > 0) {
            fpsInfo.setValue(String.valueOf(1000/delta));
        }
        for(Info info : infos){
            paintInfo(g, info, cursorHeight-=cursorDelta);
        }
        for(Tupel<Paintable, Long> tupel : paintables){
            Paintable paintable = tupel.getFirst();
            long time = tupel.getSecond();
            if(paintable.getTime() > time) {
                paintable.paint(painter);
                for (Info info : paintable.getInfos()) {
                    paintInfo(g, info, cursorHeight -= cursorDelta);
                }
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
                long delta = 1000/60;
                long lastTime;
                while(running){
                    lastTime = System.currentTimeMillis();
                    render();
                    long sleepFor = delta - (System.currentTimeMillis()-lastTime);
                    if(sleepFor>0){
                        try {
                            sleep(sleepFor);
                        } catch (InterruptedException e) {
                            running = false;
                        }
                    }
                }
            }
        };
        renderThread.start();
    }

    @Override
    public boolean add(Paintable paintable) {
        return paintables.add(new Tupel<Paintable, Long>(paintable, paintable.getTime()));
    }

    @Override
    public boolean remove(Paintable paintable) {
        Iterator<Tupel<Paintable, Long>> iterator = paintables.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getFirst()  == paintable){
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void stop(){
        running = false;
        renderThread.interrupt();
    }
}
