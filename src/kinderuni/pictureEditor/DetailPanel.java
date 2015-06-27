package kinderuni.pictureEditor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by markus on 26.06.15.
 */
public class DetailPanel extends JPanel {
    private AnimationListPanel animationListPanel;
    private JPanel frameSettingsPanel = new JPanel();
    private JPanel previewPanel = new JPanel();

    public DetailPanel () {
//        this.setLayout(new SpringLayout());
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLUE);

        this.animationListPanel = new AnimationListPanel();
        this.animationListPanel.setSize(new Dimension((int)this.animationListPanel.getSize().getWidth(), (int)this.animationListPanel.getSize().getHeight()+200));

        this.frameSettingsPanel = new JPanel();

        this.previewPanel = new JPanel();

        frameSettingsPanel.setBackground(Color.CYAN);
        previewPanel.setBackground(Color.PINK);

        JPanel container = new JPanel(new BoxLayout(this, this.getWidth()));
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(frameSettingsPanel);
        verticalBox.add(previewPanel);

        this.add(animationListPanel, BorderLayout.NORTH);
        this.add(verticalBox, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
