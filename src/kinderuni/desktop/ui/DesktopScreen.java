package kinderuni.desktop.ui;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.*;
import kinderuni.desktop.DesktopInputRetriever;
import kinderuni.desktop.ui.settings.DesktopButton;
import kinderuni.desktop.ui.settings.DesktopText;
import kinderuni.ui.graphics.InputRetriever;
import kinderuni.ui.components.ButtonComponent;
import kinderuni.ui.components.Component;
import kinderuni.ui.components.TextComponent;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopScreen extends JFrame implements Screen {
    private JPanel content;
    private DoubleTupel size;
    private Dimension sizeDim;
    private kinderuni.System system;
    private java.util.LinkedList<Component> views = new LinkedList<>();

    public DesktopScreen(int width, int height, kinderuni.System system) {
        this(new DoubleTupel(width, height), system);
    }

    public DesktopScreen(DoubleTupel size, kinderuni.System system){
        this.size = size;
        this.system = system;
        sizeDim = Util.toDim(size.roundToIntTupel());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.VERTICAL;
        content = new ContentPanel();
        content.setPreferredSize(sizeDim);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.BLUE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(content);
        pack();
        setVisible(true);
    }

    @Override
    public void push(Component component) {
        views.add(component);
        showCurrent();
    }

    @Override
    public void back(){
        views.removeLast();
        showCurrent();
    }

    @Override
    public SystemContainer createSystemContainer() {
        return new DesktopContainer();
    }

    private void showCurrent(){
        content.removeAll();
//        getContentPane().removeAll();
        JComponent current = (JComponent) views.getLast().getSystemComponent();
        current.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        content.add(current);
        content.setBackground(Color.BLUE);

        content.revalidate();
        content.repaint();
    }

//    @Override
//    public ContainerComponent createContainer() {
//        return new DesktopContainer();
//    }

    @Override
    public ButtonComponent createButton(String text) {
        return new DesktopButton(text);
    }

    @Override
    public TextComponent createText(String text) {
        return new DesktopText(text);
    }

    @Override
    public DoubleTupel getCompSize() {
        return size;
    }

    private class ContentPanel extends JPanel{ //todo: make this scrollable
        @Override
        public Dimension getPreferredSize() {
            return sizeDim;
        }
    }

    @Override
    public InputRetriever createInputRetriever(){
        System.out.println("created retriever");
        return new DesktopInputRetriever(content);
    }

    @Override
    public GraphicsComponent createGraphicsComponent(DoubleTupel dimensions) {
        return new GraphicsPanel(dimensions);
    }

//    @Override
//    public LevelsComponent createLevelsComponent(Collection<LevelSettings> levelSettings) {
//        return new LevelsComponent(system, this, levelSettings);
//    }

    @Override
    public Component createLevelOverview(Collection<LevelSettings> values) {
        return new LevelOverview(system, this, values);
    }
}
