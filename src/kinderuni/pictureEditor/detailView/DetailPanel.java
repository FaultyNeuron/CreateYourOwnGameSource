package kinderuni.pictureEditor.detailView;

import kinderuni.pictureEditor.ImageSnippet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by markus on 26.06.15.
 */
public class DetailPanel extends JPanel implements DetailPanelCallback {
    private AnimationTablePanel animationTablePanel;
    private FrameSettingsPanel frameSettingsPanel;
    private PreviewSettingsPanel previewSettingsPanel;
    private ArrayList<ImageSnippet> imageSnippets;

    public DetailPanel (ArrayList<ImageSnippet> imageSnippets) {
        this.imageSnippets = imageSnippets;

//        this.setLayout(new SpringLayout());
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLUE);

        this.animationTablePanel = new AnimationTablePanel(imageSnippets);
        this.animationTablePanel.setSize(new Dimension((int)this.animationTablePanel.getSize().getWidth(), (int)this.animationTablePanel.getSize().getHeight()+200));
        this.animationTablePanel.setDetailPanelCallback(this);

        this.frameSettingsPanel = new FrameSettingsPanel(imageSnippets);

        this.previewSettingsPanel = new PreviewSettingsPanel(imageSnippets);
        previewSettingsPanel.setBackground(Color.PINK);

        JPanel container = new JPanel(new BoxLayout(this, this.getWidth()));
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(frameSettingsPanel);
        verticalBox.add(previewSettingsPanel);

        this.add(animationTablePanel, BorderLayout.NORTH);
        this.add(verticalBox, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void changeSnipped(int index) {
        frameSettingsPanel.setSnippet(index);
        frameSettingsPanel.repaint();
    }
}