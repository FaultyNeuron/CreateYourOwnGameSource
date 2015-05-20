package kinderuni.ui;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.Paintable;
import kinderuni.ui.components.Component;

/**
 * Created by Georg Plaz.
 */
public interface GraphicsComponent extends Component{
    public DoubleTupel getCenter();

    public void setRenderCenter(DoubleTupel center);

    public Box getScreenArea();

    public abstract void render();

    public void start();

    public boolean add(Paintable paintable);

    public boolean remove(Paintable paintable);
}
