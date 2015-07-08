package kinderuni.pictureEditor;

import kinderuni.pictureEditor.detailView.DetailView;
import kinderuni.pictureEditor.generalView.GeneralView;
import kinderuni.pictureEditor.language.Language;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by markus on 25.06.15.
 */
public class MainWindow extends JFrame implements TaskFinishedCallback {
    private GeneralView generalView;
    private DetailView detailView;
    //private DragAndDropComponent dragAndDropComponent;
    private ArrayList<ImageSnippet> imageSnippets = new ArrayList<>();
    private ThreadSaveImageSnippetContainer imageSnippetContainer;
    private boolean isGeneralViewActive;

    public MainWindow() {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(800, 500));

        this.imageSnippetContainer = ThreadSaveImageSnippetContainer.getInstance();

        this.generalView = new GeneralView();
        this.generalView.setTaskFinishedCallback(this);
//        try {
//            generalView.setImage("/home/markus/Downloads/test.png");
////            generalView.setImage("/home/markus/Downloads/roller.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        this.setLayout(new BorderLayout());
        this.add(generalView, BorderLayout.CENTER);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.isGeneralViewActive = true;

        this.addKeyListener(new OpenFileKeyListener());

        this.setBackground(Color.RED);

//        this.createDefaultSelections();
//        this.taskFinished(null);
        this.setVisible(true);
    }

    private void startNewGeneralView() {
        File file = openImageFile();
        if (file != null) {
            try {
                generalView.setImage(file.getAbsolutePath());
                this.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        generalView.refresh();
    }

    private File openImageFile() {
        JFileChooser fileChooser = new ImageFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            System.err.println("Opening: " + file.getName());
            Globals.setLastFileChooserPath(file.getPath());
        }
        return file;
    }

    @Override
    public void taskFinished() {
        System.err.println("MainWindow site = " + imageSnippetContainer.size());
        if (isGeneralViewActive) {
            this.detailView = new DetailView();
            this.remove(generalView);
            this.add(detailView);
            this.isGeneralViewActive = false;
            detailView.refresh();
        } else {
            this.remove(detailView);
            this.add(generalView);
            this.isGeneralViewActive = true;
            generalView.refresh();
        }
        this.revalidate();
        System.err.println("Task switched.");
    }

    private class ImageFileChooser extends JFileChooser {
        public ImageFileChooser() {
            super(Globals.getLastFileChooserPath());
            this.addChoosableFileFilter(new FileNameExtensionFilter(Language.FILE_CHOOSER_IMAGE_FILTER, "jpg", "png"));
            this.setAcceptAllFileFilterUsed(false);
        }
    }

    private class OpenFileKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if ((e.getKeyCode() == KeyEvent.VK_O) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                MainWindow.this.startNewGeneralView();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}