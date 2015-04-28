package functionalJava.data.shape.box;

import functionalJava.data.Direction2D;
import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class ModifiableBox implements Box {
    private DoubleTupel center;
    private DoubleTupel dimensions;

    public ModifiableBox(DoubleTupel center, DoubleTupel dimensions) {
        this.center = center;
        this.dimensions = dimensions;
    }

    public void setDimensions(DoubleTupel newDimensions){
        dimensions = newDimensions;
    }

    public void setCenter(DoubleTupel newCenter){
        center = newCenter;
    }

    @Override
    public DoubleTupel getCenter() {
        return center;
    }

    @Override
    public DoubleTupel getDimensions() {
        return dimensions;
    }

    @Override
    public DoubleTupel getLeftLower() {
        return center.sub(dimensions.div(2));
    }

    @Override
    public DoubleTupel getRightUpper() {
        return center.add(dimensions.div(2));
    }

    @Override
    public DoubleTupel getRightLower() {
        return center.add(dimensions.div(2, -2));
    }

    @Override
    public DoubleTupel getLeftUpper() {
        return center.add(dimensions.div(-2, 2));
    }

    @Override
    public double getLeft() {
        return center.getFirst()-dimensions.getFirst()/2;
    }

    @Override
    public double getLower() {
        return center.getSecond()-dimensions.getSecond()/2;
    }

    @Override
    public double getRight() {
        return center.getFirst()+dimensions.getFirst()/2;
    }

    @Override
    public double getUpper() {
        return center.getSecond()+dimensions.getSecond()/2;
    }

    public boolean contains(DoubleTupel toCheck) {
        return getLeftLower().bothSmallerEqual(toCheck) &&
                getRightUpper().bothGreaterEqual(toCheck);
    }
    @Override
    public boolean contains(double first, double second) {
        return first >= getLeft() && second >= getLower() &&
                first <= getRight() && second <= getUpper();
    }

    @Override
    public double getSide(Direction2D side) {
        switch (side){
            case UP:
                return getUpper();
            case RIGHT:
                return getRight();
            case DOWN:
                return getLower();
            default:
                return getLeft();
        }
    }

    @Override
    public boolean collides(Box second) {
        return getRight() > second.getLeft() &&
                getLeft() < second.getRight() &&
                getUpper() > second.getLower() &&
                getLower() < second.getUpper();
    }


    @Override
    public ModifiableBox move(DoubleTupel delta) {
        ModifiableBox toReturn = copy();
        toReturn.center = center.add(delta);
        return toReturn;
    }

    @Override
    public ModifiableBox copy() {
        return new ModifiableBox(getCenter(), getDimensions());
    }

    @Override
    public double getWidth() {
        return dimensions.getFirst();
    }

    @Override
    public double getHeight() {
        return dimensions.getSecond();
    }

    @Override
    public String toString() {
        return "Box{center="+getCenter()+", dimensions="+getDimensions()+"}";
    }

    @Override
    public ModifiableBox scale(DoubleTupel c) {
        return scale(c.getFirst(), c.getSecond());
    }

    @Override
    public ModifiableBox scale(double withC, double heightC){
        ModifiableBox toReturn = copy();
        toReturn.dimensions = dimensions.mult(withC, heightC);
        return toReturn;
    }

    @Override
    public ModifiableBox scaleTo(double width, double height){
        return scaleTo(new DoubleTupel(width, height));
    }

    @Override
    public ModifiableBox scaleTo(DoubleTupel dimensions) {
        ModifiableBox toReturn = copy();
        toReturn.dimensions = dimensions;
        return toReturn;
    }
}
