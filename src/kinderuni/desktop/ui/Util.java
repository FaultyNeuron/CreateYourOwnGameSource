package kinderuni.desktop.ui;

import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.tupel.IntTupel;

import java.awt.*;

/**
 * Created by Georg Plaz.
 */
public class Util {
    public static DoubleTupel toDoubleTupel(Dimension dimension){
        return new DoubleTupel(dimension.width, dimension.height);
    }

    public static Dimension toDim(IntTupel size) {
        return new Dimension(size.getFirst(), size.getSecond());
    }

    public static Color toColor(int[] colour){
        if(colour.length>=4){
            return new Color(colour[0], colour[1], colour[2], colour[3]);
        }else{
            return new Color(colour[0], colour[1], colour[2]);
        }
    }
}
