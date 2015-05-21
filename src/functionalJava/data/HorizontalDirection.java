package functionalJava.data;

import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.NumberTupel;

/**
 * Created by Georg Plaz.
 */
public enum HorizontalDirection {
    LEFT, RIGHT;

    public Direction2D to2D(){
        return this==LEFT?Direction2D.LEFT:Direction2D.RIGHT;
    }

    public NumberTupel toVector(double speed) {
        return to2D().toVector(speed);
    }

    public static HorizontalDirection toDirection(DoubleTupel delta) {
        if(delta.getFirst()>0){
            return RIGHT;
        }else{
            return LEFT;
        }
    }
}
