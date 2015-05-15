package kinderuni.desktop;

import functionalJava.data.Direction1D;
import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.AnimationLogicImpl;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class DesktopFileGraphics extends DesktopGraphics {
    private ImageIcon[] imageIcon;
    private ImageIcon jump;
    private ImageIcon standing;
    public DesktopFileGraphics(File path, DoubleTupel dimensions) {
        super(dimensions);
        List<File> files = new LinkedList<>();
        for (int i = 0;; i++) {
            File f = new File(path, "anim"+i+".jpg");
            if(f.exists()){
                files.add(f);
            }else{
                break;
            }
        }
//        animationLogic = new AnimationLogic(10, AnimationLogic.LoopType.START_OVER, files.size());
//        animationLogic.start();
        addStillState(State.JUMPING);
        addStillState(State.STANDING);
        addState(State.WALKING, new AnimationLogicImpl(20, AnimationLogicImpl.LoopType.START_OVER, files.size()));
        imageIcon = new ImageIcon[files.size()];
        int counter = 0;
        try {
            jump = new ImageIcon(new File(path, "anim3.jpg").toURI().toURL());
            standing = new ImageIcon(new File(path, "standing.jpg").toURI().toURL());
            for(File f : files){
                imageIcon[counter++] =  new ImageIcon(f.toURI().toURL());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawTo(Graphics drawTo, DoubleTupel center) {
        Box graphicsBounding = new FastAccessBox(center, getDimensions());
        Image image;// = imageIcon[animationLogic.getCurrentFrame()].getImage();
        switch (getState()){
            case STANDING:
                image = standing.getImage();
                break;
            case WALKING:
                image = imageIcon[getCurrentFrame()].getImage();
                break;
            case JUMPING:
                image = jump.getImage();
                break;
//            case FLYING:
//                break;
            default:
                return;
        }

        if(!isBlinking() || blinkShow()) {
            boolean right = getDirection() == Direction1D.RIGHT;
            int width = (int)Math.round(graphicsBounding.getWidth() * (right?1:-1));
            int left = (int)Math.round(graphicsBounding.getLeft() + (right?0:-width));
            drawTo.drawImage(image,
                    left,
                    -(int) graphicsBounding.getUpper(),
                    width,
                    (int) graphicsBounding.getHeight(),
                    null);
        }
//        if(!isBlinking() || blinkShow()) {
//            drawTo.drawRect((int) boundingBox.getLeft(),
//                    -(int) boundingBox.getUpper(),
//                    (int) boundingBox.getWidth(),
//                    (int) boundingBox.getHeight());
//
//        }
    }
}
