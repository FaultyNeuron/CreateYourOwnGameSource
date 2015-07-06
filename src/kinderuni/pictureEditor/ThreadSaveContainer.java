package kinderuni.pictureEditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by markus on 29.06.15.
 */
public class ThreadSaveContainer<A> {
    private ArrayList<A> items = new ArrayList<>();
    public final static int CYCLE_BEGINNING = 0; // TODO use enum
    public final static int CYCLE_REVERSE = 1; // TODO use enum

    // Not thread save.
    public synchronized A get(int index) {
        return items.get(index);
    }

    private synchronized boolean hasNext() {
        return items.size() > 0;
    }

    private synchronized A getNextBeginningMode(int index) {
        if (!hasNext()) {
            return null;
        }
        if (index < items.size()) {
            return items.get(index);
        }
        return items.get(0);
    }

    private synchronized A getNextReverseMode(int index, CycleState state) {
        if (!hasNext()) {
            return null;
        }
        if (index < items.size()) {
            return items.get(index);
        }
        return items.get(items.size()-1);
    }

    public synchronized A getCyclicItem(int index, int mode) {
        if (hasNext()) {
            switch (mode) {
                case CYCLE_BEGINNING: return getNextBeginningMode(index);
//                case CYCLE_REVERSE: return getNextReverseMode(index);
//                default: throw new IllegalArgumentException("Unknown cycle mode: " + mode);
            }
        }
        return null;
    }

    public synchronized void remove(int index) {
        items.remove(index);
    }

    public synchronized void remove(A instance) {
        items.remove(instance);
    }

    public class CycleState {
        private int cycleMode;
        private final int STATE_UP = 0; // TODO use enum
        private final int STATE_DOWN = 1; // TODO use enum
        private int state;

        public CycleState(int cycleMode) {
            this.cycleMode = cycleMode;
            this.state = STATE_UP;
        }

    }
}
