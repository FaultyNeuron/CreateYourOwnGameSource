package kinderuni.desktop;

import functionalJava.data.Direction1D;
import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.AnimationLogicImpl;
import kinderuni.graphics.GraphicsInfo;
import kinderuni.graphics.GraphicsObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Georg Plaz.
 */
public class DesktopFileGraphics extends DesktopGraphics {
    private Map<State, ImageIcon[]> images = new HashMap<>();
    private File path;

    public DesktopFileGraphics(File path, DoubleTupel dimensions) {
        super(dimensions);
        this.path = path;
        GraphicsInfo graphicsInfo = GraphicsInfo.createInfo(path);
        for (State state : State.values()){
            File stateFolder = new File(path, state.name().toLowerCase());
            if(stateFolder.exists()){
                List<File> files = new LinkedList<>();
                for (int i = 0;; i++) {
                    File f = new File(stateFolder, "anim"+i+"."+graphicsInfo.getFileType());
                    if(f.exists()){
                        files.add(f);
                    }else{
                        break;
                    }
                }
                if(files.size()==1){
                    addStillState(state);
                }else{
                    addState(state, new AnimationLogicImpl(graphicsInfo.getFps(), graphicsInfo.getLoopType(), files.size()));
                }
                try{
                    ImageIcon[] imageIcon = new ImageIcon[files.size()];
                    int counter = 0;
                    for(File f : files){
                        imageIcon[counter++] =  new ImageIcon(f.toURI().toURL());
                    }
                    images.put(state, imageIcon);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
//        animationLogic = new AnimationLogic(10, AnimationLogic.LoopType.START_OVER, files.size());
//        animationLogic.start();


//        try {
//            jump = new ImageIcon(new File(path, "anim3.jpg").toURI().toURL());
//            standing = new ImageIcon(new File(path, "standing.jpg").toURI().toURL());
//

    }

    @Override
    public void drawTo(Graphics drawTo, DoubleTupel center) {
        Box graphicsBounding = new FastAccessBox(center, getDimensions());
        Image image;// = imageIcon[animationLogic.getCurrentFrame()].getImage();
        ImageIcon[] imageIcons;
        if(images.containsKey(getState())){
            imageIcons = images.get(getState());
        }else{
            imageIcons = images.get(State.STANDING);
        }
        image = imageIcons[getCurrentFrame()].getImage();

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
    private ImageIcon[] getIcons(State state){
        return images.get(state);
    }

    @Override
    public GraphicsObject copy() {
        return new DesktopFileGraphics(path, getDimensions());
    }
}
