package kinderuni.pictureEditor;

//import net.iharder.dnd.FileDrop;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by markus on 25.06.15.
 */
public class DragAndDropComponent extends JComponent {
    private FileDroppedCallback callback;
    private int width, height;
    private String text;

    public DragAndDropComponent(String text, FileDroppedCallback callback, int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.text = text;
        this.setSize(new Dimension(width, height));
        this.callback = callback;
        this.setEnabled(false);
//        new FileDrop(this, new FileDrop.Listener() {
//            @Override
//            public void filesDropped(File[] files) {
//                callback.filesDropped(files);
//            }
//        });

        this.setBackground(color);
        this.setBorder(BorderFactory.createDashedBorder(null));
        this.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setFont(new JLabel().getFont());
        graphics2D.drawString(text, (this.width/2) -  graphics2D.getFontMetrics().stringWidth(text)/2, this.height/2);
    }
}