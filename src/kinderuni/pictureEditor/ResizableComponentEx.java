package kinderuni.pictureEditor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by markus on 26.06.15.
 */

public class ResizableComponentEx extends JFrame {

    private Resizable res1;
    private Resizable res2;

    public ResizableComponentEx() {

        initUI();
    }

    private void initUI() {

        JPanel pnl = new JPanel(null);
        add(pnl);

        JPanel area1 = new JPanel();
        area1.setBackground(Color.white);
        res1 = new Resizable(area1);
        res1.setBounds(50, 50, 200, 150);
        pnl.add(res1);

        JPanel area2 = new JPanel();
        area2.setBackground(Color.white);
        res2 = new Resizable(null);
        res2.setBounds(50, 50, 200, 150);
        pnl.add(res2);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                requestFocus();
                res1.repaint();
                res2.repaint();
            }
        });

        setSize(350, 300);
        setTitle("Resizable component");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ResizableComponentEx ex = new ResizableComponentEx();
                ex.setVisible(true);
            }
        });
    }
}