package kinderuni.ui.graphics;

import kinderuni.desktop.Info;

import java.util.List;

/**
 * Created by Georg Plaz.
 */
public interface Paintable {
    public void paint(Painter painter);
    public List<Info> getInfos();
    public long getTime();
    public boolean tracksTime();
}
