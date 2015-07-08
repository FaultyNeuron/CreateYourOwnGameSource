package kinderuni.pictureEditor.detailView;

import kinderuni.pictureEditor.ImageSnippet;
import kinderuni.pictureEditor.ThreadSaveImageSnippetContainer;
import kinderuni.pictureEditor.language.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

/**
 * Created by markus on 27.06.15.
 */
public class FrameSettingsPanel extends JPanel {
//    private ArrayList<ImageSnippet> imageSnippets;
    private ThreadSaveImageSnippetContainer imageSnippetContainer;
    private int currentSnippet;
    private JPanel framePanel;
    private JScrollBar rThresholdScrollBar, gThresholdScrollBar, bThresholdScrollBar;
    private JButton applyButton;
    private JButton cropButton;
    private JButton resetButton;
    private JLabel scrollBarTitleLabel, scrollBarValueLabel;
    private final int defaultThreshold = 200;
    private int minThreshold = 0;
    private int maxThreshold = 255;
    private int currentThreshold = defaultThreshold;
    private static final String SCROLL_BAR_VALUE_STRING = "r%d g%d  b%d";
    private int rThreshold = defaultThreshold, gThreshold = defaultThreshold, bThreshold = defaultThreshold;

    public FrameSettingsPanel() {
//        this.imageSnippets = imageSnippets;
        this.imageSnippetContainer = ThreadSaveImageSnippetContainer.getInstance();
        this.currentSnippet = 0;

        framePanel = new FramePanel();
        this.applyButton = new JButton(Language.APPLY);
        this.cropButton = new JButton(Language.CROP);
        this.resetButton = new JButton(Language.RESET);
        this.rThresholdScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, currentThreshold, 0, minThreshold, maxThreshold);
        this.gThresholdScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, currentThreshold, 0, minThreshold, maxThreshold);
        this.bThresholdScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, currentThreshold, 0, minThreshold, maxThreshold);
        this.scrollBarTitleLabel = new JLabel(Language.THRESHOLD);
        this.scrollBarValueLabel = new JLabel(String.format(SCROLL_BAR_VALUE_STRING, currentThreshold, currentThreshold, currentThreshold));

        this.rThresholdScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                rThreshold = rThresholdScrollBar.getValue();
                updateThresholdValueLabel();
            }
        });
        this.gThresholdScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                gThreshold = gThresholdScrollBar.getValue();
                updateThresholdValueLabel();
            }
        });
        this.bThresholdScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                bThreshold = bThresholdScrollBar.getValue();
                updateThresholdValueLabel();
            }
        });
        this.applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTransparency();
                framePanel.repaint();
            }
        });
        this.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFinalFrame();
            }
        });

        this.setLayout(new BorderLayout());
        Box horizontalBox = Box.createHorizontalBox();
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(scrollBarTitleLabel);
        verticalBox.add(rThresholdScrollBar);
        verticalBox.add(gThresholdScrollBar);
        verticalBox.add(bThresholdScrollBar);
        verticalBox.add(scrollBarValueLabel);
        verticalBox.add(applyButton);
        verticalBox.add(cropButton);
        verticalBox.add(resetButton);
        horizontalBox.add(framePanel);
        horizontalBox.add(verticalBox);
        this.add(horizontalBox);

        this.setBackground(Color.WHITE);

        this.addKeyListener(SaveKeyListener.getInstance());
    }

    private void updateThresholdValueLabel() {
        scrollBarValueLabel.setText(String.format(SCROLL_BAR_VALUE_STRING, rThreshold, gThreshold, bThreshold));
    }

    private void calculateTransparency() {
        imageSnippetContainer.get(currentSnippet).calculateFinalFrameTransparency(rThreshold, gThreshold, bThreshold);
        framePanel.repaint();
    }

    private void resetFinalFrame() {
        imageSnippetContainer.get(currentSnippet).resetFinalFrame();
        framePanel.repaint();
    }

    private synchronized int getCurrentThreshold() {
        return this.currentThreshold;
    }

    public void setSnippet(int index) {
        this.currentSnippet = index;
    }

    private boolean isIndexValid() {
        return currentSnippet >= 0 && currentSnippet < imageSnippetContainer.size();
    }

    private class FramePanel extends JPanel {

        public FramePanel() {
            this.setBackground(Color.GREEN);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isIndexValid()) {
                Graphics2D graphics2D = (Graphics2D) g;
//                Composite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0f);
//                graphics2D.setComposite(composite);
                graphics2D.drawImage(imageSnippetContainer.get(currentSnippet).getFinalFrame(), 0, 0, null);
//                graphics2D.dispose();
            }
        }
    }
}