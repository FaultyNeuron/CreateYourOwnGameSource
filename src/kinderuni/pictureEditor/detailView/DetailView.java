package kinderuni.pictureEditor.detailView;

import kinderuni.pictureEditor.ImageSnippet;
import kinderuni.pictureEditor.ThreadSaveImageSnippetContainer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by markus on 26.06.15.
 */
public class DetailView extends JPanel implements DetailPanelCallback, SaveCallback {
    private AnimationTablePanel animationTablePanel;
    private FrameSettingsPanel frameSettingsPanel;
    private PreviewSettingsPanel previewSettingsPanel;
    private ArrayList<ImageSnippet> imageSnippets;
    private ThreadSaveImageSnippetContainer imageSnippetContainer;
    private SavePanel savePanel;

    public DetailView() {
        this.imageSnippetContainer = ThreadSaveImageSnippetContainer.getInstance();
        System.err.println("DetailView size = " + imageSnippetContainer.size());

//        this.setLayout(new SpringLayout());
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLUE);

        this.animationTablePanel = new AnimationTablePanel();
        this.animationTablePanel.setSize(new Dimension((int)this.animationTablePanel.getSize().getWidth(), (int)this.animationTablePanel.getSize().getHeight()+200));
        this.animationTablePanel.setDetailPanelCallback(this);

        this.frameSettingsPanel = new FrameSettingsPanel();

        this.previewSettingsPanel = new PreviewSettingsPanel(this);
        previewSettingsPanel.setBackground(Color.PINK);

        JPanel container = new JPanel(new BoxLayout(this, this.getWidth()));
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(frameSettingsPanel);
        verticalBox.add(previewSettingsPanel);

        savePanel = new SavePanel();

        this.add(animationTablePanel, BorderLayout.NORTH);
//        this.add(verticalBox, BorderLayout.CENTER);

        this.add(savePanel, BorderLayout.CENTER);

        this.addKeyListener(SaveKeyListener.getInstance());

        this.setVisible(true);
    }

    public void refresh() {
        animationTablePanel.init();
        previewSettingsPanel.init();
    }

    public void changeSnipped(int index) {
        frameSettingsPanel.setSnippet(index);
        frameSettingsPanel.repaint();
    }

    @Override
    public void save() {
        SavePanel savePanel = new SavePanel();
        System.err.println("Save callback called .....");
    }
}