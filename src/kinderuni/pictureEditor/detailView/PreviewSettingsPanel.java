package kinderuni.pictureEditor.detailView;

import kinderuni.pictureEditor.ImageSnippet;
import kinderuni.pictureEditor.language.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by markus on 27.06.15.
 */
public class PreviewSettingsPanel extends JPanel {
    private ArrayList<ImageSnippet> imageSnippets;
    private PreviewPanel previewPanel;
    private JPanel buttonPanel;
    private JButton startButton, stopButton;
    private JScrollBar speedScrollBar;
    private JLabel scrollBarTitle, scrollBarValue;
    private AnimationThread animationThread = null;
    private int currentAnimationSpeed = 50;
    private int minAnimationSleepTime = 0;
    private int maxAnimationSleepTime = 1000;
    private static final String SCROLL_BAR_VALUE_STRING = "%d ms";

    public PreviewSettingsPanel(ArrayList<ImageSnippet> imageSnippets) {
        this.imageSnippets = imageSnippets;

        this.setLayout(new BorderLayout());
        this.previewPanel = new PreviewPanel();
        this.previewPanel.setImage(imageSnippets.get(0).getSubimage());

        this.startButton = new JButton(Language.START);
        this.stopButton = new JButton(Language.STOP);
        this.buttonPanel = new JPanel(new BoxLayout(this, 0));
        this.speedScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, getCurrentAnimationSpeed(), 0, minAnimationSleepTime, maxAnimationSleepTime);
        this.scrollBarTitle = new JLabel(Language.ANIMATION_SPEED_IN_MS);
        this.scrollBarValue = new JLabel(String.format(SCROLL_BAR_VALUE_STRING, getCurrentAnimationSpeed()));

        System.err.println("PreStartButton init");
        this.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("Start button pressed");
                if (PreviewSettingsPanel.this.animationThread == null) {
                    PreviewSettingsPanel.this.animationThread = new AnimationThread(previewPanel, 0, getCurrentAnimationSpeed());
                    PreviewSettingsPanel.this.animationThread.start();
                }
            }
        });
        System.err.println("PostStartButton init");

        this.stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("Stop button pressed");
                if (animationThread != null) {
                    PreviewSettingsPanel.this.animationThread.interrupt();
                    PreviewSettingsPanel.this.animationThread = null;
                }
            }
        });

        this.speedScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                System.err.println("ScrollBar has new value: " + speedScrollBar.getValue());
                setCurrentAnimationSpeed(speedScrollBar.getValue());
                setAnimationThreadSleepTime(getCurrentAnimationSpeed());
                scrollBarValue.setText(String.format(SCROLL_BAR_VALUE_STRING, getCurrentAnimationSpeed()));
            }
        });

        Box horizontalBox = Box.createHorizontalBox();
        Box verticalBox = Box.createVerticalBox();
        horizontalBox.add(this.startButton);
        horizontalBox.add(this.stopButton);
        verticalBox.add(horizontalBox);
        verticalBox.add(scrollBarTitle);
        verticalBox.add(speedScrollBar);
        verticalBox.add(scrollBarValue);

        JPanel filler = new JPanel();
        filler.setBackground(Color.BLUE);
        this.add(filler, BorderLayout.WEST);
        this.add(previewPanel, BorderLayout.CENTER);
        this.add(verticalBox, BorderLayout.EAST);
    }

    private synchronized void setCurrentAnimationSpeed(int value) {
        this.currentAnimationSpeed = value;
    }

    private synchronized int getCurrentAnimationSpeed() {
        return currentAnimationSpeed;
    }

    private synchronized void setAnimationThreadSleepTime(int value) {
        if (animationThread != null) {
            animationThread.setSleepDuration(value);
        }
    }


    private class PreviewPanel extends JPanel {
        private BufferedImage image;

        public PreviewPanel() {
            this.setBackground(Color.GREEN);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }
    }

    private class AnimationThread extends Thread {
        private PreviewPanel panel;
        private int index, sleepDuration;

        public AnimationThread(PreviewPanel panel, int index, int sleepDuration) {
            this.panel = panel;
            this.index = index;
            this.sleepDuration = sleepDuration;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                panel.setImage(getNextImage());
                panel.repaint(0);
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public synchronized BufferedImage getNextImage() {
            // TODO Make the whole ImageSnippet shit thread save.
            BufferedImage image = PreviewSettingsPanel.this.imageSnippets.get(index).getFinalFrame();
            index++;
            index %= PreviewSettingsPanel.this.imageSnippets.size();
            return image;
        }

        public synchronized void setSleepDuration(int sleepDuration) {
            this.sleepDuration = sleepDuration;
        }
    }


}
