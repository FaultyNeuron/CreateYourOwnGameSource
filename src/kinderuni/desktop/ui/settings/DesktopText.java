package kinderuni.desktop.ui.settings;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.desktop.ui.Util;
import kinderuni.ui.SystemComponent;
import kinderuni.ui.SystemContainer;
import kinderuni.ui.components.TextComponent;

import javax.swing.*;

/**
 * Created by Georg Plaz.
 */
public class DesktopText extends JLabel implements TextComponent, SystemComponent {
    public DesktopText(String text){
        super(text);
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
