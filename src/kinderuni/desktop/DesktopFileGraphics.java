package kinderuni.desktop;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.desktop.ui.Util;
import kinderuni.ui.graphics.AnimationLogicImpl;
import kinderuni.ui.graphics.GraphicsInfo;
import kinderuni.ui.graphics.GraphicsObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Georg Plaz.
 */
public class DesktopFileGraphics extends DesktopGraphics {
    private Map<State, BufferedImage[]> images;
    private File path;
    private int[] bgColour;
    private int[] lineColour;
    private String text;

    public DesktopFileGraphics(File path, DoubleTupel dimensions) {
        this(path, null, null, null, dimensions);
    }

    public DesktopFileGraphics(int[] bgColour, int[] lineColour, DoubleTupel dimensions) {
        this(null, bgColour, lineColour, null, dimensions);
    }

    public DesktopFileGraphics(String text, DoubleTupel dimensions) {
        this(null, null, new int[]{0,0,0}, text, dimensions);
    }

    public DesktopFileGraphics(File path, int[] bgColour, int[] lineColour, String text, DoubleTupel dimensions) {
        super(dimensions);
        this.path = path;
        this.bgColour = bgColour;
        this.lineColour = lineColour;
        this.text = text;
        if(path!=null) {
            images = new HashMap<>();
            GraphicsInfo graphicsInfo = GraphicsInfo.createInfo(path);
            for (State state : State.values()) {
                File stateFolder =  new File(path, state.name().toLowerCase());
                if (stateFolder.exists()) {
                    List<File> files = new LinkedList<>();
                    for (int i = 0; ; i++) {
                        File f = new File(stateFolder, "anim" + i + "." + graphicsInfo.getFileType());
                        if (f.exists()) {
                            files.add(f);
                        } else {
                            break;
                        }
                    }
                    if (files.size() == 1) {
                        addStillState(state);
                    } else {
                        addState(state, new AnimationLogicImpl(graphicsInfo.getFps(), graphicsInfo.getLoopType(), files.size()));
                    }
                    try {
                        BufferedImage[] imageIcon = new BufferedImage[files.size()];
                        int counter = 0;
                        for (File f : files) {
                            imageIcon[counter++] = ImageIO.read(f);
                        }
                        images.put(state, imageIcon);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void drawTo(Graphics drawTo, DoubleTupel center) {
        Box graphicsBounding = new FastAccessBox(center, getDimensions());
        if(!isBlinking() || blinkShow()) {
            Box boundingBox = new FastAccessBox(center, getDimensions());
            if(bgColour!=null) {
                drawTo.setColor(Util.toColor(bgColour));
                drawTo.fillRect((int) boundingBox.getLeft(),
                        -(int) boundingBox.getUpper(),
                        (int) boundingBox.getWidth(),
                        (int) boundingBox.getHeight());
                drawTo.setColor(Color.BLACK);
            }
            if(images!=null) {
                Image image;// = imageIcon[animationLogic.getCurrentFrame()].getImage();
                BufferedImage[] imageIcons;
                if(images.containsKey(getState())){
                    imageIcons = images.get(getState());
                    image = imageIcons[getCurrentFrame()];
                }else{
                    imageIcons = images.get(State.IDLE);
                    image = imageIcons[getCurrentFrame(State.IDLE)];
                }
                boolean right = getDirection() == HorizontalDirection.RIGHT;
                int width = (int) Math.round(graphicsBounding.getWidth() * (right ? 1 : -1));
                int left = (int) Math.round(graphicsBounding.getLeft() + (right ? 0 : -width));
                drawTo.drawImage(image, left,
                        -(int) graphicsBounding.getUpper(), width,
                        (int) graphicsBounding.getHeight(),
                        null);
            }
            if(lineColour!=null){
                drawTo.setColor(Util.toColor(lineColour));
                drawTo.drawRect((int) boundingBox.getLeft(),
                        -(int) boundingBox.getUpper(),
                        (int) boundingBox.getWidth(),
                        (int) boundingBox.getHeight());
                drawTo.setColor(Color.BLACK);
            }
            if (text != null) {
                drawTo.drawString(text, (int) boundingBox.getLeft() + 2, -(int) boundingBox.getUpper() + 13);
            }
        }
    }
    private BufferedImage[] getIcons(State state){
        return images.get(state);
    }

    @Override
    public GraphicsObject copy() {
        return new DesktopFileGraphics(path, getDimensions());
    }
}
