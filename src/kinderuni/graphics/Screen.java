package kinderuni.graphics;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.GameWorld;
import kinderuni.level.Level;

/**
 * Created by Georg Plaz.
 */
public interface Screen {
    public DoubleTupel getScreenDimensions();

    public double getScreenWidth();

    public double getScreenHeight();

    public DoubleTupel getCenter();

    public void setCenter(DoubleTupel center);

    public Box getScreenArea();

    public abstract void render();

    public void start();

    public boolean add(Paintable paintable);

    public boolean remove(Paintable paintable);
}
