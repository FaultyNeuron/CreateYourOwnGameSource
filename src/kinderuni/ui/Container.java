package kinderuni.ui;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.*;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.components.ButtonComponent;
import kinderuni.ui.components.Component;

import java.util.Collection;

/**
 * Created by Georg Plaz.
 */
public class Container implements ContainerComponent{
    private final SystemContainer systemComponent;

    public Container(final Screen screen){
        systemComponent = screen.createSystemContainer();
    }

    public void add(Component component){
        systemComponent.add(component);
    }

    @Override
    public void breakLine() {
        systemComponent.breakLine();
    }

    @Override
    public DoubleTupel getCompSize() {
        return systemComponent.getCompSize();
    }

    @Override
    public SystemComponent getSystemComponent() {
        return systemComponent;
    }
}
