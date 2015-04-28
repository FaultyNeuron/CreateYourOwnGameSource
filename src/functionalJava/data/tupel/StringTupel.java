package functionalJava.data.tupel;


import java.util.regex.Pattern;


/**
 * Created by Georg Plaz
 */
public class StringTupel extends SymTupel<String> {
    public static final StringTupel EMPTY_TUPEL = new StringTupel("", "");

    public StringTupel(String[] values) {
        super(values);
    }

    public StringTupel(String first, String second) {
        super(first, second);
    }

    public StringTupel(Tupel<String,String> tupel) {
        super(tupel.getFirst(),tupel.getSecond());
    }

    public StringTupel(String s) {
        super(s, s);
    }

    public StringTupel trim(){
        String firstTrimmed = getFirst().trim();
        String secondTrimmed = getSecond().trim();
        if(firstTrimmed.equals(getFirst()) && secondTrimmed.equals(getSecond())){
            return this;
        }else{
            return new StringTupel(getFirst().trim(), getSecond().trim());
        }
    }
    public StringTupel toLowerCase(){
        String firstLower = getFirst().toLowerCase();
        String secondLower = getSecond().toLowerCase();
        if(getFirst().equals(firstLower) && getSecond().equals(secondLower)){
            return this;
        }
        return new StringTupel(firstLower, secondLower);
    }

    public boolean equals(){
        return getFirst().equals(getSecond());
    }

    public IntTupel length(){
        return new IntTupel(getFirst().length(),getSecond().length());
    }

    public BooleanTupel isEmpty(){
        return BooleanTupel.get(getFirst().isEmpty(), getSecond().isEmpty());
    }

    public StringTupel remove(String toRemove) {
        if(anyContains(toRemove)){
            return new StringTupel(remove(getFirst(), toRemove), remove(getSecond(), toRemove));
        }else{
            return this;
        }
    }

    public StringTupel remove(String... toRemove) {
        String first = getFirst();
        String second = getSecond();
        boolean changed = false;
        for(String s:toRemove) {
            if (first.contains(s) || second.contains(s)) {
                first = remove(first, s);
                second = remove(second, s);
                changed = true;
            }
        }
        if(!changed) {
            return this;
        }else{
            return new StringTupel(first, second);
        }
    }

    @Override
    public StringTupel swap(){
        return new StringTupel(getSecond(), getFirst());
    }


    public StringTupel replace(char oldChar, char newChar) {
        return new StringTupel(getFirst().replace(oldChar, newChar), getSecond().replace(oldChar, newChar));
    }

    public StringTupel replace(String regex, String replacement) {
        return new StringTupel(getFirst().replace(regex, replacement), getSecond().replace(regex, replacement));
    }

    public boolean bothContain(String s) {
        return getFirst().contains(s) && getSecond().contains(s);
    }

    public boolean anyContains(String s) {
        return getFirst().contains(s) || getSecond().contains(s);
    }

    public boolean containsOther() {
        return getFirst().contains(getSecond()) || getSecond().contains(getFirst());
    }

    public boolean bothEndWith(String s) {
        return getFirst().endsWith(s) && getSecond().endsWith(s);
    }

    public boolean anyEndsWith(String s) {
        return getFirst().endsWith(s) || getSecond().endsWith(s);
    }

    public boolean bothStartWith(String s) {
        return getFirst().startsWith(s) && getSecond().startsWith(s);
    }

    public boolean anyStartsWith(String s) {
        return getFirst().startsWith(s) || getSecond().startsWith(s);
    }

    public boolean bothContainWholeWord(String toTest){
        return containsWholeWord(getFirst(), toTest) &&
                containsWholeWord(getSecond(), toTest);
    }

    public boolean anyContainsWholeWord(String toTest){
        return containsWholeWord(getFirst(), toTest) ||
                containsWholeWord(getSecond(), toTest);
    }

    public boolean bothEqual(String s) {
        return getFirst().equals(s) && getSecond().equals(s);
    }

    public boolean anyEquals(String s) {
        return getFirst().equals(s) || getSecond().equals(s);
    }

    public BooleanTupel contains(Tupel<String,String> other) {
        return BooleanTupel.get(getFirst().contains(other.getFirst()), getSecond().contains(other.getSecond()));
    }

    public boolean bothContain(Tupel<String,String> other) {
        return getFirst().contains(other.getFirst()) && getSecond().contains(other.getSecond());
    }

    public boolean anyContains(Tupel<String,String> other) {
        return getFirst().contains(other.getFirst()) || getSecond().contains(other.getSecond());
    }

    public boolean containsOtherWholeWord() {
        return containsWholeWord(getSecond(), getFirst()) || containsWholeWord(getFirst(), getSecond());
    }

    public boolean bothIsEmpty() {
        return getFirst().isEmpty() && getSecond().isEmpty();
    }

    public boolean anyIsEmpty() {
        return getFirst().isEmpty() || getSecond().isEmpty();
    }

    public StringTupel format(String... args) {
        return new StringTupel(
                String.format(getFirst(), args),
                String.format(getSecond(), args));
    }

    public StringTupel format(Tupel<String, String> location) {
        return new StringTupel(String.format(getFirst(), location.getFirst()),
                String.format(getSecond(), location.getSecond()));
    }


    public StringTupel substring(IntTupel beginIndex, IntTupel endIndex){
        return new StringTupel(getFirst().substring(beginIndex.getFirst(),endIndex.getFirst()),
                getSecond().substring(beginIndex.getSecond(),endIndex.getSecond()));
    }

    public IntTupel lastIndicesOf(String s){
        return new IntTupel(getFirst().lastIndexOf(s),getSecond().lastIndexOf(s));
    }

    public static String remove(String from, String toRemove){
        return Pattern.compile(Pattern.quote(toRemove)).matcher(from).replaceAll("");
    }

    public boolean equals(StringTupel other){
        return getFirst().equals(other.getFirst()) && getSecond().equals(other.getSecond());
    }

    @Override
    public String toString(){
        return START+"\""+ getFirst()+"\""+DELIMITER+" \""+getSecond()+"\""+END;
    }

    public StringTupel split(String regex, int index){
        return new StringTupel(getFirst().split(regex)[index],getSecond().split(regex)[index]);
    }

    public static boolean containsWholeWord(String lookIn, String lookFor) {
        StringBuilder builder = new StringBuilder(" ").append(lookFor);
        return lookIn.endsWith(builder.toString()) ||
                lookIn.contains(builder.append(" ").toString()) ||
                lookIn.startsWith(builder.substring(1)) ||
                lookIn.equals(lookFor);
    }
}
