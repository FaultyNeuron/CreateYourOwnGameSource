package functionalJava.data.tupel;

/**
 * Created by Georg Plaz
 */
public class BooleanTupel extends SymTupel<Boolean> {
    public static final BooleanTupel TRUE_TRUE = new BooleanTupel(true);
    public static final BooleanTupel TRUE_FALSE = new BooleanTupel(true, false);
    public static final BooleanTupel FALSE_TRUE = new BooleanTupel(false, true);
    public static final BooleanTupel FALSE_FALSE = new BooleanTupel(false);

    private final boolean and;
    private final boolean or;
    private final boolean xor;
//    private boolean not;
    private BooleanTupel(boolean forBoth) {
        this(forBoth, forBoth);
    }

    private BooleanTupel(boolean first, boolean second) {
        super(first, second);
        and = getFirst() && getSecond();
        or = getFirst() || getSecond();
        xor = !getFirst().equals(getSecond());
    }

    public boolean and(){
        return and;
    }

    public boolean or(){
        return or;
    }

    public boolean xor(){
        return xor;
    }

    public static BooleanTupel get(boolean forBoth) {
        return forBoth? TRUE_TRUE : FALSE_FALSE;
    }
    public static BooleanTupel get(boolean first, boolean second) {
        if(first){
            if(second){
                return TRUE_TRUE;
            }else{
                return TRUE_FALSE;
            }
        }else{
            if(second){
                return FALSE_TRUE;
            }else{
                return FALSE_FALSE;
            }
        }
    }
}
