package kinderuni.pictureEditor.detailView;

import kinderuni.pictureEditor.ThreadSaveImageSnippetContainer;
import kinderuni.pictureEditor.language.Language;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by markus on 08.07.15.
 */
public class SavePanel extends JPanel {
    private ThreadSaveImageSnippetContainer imageSnippetContainer;
    private ContentPanel contentPanel;

    public SavePanel() {
        this.imageSnippetContainer = ThreadSaveImageSnippetContainer.getInstance();
//        this.setMinimumSize(new Dimension(800, 500));
        this.contentPanel = new ContentPanel();
        this.add(contentPanel);

//        this.setVisible(true);
    }

    private class ContentPanel extends JPanel {
        private JTextField fileEndingsField, folderField, fpsTextField;
        private JLabel fileEndingsLabel, fpsLabel, selectFolderLabel, animationTypeLabel;
        private JButton saveButton;
        private JRadioButton idleButton, walkingButton, jumpingButton, flyButton, startOverButton;
        private ButtonGroup buttonGroupLoopType, buttonGroupAnimationType;
        private ArrayList<JLabel> warnings = new ArrayList<>();
        private Box warningsBox = Box.createVerticalBox();

        public ContentPanel() {
            fileEndingsLabel = new JLabel("Die Bilder werden wie folgt nummeriert sein:");
            fileEndingsField = new JTextField(getFileNameFieldText());
            selectFolderLabel = new JLabel("Ordner der Animation angeben");
            folderField = new JTextField();
            animationTypeLabel = new JLabel("Animationstyp angeben");
            idleButton = new JRadioButton("idle");
            walkingButton = new JRadioButton("walking");
            jumpingButton = new JRadioButton("jumping");
            flyButton = new JRadioButton("flying");
//            fpsLabel = new JLabel("FPS");
//            fpsTextField = new JTextField("10");
//            startOverButton = new JRadioButton("START_OVER");

            buttonGroupAnimationType = new ButtonGroup();
            buttonGroupAnimationType.add(idleButton);
            buttonGroupAnimationType.add(walkingButton);
            buttonGroupAnimationType.add(jumpingButton);
            buttonGroupAnimationType.add(flyButton);
            idleButton.setSelected(true);

            buttonGroupLoopType = new ButtonGroup();
            buttonGroupLoopType.add(startOverButton);

            saveButton = new JButton(Language.SAVE);

            Box verticalBox = Box.createVerticalBox();
            verticalBox.add(fileEndingsLabel);
            verticalBox.add(fileEndingsField);
            verticalBox.add(selectFolderLabel);
            verticalBox.add(folderField);
            verticalBox.add(animationTypeLabel);
            verticalBox.add(idleButton);
            verticalBox.add(walkingButton);
            verticalBox.add(jumpingButton);
            verticalBox.add(flyButton);
//            verticalBox.add(fpsLabel);
//            verticalBox.add(fpsTextField);
            verticalBox.add(saveButton);
            verticalBox.add(warningsBox);

            add(verticalBox);

            saveButton.addActionListener(new SaveButtonListener());

//            this.setBackground(Color.YELLOW);
        }

        private String getFileNameFieldText() {
            String s = "";
            for (int i=0; i<imageSnippetContainer.size(); i++) {
                if (s.equals("")) {
                    s += i;
                } else {
                    s += "," + i;
                }
            }
            return s;
        }

        private void displayWarnings() {
            warningsBox.removeAll();
            for (JLabel label : warnings) {
                warningsBox.add(label);
            }
            warningsBox.repaint();
            this.repaint();
        }

        private class SaveButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<BufferedImage> scaledImages = imageSnippetContainer.getFinalFrames();
                ArrayList<String> fileNumbers = getFileNumbers();
                File animationFolder = new File(folderField.getText());
                animationFolder.mkdirs();
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(new File(animationFolder, "graphicsInfo.json")));
                    writer.write("{\n" +
                            "    \"fps\" : 5,\n" +
                            "    \"file_type\" : \"png\",\n" +
                            "    \"loop_type\" : \"START_OVER\"\n" +
                            "}");
                }catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // Close the writer regardless of what happens...
                        writer.close();
                    } catch (Exception e) {
                    }
                }
                File typeFolder = new File(animationFolder, "" + getSelectedButtonText(buttonGroupAnimationType));
                typeFolder.mkdirs();
                String fileEnding = "png";
                String fileName = "anim%s." + fileEnding;


                int index = 0;
                for (BufferedImage image : scaledImages) {
                    File out = new File(typeFolder, String.format(fileName, fileNumbers.get(index++)));
                    if (out.exists()) {
                        System.err.println("Wrinting image " + out.getAbsolutePath() + " skipped because file already exists.");
                        warnings.add(new JLabel("Datei " + out.getAbsolutePath() + " existiert bereits und wurde nicht überschrieben."));
                    } else {
                        System.err.println("Image " + out.getAbsolutePath() + " written.");
                        try {
                            out.createNewFile();
                            ImageIO.write(image, fileEnding, out);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                displayWarnings();
            }
        }

        private ArrayList<String> getFileNumbers() {
            String[] numbers = fileEndingsField.getText().split(",");
            ArrayList<String> s = new ArrayList<>();
            s.addAll(Arrays.asList(numbers));
            return s;
        }

        public String getSelectedButtonText(ButtonGroup buttonGroup) {
            for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();

                if (button.isSelected()) {
                    return button.getText();
                }
            }

            return null;
        }
    }
}
