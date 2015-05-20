package kinderuni.desktop.ui.settings;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.desktop.ui.Util;
import kinderuni.ui.SystemComponent;
import kinderuni.ui.components.ButtonComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Georg Plaz.
 */
public class DesktopButton extends JButton implements ButtonComponent, SystemComponent {
    public DesktopButton(String text){
        super(text);
    }

    @Override
    public void addListener(final OnClickListener listener) {
        addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onClick();
            }
        });
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
