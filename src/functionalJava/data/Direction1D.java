package functionalJava.data;

/**
 * Created by Georg Plaz.
 */
public enum Direction1D {
    LEFT, RIGHT;

    public Direction2D to2D(){
        return this==LEFT?Direction2D.LEFT:Direction2D.RIGHT;
    }
}
