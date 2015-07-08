package kinderuni.pictureEditor.detailView;

import kinderuni.ui.graphics.AnimationLogic;
import kinderuni.ui.graphics.AnimationLogicImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class AnimationPreviewPanel extends JComponent{
    AnimationLogic animationLogic;
    private BufferedImage[] images;
    private AnimationLogic.LoopType loopType = AnimationLogic.LoopType.START_OVER;
    private double fps = 10;


    public AnimationPreviewPanel(BufferedImage[] images){
        setPreferredSize(new Dimension(200,200));
        this.images = images;
        create();
        Thread animationThread = new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        sleep(Math.round(1000/fps));
                    } catch (InterruptedException e) {
                        break;
                    }
                    repaint();
                }
            }
        };
        animationThread.start();
    }

    public void setFps(double fps){
        this.fps = fps;
        create();
    }

    public void setLoopType(AnimationLogic.LoopType type){
        this.loopType = type;
        create();
    }

    private void create(){
        animationLogic = new AnimationLogicImpl(fps, loopType, images.length);
        animationLogic.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(images[animationLogic.getCurrentFrame()], 0, 0, 100, 100, null);
    }

    public double getFps() {
        return fps;
    }

    public AnimationLogic.LoopType getLoopType() {
        return loopType;
    }
}
