package kinderuni.pictureEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by markus on 26.06.15.
 */
public class GeneralView extends JLayeredPane implements TaskFinishedCallback<ArrayList<Resizable>> {
    private int width, height;
    private ImagePanel imagePanel;
    private ResizableRectanglePanel resizableRectanglePanel;
    private TaskFinishedCallback taskFinishedCallback = null;

    public GeneralView(int width, int height) {
        System.err.println("Creating JLayeredPane with size  " + width + " " + height);

        this.width = width;
        this.height = height;

        this.setLayout(null);

        this.imagePanel = new ImagePanel();
        this.resizableRectanglePanel = new ResizableRectanglePanel(width, height);
        this.resizableRectanglePanel.setBackground(Color.CYAN);
        this.resizableRectanglePanel.setTaskFinishedCallback(this);

        this.add(imagePanel);
        this.add(resizableRectanglePanel);

        this.setComponentZOrder(resizableRectanglePanel, 0);
        this.setComponentZOrder(imagePanel, 1);

        this.setBackground(Color.BLUE);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                GeneralView.this.setSize(GeneralView.this.getParent().getWidth(), GeneralView.this.getParent().getHeight());
                imagePanel.setSize(GeneralView.this.getParent().getWidth(), GeneralView.this.getParent().getHeight());
                resizableRectanglePanel.setSize(GeneralView.this.getParent().getWidth(), GeneralView.this.getParent().getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }

    public void setImage(String path) throws IOException {
        imagePanel.setImage(path);
    }

    public void setTaskFinishedCallback(TaskFinishedCallback taskFinishedCallback) {
        this.taskFinishedCallback = taskFinishedCallback;
    }

    private void callTaskFinishedCallback(ArrayList<Resizable> result) {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        for (Resizable resizable : result) {
            rectangles.add(new Rectangle(resizable.getX(), resizable.getY(), resizable.getWidth(), resizable.getHeight()));
        }
        taskFinishedCallback.taskFinished(rectangles);
    }

    @Override
    public void taskFinished(ArrayList<Resizable> result) {
        this.callTaskFinishedCallback(result);
    }
}
