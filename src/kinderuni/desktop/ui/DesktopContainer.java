package kinderuni.desktop.ui;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.ContainerComponent;
import kinderuni.ui.SystemComponent;
import kinderuni.ui.SystemContainer;
import kinderuni.ui.components.Component;

import javax.swing.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopContainer extends JPanel implements SystemContainer {
    private JPanel currentRow;

    public DesktopContainer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        breakLine();
    }

    @Override
    public void add(Component component) {
        JComponent jComponent = (JComponent) component.getSystemComponent();
        currentRow.add(jComponent);
//        currentRow.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    }

    @Override
    public void breakLine() {
        this.currentRow = new JPanel();
        add(currentRow);
    }

    @Override
    public DoubleTupel getCompSize() {
        return Util.toDoubleTupel(getPreferredSize());
    }

    @Override
    public SystemComponent getSystemComponent() {
        return this;
    }
}
