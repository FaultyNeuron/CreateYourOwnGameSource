package functionalJava.data;

import functionalJava.data.tupel.DoubleTupel;

import java.util.*;

/**
 * Created by Georg Plaz.
 */
public enum Direction2D {
    UP, RIGHT, DOWN, LEFT;
    public static final List<Direction2D> DIRECTION_LIST = Collections.unmodifiableList(Arrays.asList(values()));
    public static final Set<Direction2D> DIRECTION_SET = Collections.unmodifiableSet(new HashSet<Direction2D>(DIRECTION_LIST));

    public Axis toAxis(){
        switch (this){
            case UP:
            case DOWN:
                return Axis.VERTICAL;
            case RIGHT:
            default:
                return Axis.HORIZONTAL;
        }
    }

    public boolean isPositive(){
        switch (this){
            case UP:
            case RIGHT:
                return true;
            case DOWN:
            default:
                return false;
        }
    }

    public boolean isHorizontal(){
        switch (this){
            case LEFT:
            case RIGHT:
                return true;
            default:
                return false;
        }
    }

    public boolean isVertical(){return !isHorizontal();}

    public int getSign(){
        return isPositive()?1:-1;
    }

    public Direction2D reverse(){
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            default:
                return RIGHT;
        }
    }
    public DoubleTupel toVector() {
        switch (this){
            case UP:
                return DoubleTupel.UP;
            case DOWN:
                return DoubleTupel.DOWN;
            case RIGHT:
                return DoubleTupel.RIGHT;
            default:
                return DoubleTupel.LEFT;
        }
    }

    public static Direction2D getDirection(Axis axis, boolean positive){
        return axis.toDirection(positive);
    }

    public DoubleTupel toVector(double length) {
        return toVector().mult(length);
    }

    public static Set<Direction2D> createSet(){
        return new HashSet<Direction2D>(DIRECTION_SET);
    }

    public Direction1D toDirection1D(){
        switch (this){
            case LEFT:
                return Direction1D.LEFT;
            case RIGHT:
                return Direction1D.RIGHT;
            default:
                return null;
        }
    }
}
