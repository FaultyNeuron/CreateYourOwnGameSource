package functionalJava.data.tupel;

import functionalJava.data.Axis;

/**
 * Created by Georg Plaz
 */
public abstract class NumberTupel<A extends Number> extends SymTupel<A> {
    public NumberTupel(A first, A second) {
        super(first, second);
    }

    public NumberTupel(Tupel<A, A> values) {
        super(values);
    }

    public NumberTupel(A[] values) {
        super(values);
    }

    public DoubleTupel toDoubleTupel(){
        return new DoubleTupel(getFirst().doubleValue(),getSecond().doubleValue());
    }

    public IntTupel toIntTupel(){
        return new IntTupel(getFirst().intValue(),getSecond().intValue());
    }

    public DoubleTupel add(NumberTupel other){
        return new DoubleTupel(toDoubleTupel().getFirst()+other.toDoubleTupel().getFirst(),toDoubleTupel().getSecond()+other.toDoubleTupel().getSecond());
    }

    public DoubleTupel add(Number first, Number second){
        return new DoubleTupel(toDoubleTupel().getFirst()+first.doubleValue(),toDoubleTupel().getSecond()+second.doubleValue());
    }

    public DoubleTupel add(Axis axis, Number delta){
        return axis == Axis.HORIZONTAL?add(delta, 0):add(0, delta);
    }

    public DoubleTupel sub(NumberTupel other){
        return new DoubleTupel(toDoubleTupel().getFirst()-other.toDoubleTupel().getFirst(),toDoubleTupel().getSecond()-other.toDoubleTupel().getSecond());
    }

    public DoubleTupel sub(Number first, Number second){
        return new DoubleTupel(toDoubleTupel().getFirst()-first.doubleValue(),toDoubleTupel().getSecond()-second.doubleValue());
    }

    public double mult(){
        return getFirst().doubleValue()*getSecond().doubleValue();
    }
    public DoubleTupel mult(NumberTupel other){
        return new DoubleTupel(toDoubleTupel().getFirst()*other.toDoubleTupel().getFirst(),toDoubleTupel().getSecond()*other.toDoubleTupel().getSecond());
    }

    public DoubleTupel mult(Number first, Number second){
        return new DoubleTupel(toDoubleTupel().getFirst()*first.doubleValue(),toDoubleTupel().getSecond()*second.doubleValue());
    }

    public DoubleTupel mult(Axis axis, Number c){
        return axis == Axis.HORIZONTAL ? mult(c, 1) : mult(1, c);
    }

    public DoubleTupel div(NumberTupel other){
        return new DoubleTupel(toDoubleTupel().getFirst()/other.toDoubleTupel().getFirst(),toDoubleTupel().getSecond()/other.toDoubleTupel().getSecond());
    }

    public DoubleTupel div(Number first, Number second){
        return new DoubleTupel(toDoubleTupel().getFirst()/first.doubleValue(),toDoubleTupel().getSecond()/second.doubleValue());
    }

    public A get(Axis axis) {
        return axis == Axis.HORIZONTAL ? getFirst() : getSecond();
    }
}
