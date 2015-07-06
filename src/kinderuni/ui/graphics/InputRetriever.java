package kinderuni.ui.graphics;

/**
 * Created by Georg Plaz.
 */
public interface InputRetriever {
    boolean right();

    boolean left();

    boolean up();

    boolean down();

    boolean action();

    public boolean skipLevelAndConsume();

}
