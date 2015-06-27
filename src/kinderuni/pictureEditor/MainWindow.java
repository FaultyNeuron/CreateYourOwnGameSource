package kinderuni.pictureEditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by markus on 25.06.15.
 */
public class MainWindow extends JFrame implements TaskFinishedCallback<ArrayList<Rectangle>> {
    private GeneralView generalView;
    private DetailPanel detailView;
    private DragAndDropComponent dragAndDropComponent;
    private ArrayList<ImageSelection> imageSelections = new ArrayList<>();

    public MainWindow() {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(500, 500));
        this.generalView = new GeneralView(this.getWidth(), this.getHeight());
        try {
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
    public void taskFinished(ArrayList<Rectangle> result) {
        detailView = new DetailPanel();
        this.remove(generalView);
        this.add(detailView);
        this.revalidate();
        System.err.println("MainWindow taskFinished finished");
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