package kinderuni.desktop.ui;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.SystemComponent;
import kinderuni.ui.components.Component;

import javax.swing.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopComponent extends JComponent implements Component, SystemComponent {
    @Override
    public DoubleTupel getCompSize() {
        return Util.toDoubleTupel(getPreferredSize());
    }

    @Override
    public SystemComponent getSystemComponent() {
        return this;
    }
}
