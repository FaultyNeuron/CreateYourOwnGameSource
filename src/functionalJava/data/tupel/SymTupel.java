package functionalJava.data.tupel;

import java.util.Iterator;

/**
 * Created by Georg Plaz
 */
public class SymTupel<A> extends Tupel<A, A> implements Iterable<A>{
    public SymTupel(A forBoth) {
        this(forBoth, forBoth);
    }

    public SymTupel(A first, A second) {
        super(first, second);
    }

    public SymTupel(Tupel<A, A> values) {
        super(values);
    }

    public SymTupel(A[] values) {
        super(values[0], values[1]);
        if(values.length!=2){
            throw new RuntimeException("array must be of dimension 2!");
        }
    }

//    public A[] toArray() {
//        return (A[]) new Object[]{getFirst(), getSecond()};
//    }

    public SymTupel<A> swap(){
        return new SymTupel<A>(getSecond(), getFirst());
    }

    @Override
    public Iterator<A> iterator() {
        return new Iter();
    }

    public A get(int hardFactIndex) {
        return hardFactIndex==0?getFirst():getSecond();
    }

    public class Iter implements Iterator<A>{
        short pos = 0;
        @Override
        public boolean hasNext() {
            return pos<=1;
        }

        @Override
        public A next() {
            return get(pos++);
        }
    }

}
