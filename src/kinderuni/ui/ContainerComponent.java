package kinderuni.ui;


import kinderuni.ui.components.Component;

/**
 * Created by Georg Plaz.
 */
public interface ContainerComponent extends Component{
    void add(Component component);
    void breakLine();
}
