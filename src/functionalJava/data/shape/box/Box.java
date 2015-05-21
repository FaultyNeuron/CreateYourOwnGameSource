package functionalJava.data.shape.box;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz
 */
public interface Box {
//    public static final Box MIN_MAX_BOX = new MemoryHeavyBox(DoubleTupel.MIN, DoubleTupel.MAX);

    public DoubleTupel getLeftLower();
    public DoubleTupel getRightUpper();
    public DoubleTupel getLeftUpper();
    public DoubleTupel getRightLower();
    public DoubleTupel getCenter();
    public DoubleTupel getDimensions();

    public double getLeft();
    public double getLower();
    public double getRight();
    public double getUpper();
    public boolean contains(double x, double y);

    public double getSide(Direction2D side);

    boolean collides(Box second);

    public Box move(DoubleTupel delta);

    public Box copy();

    public double getWidth();
    public double getHeight();

    public ModifiableBox scale(double withC, double heightC);
    public ModifiableBox scale(DoubleTupel c);
    public ModifiableBox scaleTo(double width, double height);
    public ModifiableBox scaleTo(DoubleTupel dimensions);

}
