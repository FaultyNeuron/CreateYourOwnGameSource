package functionalJava.data.tupel;

import java.util.Collection;

/**
 * Created by Georg Plaz
 */
public class DoubleTupel extends NumberTupel<Double>{
    public static final DoubleTupel ZEROS = new DoubleTupel(0);
    public static final DoubleTupel HALVES = new DoubleTupel(0.5);
    public static final DoubleTupel ONES = new DoubleTupel(1);
    public static final DoubleTupel TWOS = new DoubleTupel(2);
    public static final DoubleTupel MIN = new DoubleTupel(-Double.MAX_VALUE);
    public static final DoubleTupel MAX = new DoubleTupel(Double.MAX_VALUE);
    public static final double EPSILON_VALUE = 0.00001;
    public static final DoubleTupel EPSILON = new DoubleTupel(EPSILON_VALUE);
    public static final DoubleTupel UP = new DoubleTupel(0, 1);
    public static final DoubleTupel RIGHT = new DoubleTupel(1, 0);
    public static final DoubleTupel DOWN = new DoubleTupel(0, -1);
    public static final DoubleTupel LEFT = new DoubleTupel(-1, 0);

    public DoubleTupel(Tupel<Double, Double> values) {
        super(values);
    }

    public DoubleTupel(double[] array) {
        super(array[0], array[1]);
    }

    public DoubleTupel(Double[] array) {
        this(array[0], array[1]);
    }

    public DoubleTupel(double first, double second) {
        super(first, second);
    }

//    public DoubleTupel(Double first, Double second) {
//        super(first, second);
//    }

    public DoubleTupel(String toParse) {
        this(parse(toParse));
    }

    public DoubleTupel(StringTupel toParse) {
        this(toParse.getFirst(), toParse.getSecond());
    }

    public DoubleTupel(String[] valuesToParse) {
        this(valuesToParse[0], valuesToParse[1]);
    }

    public DoubleTupel(String first, String second) {
        this(new Double(first), new Double(second));
    }

    public BooleanTupel isNaN(){
        return BooleanTupel.get(getFirst().isNaN(), getSecond().isNaN());
    }

    public boolean anyIsNaN(){
        return getFirst().isNaN() || getSecond().isNaN();
    }

    public DoubleTupel(double value) {
        super(value, value);
    }

    @Override
    public DoubleTupel toDoubleTupel(){
        return this;
    }

    public IntTupel roundToIntTupel(){
        return new IntTupel(Math.round(getFirst().intValue()), Math.round(getSecond().intValue()));
    }

    public double max(){
        return Math.max(getFirst(), getSecond());
    }

    public double min(){
        return Math.min(getFirst(), getSecond());
    }

    public double[] toArrayUnboxed() {
        return new double[]{getFirst(), getSecond()};
    }

    public DoubleTupel round(int precision) {
        double precisionPow = Math.round(Math.pow(10, precision));
        return new DoubleTupel(round(getFirst(), precisionPow), round(getSecond(), precisionPow));
    }

    private double round(double toRound, double precisionPow){
        return Math.round(toRound*precisionPow)/precisionPow;
    }

    public static double roundSingle(double number, int precision){
        double precisionPow = Math.round(Math.pow(10, precision));
        return Math.round(number*precisionPow)/precisionPow;
    }

    public DoubleTupel min(double first, double second) {
        if(getFirst()<=first && getSecond()<=second){
            return this;
        }
        return new DoubleTupel(Math.min(getFirst(), first), Math.min(getSecond(), second));
    }

    public DoubleTupel min(DoubleTupel other) {
        if(bothSmallerEqual(other)){
            return this;
        }else if(other.bothSmallerEqual(this)){
            return other;
        }
        return new DoubleTupel(Math.min(getFirst(), other.getFirst()), Math.min(getSecond(), other.getSecond()));
    }

    public DoubleTupel max(double first, double second) {
        if(getFirst()>=first && getSecond()>=second){
            return this;
        }
        return new DoubleTupel(Math.max(getFirst(), first), Math.max(getSecond(), second));
    }
    
    public DoubleTupel max(DoubleTupel other) {
        if(bothGreaterEqual(other)){
            return this;
        }else if(other.bothGreaterEqual(this)){
            return other;
        }
        return new DoubleTupel(Math.max(getFirst(), other.getFirst()), Math.max(getSecond(), other.getSecond()));
    }

    public boolean containedIn(Tupel<DoubleTupel, DoubleTupel> box){
        return bothGreaterEqual(box.getFirst()) &&
                bothSmallerEqual(box.getSecond());
    }
    public boolean containedIn(Collection<Tupel<DoubleTupel, DoubleTupel>> boxes){
        for(Tupel<DoubleTupel, DoubleTupel> box : boxes){
            if(containedIn(box)){
                return true;
            }
        }
        return false;
    }

    public boolean bothGreaterEqual(DoubleTupel other){
        return bothGreaterEqual(other.getFirst(), other.getSecond());
    }

    public boolean bothGreaterEqual(double first, double second){
        return getFirst()>=first && getSecond()>=second;
    }

    public boolean anyGreaterEqual(DoubleTupel other){
        return anyGreaterEqual(other.getFirst(), other.getSecond());
    }

    public boolean anyGreaterEqual(double first, double second){
        return getFirst()>=first || getSecond()>=second;
    }

    public boolean bothSmallerEqual(DoubleTupel other){
        return getFirst()<=other.getFirst() && getSecond()<=other.getSecond();
    }

    public boolean anySmallerEqual(DoubleTupel other){
        return getFirst()<=other.getFirst() || getSecond()<=other.getSecond();
    }

    public double sum() {
        return getFirst()+getSecond();
    }

    public boolean equals(double first, double second){
        return getFirst() == first && getSecond() == second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleTupel)) return false;

        DoubleTupel that = (DoubleTupel) o;
        return getFirst().equals(that.getFirst()) &&
                getSecond().equals(that.getSecond());

    }

    public static DoubleTupel parse(String toParse){
        int errorOffset = 0;
        try {
//            System.out.println("toParse: "+toParse);
            String escapedDelimiter = "\\" + DELIMITER;
            StringTupel values = new StringTupel(toParse.split(escapedDelimiter)).remove(" ");
//            System.out.println("values"+ values);
            if (values.getFirst().startsWith(START_STRING) && values.getSecond().endsWith(END_STRING)) {
//                System.out.println("true");
                values = new StringTupel(values.getFirst().substring(1), values.getSecond().substring(0, values.getSecond().length() - 1));
            }
//            System.out.println("length: "+(values.toArray().length));
            return new DoubleTupel(values);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new NumberFormatException("could not parse \""+toParse+"\" to a DoubleTupel!");
        }
    }

    public DoubleTupel mult(double c) {
        return mult(c, c);
    }

    public double length() {
        return Math.sqrt(getFirst()*getFirst()+getSecond()*getSecond());
    }

    public DoubleTupel div(double c) {
        return div(c, c);
    }

    public DoubleTupel signum(){
        return new DoubleTupel(Math.signum(getFirst()), Math.signum(getSecond()));
    }

    public DoubleTupel toLength(double newLength) {
        return mult(newLength/length());
    }
}
