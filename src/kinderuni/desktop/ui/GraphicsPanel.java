package kinderuni.desktop.ui;

import functionalJava.data.shape.box.*;
import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.desktop.DesktopPainter;
import kinderuni.ui.Info;
import kinderuni.ui.SystemComponent;
import kinderuni.ui.graphics.Paintable;
import kinderuni.ui.graphics.Painter;
import kinderuni.ui.GraphicsComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Georg Plaz.
 */
public class GraphicsPanel extends JPanel implements GraphicsComponent, SystemComponent {
    private DoubleTupel center;
    private DoubleTupel size;
    private Dimension dimensionsAwt;
    private long lastPaintTime = 0;
    private Thread renderThread;
    private Info fpsInfo;
    private List<Info> infos = new LinkedList<>();
    private List<Tupel<Paintable, Long>> paintables = new LinkedList<>();
    private boolean running;

    public GraphicsPanel(DoubleTupel size) {
        setBackground(Color.WHITE);
        fpsInfo = new Info("fps", "NA");
        infos.add(fpsInfo);
        this.center = DoubleTupel.ZEROS;
        this.size = size;
        dimensionsAwt = new Dimension((int) size.getFirst().doubleValue(), (int) size.getSecond().doubleValue());
        setFocusable(true);
        requestFocusInWindow();

    }

    public DoubleTupel getComponentDimensions(){
        return size;
    }
    public double getComponentWidth(){
        return getComponentDimensions().getFirst();
    }

    public double getComponentHeight(){
        return getComponentDimensions().getSecond();
    }

    public DoubleTupel getCenter() {
        return center;
    }

    public void setRenderCenter(DoubleTupel center) {
        this.center = center;
    }

    public Box getScreenArea(){
        return new ModifiableBox(center, size);
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

    @Override
    public DoubleTupel getCompSize() {
        return size;
    }

    @Override
    public SystemComponent getSystemComponent() {
        return this;
    }
}
