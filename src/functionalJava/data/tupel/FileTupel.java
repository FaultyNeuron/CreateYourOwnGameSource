package functionalJava.data.tupel;

import java.io.File;

/**
 * Created by Georg Plaz.
 */
public class FileTupel extends SymTupel<File> {
    public FileTupel(SymTupel<File> parent, StringTupel sub) {
        super(new File(parent.getFirst(), sub.getFirst()), new File(parent.getSecond(), sub.getSecond()));
    }

    public FileTupel(SymTupel<File> parent, String sub) {
        super(new File(parent.getFirst(), sub), new File(parent.getSecond(), sub));
    }

    public FileTupel(File parent, StringTupel sub) {
        super(new File(parent, sub.getFirst()), new File(parent, sub.getSecond()));
    }

    public FileTupel(File first, File second) {
        super(first, second);
    }

    public FileTupel(Tupel<File, File> values) {
        super(values);
    }

    public FileTupel(File[] values) {
        super(values);
    }


}
