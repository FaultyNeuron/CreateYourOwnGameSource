package functionalJava.data;

/**
 * Created by Georg Plaz.
 */
public enum Axis {
    HORIZONTAL, VERTICAL;

    public Direction2D toDirection(boolean positive){
        if(this==HORIZONTAL){
            if(positive){
                return Direction2D.RIGHT;
            }else{
                return Direction2D.LEFT;
            }
        }else{
            if(positive){
                return Direction2D.UP;
            }else{
                return Direction2D.DOWN;
            }
        }
    }

    public Axis flip(){
        return this==HORIZONTAL?VERTICAL:HORIZONTAL;
    }

    public static Axis get(boolean horizontal) {
        return horizontal?HORIZONTAL:VERTICAL;
    }
}
