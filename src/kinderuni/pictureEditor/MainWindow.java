package kinderuni.pictureEditor;

import kinderuni.pictureEditor.detailView.DetailView;
import kinderuni.pictureEditor.generalView.DragAndDropComponent;
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

    public MainWindow() {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(800, 500));
        this.generalView = new GeneralView(imageSnippets);
        try {
//            generalView.setImage("/home/markus/Downloads/test.png");
            generalView.setImage("/home/markus/Downloads/roller.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.generalView.setTaskFinishedCallback(this);

        this.setLayout(new BorderLayout());
        this.add(generalView, BorderLayout.CENTER);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
    public void taskFinished(Object result) {
        detailView = new DetailView(imageSnippets);
        this.remove(generalView);
        this.add(detailView);
        this.revalidate();
        System.err.println("MainWindow taskFinished finished");
    }

    public void createDefaultSelections() {
        String path = "/home/markus/Downloads/roller.jpg";
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ImageSnippetFactory.setOriginalImage(image);
        int width = 500;
        int height = 500;
        for (int h=height; h < image.getHeight(); h += height){
            for (int w=width; w < image.getWidth(); w += width) {
                ImageSnippet imageSnippet = ImageSnippetFactory.getImageSelection();
                imageSnippet.setSnippetBounds(w - width, h - height, width, height);
                imageSnippets.add(imageSnippet);
            }
        }
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