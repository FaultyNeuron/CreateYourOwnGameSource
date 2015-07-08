package kinderuni.pictureEditor.detailView;

import kinderuni.ui.graphics.AnimationLogic;

import javax.swing.*;
import javax.xml.bind.JAXBPermission;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class AnimationSettingsPanel extends JPanel {
    JScrollBar jScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 1, 10, 1, 300);
    JLabel fpsDisplay;
    AnimationPreviewPanel animationPreviewPanel;
    JButton loopTypeButton = new JButton();
    public AnimationSettingsPanel(List<BufferedImage> images){
        this(images.toArray(new BufferedImage[images.size()]));
    }

    public AnimationSettingsPanel(BufferedImage[] images){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(jScrollBar);
        animationPreviewPanel = new AnimationPreviewPanel(images);
        double fps = animationPreviewPanel.getFps();
        fpsDisplay = new JLabel("fps: "+fps);
        add(fpsDisplay);
        add(animationPreviewPanel);
        add(loopTypeButton);
        loopTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnimationLogic.LoopType loopType = animationPreviewPanel.getLoopType();
                AnimationLogic.LoopType otherType = loopType== AnimationLogic.LoopType.START_OVER? AnimationLogic.LoopType.BACK_AND_FORTH: AnimationLogic.LoopType.START_OVER;
                animationPreviewPanel.setLoopType(otherType);
                loopTypeButton.setText("change type to: "+loopType.toString());
            }
        });
        jScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                animationPreviewPanel.setFps(e.getValue()/10.);
                fpsDisplay.setText("fps: "+animationPreviewPanel.getFps());
            }
        });
        jScrollBar.setValue((int) Math.round(fps*10));

    }
}
