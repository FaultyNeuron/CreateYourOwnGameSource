package util;


/**
 * Created by Georg Plaz.
 */
public class TickTock {
    private long tick;
    private long tock;

    public void tick(){
        tick = System.currentTimeMillis();
    }

    public long tock(){
        tock = System.currentTimeMillis();
        return tock-tick;
    }

    public String tockFormatted(){
        return "tock: "+(tock()) + "milliseconds";
    }
}
