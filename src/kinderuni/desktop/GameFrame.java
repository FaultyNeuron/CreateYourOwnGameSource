package kinderuni.desktop;

import functionalJava.data.tupel.DoubleTupel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public class GameFrame extends JFrame {
    private GamePanel gamePanel;

    public GameFrame(DoubleTupel dimensions){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gamePanel = new GamePanel(dimensions);
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }


    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
