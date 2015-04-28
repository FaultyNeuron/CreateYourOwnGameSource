package functionalJava.data.tupel;

/**
 * Created by Georg Plaz
 */
public class IntTupel extends NumberTupel<Integer> {
    public static final IntTupel ZEROS = new IntTupel(0);
    public IntTupel(Integer values) {
        super(values, values);
    }
    public IntTupel(Integer first, Integer second) {
        super(first, second);
    }

    public int sum(){
        return getFirst()+getSecond();
    }

    public IntTupel add(IntTupel other){
        return new IntTupel(getFirst()+other.getFirst(),getSecond()+other.getSecond());
    }

    public IntTupel add(int first, int second){
        return new IntTupel(getFirst()+first,getSecond()+second);
    }

    public IntTupel sub(IntTupel other){
        return new IntTupel(getFirst()-other.getFirst(),getSecond()-other.getSecond());
    }

    public IntTupel sub(int sub){
        return sub(sub, sub);
    }

    public IntTupel sub(int first, int second){
        if(first==0 && second==0){
            return this;
        }
        return new IntTupel(getFirst()-first,getSecond()-second);
    }

    public IntTupel mult(IntTupel other){
        if(other.equals(ZEROS)){
            return this;
        }
        return new IntTupel(getFirst()*other.getFirst(), getSecond()*other.getSecond());
    }

    public IntTupel mult(int first, int second){
        return new IntTupel(getFirst()*first,getSecond()*second);
    }

    public IntTupel div(IntTupel other){
        return new IntTupel(getFirst()/other.getFirst(),getSecond()/other.getSecond());
    }

    public IntTupel div(int first, int second){
        return new IntTupel(getFirst()/first,getSecond()/second);
    }

    public IntTupel abs(){return new IntTupel(Math.abs(getFirst()),Math.abs(getSecond()));}

    public int max(){
        return Math.max(getFirst(),getSecond());
    }

    public int min(){
        return Math.min(getFirst(), getSecond());
    }

    public IntTupel min(int first, int second){
        if(bothSmallerEqual(first, second)){
            return this;
        }
        return new IntTupel(Math.min(getFirst(),first), Math.min(getSecond(), second));
    }

    public IntTupel min(IntTupel other){
        if(bothSmallerEqual(other)){
            return this;
        }else if (other.bothSmallerEqual(this)){
            return other;
        }
        return new IntTupel(Math.min(getFirst(),other.getFirst()), Math.min(getSecond(), other.getSecond()));
    }

    public IntTupel max(IntTupel other){
        if(bothGreaterEqual(other)){
            return this;
        }else if (other.bothGreaterEqual(this)){
            return other;
        }
        return new IntTupel(Math.max(getFirst(), other.getFirst()), Math.max(getSecond(), other.getSecond()));
    }

    public boolean bothSmallerEqual(IntTupel other){
        return bothSmallerEqual(other.getFirst(), other.getSecond());
    }

    public boolean bothSmallerEqual(int first, int second){
        return getFirst()<=first && getSecond()<=second;
    }

    public boolean bothGreaterEqual(IntTupel other){
        return getFirst()>=other.getFirst() && getSecond()>=other.getSecond();
    }

    @Override
    public IntTupel toIntTupel(){
        return this;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof IntTupel)) return false;

        IntTupel tupel = (IntTupel) o;

        return getFirst().equals(tupel.getFirst()) &&
                getSecond().equals(tupel.getSecond());
    }

    @Override
    public int hashCode() {
        return getFirst()*31 + getSecond();
    }
}
