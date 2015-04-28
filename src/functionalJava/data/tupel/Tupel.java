package functionalJava.data.tupel;

/**
 * Created by Georg Plaz
 */
public class Tupel<A,B> {
    public static final char DELIMITER = ',';
    public static final char START = '(';
    public static final String START_STRING = String.valueOf(START);
    public static final char END = ')';
    public static final String END_STRING = String.valueOf(END);
    private final A first;
    private final B second;

    public Tupel(Tupel<A,B> values) {
        this(values.getFirst(), values.getSecond());
    }
    public Tupel(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public String toString(){
        return START + getFirst().toString()+DELIMITER+' '+getSecond().toString()+END;
    }
    public StringTupel toStringTupel(){
        return new StringTupel(getFirst().toString(), getSecond().toString()+END);
    }

    public String[] toStringArray() {
        return new String[]{getFirst().toString(), getSecond().toString()};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tupel)) return false;

        Tupel tupel = (Tupel) o;

        if (first != null ? !first.equals(tupel.first) : tupel.first != null) return false;
        if (second != null ? !second.equals(tupel.second) : tupel.second != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    public boolean hasNullValues(){
        return getFirst()==null || getSecond()==null;
    }

    public Tupel<B, A> flip(){
        return new Tupel<B, A>(getSecond(), getFirst());
    }

    public boolean equals(){
        return getFirst().equals(getSecond());
    }

    public boolean equals(String first, String second){
        return getFirst().equals(first) && getSecond().equals(second);
    }
}
