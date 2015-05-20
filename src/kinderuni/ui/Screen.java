package kinderuni.ui;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.graphics.InputRetriever;
import kinderuni.ui.components.ButtonComponent;
import kinderuni.ui.components.Component;
import kinderuni.ui.components.TextComponent;

import java.util.Collection;

/**
 * Created by Georg Plaz.
 */
public interface Screen {
//    public void add(Component component);
    public ButtonComponent createButton(String text);
    public TextComponent createText(String text);
    public DoubleTupel getCompSize();

    public InputRetriever createInputRetriever();

    public GraphicsComponent createGraphicsComponent(DoubleTupel dimensions);
//    public LevelsComponent createLevelsComponent(Collection<LevelSettings> levelSettings);
    public Component createLevelOverview(Collection<LevelSettings> values);

    public void push(Component component);

    void back();

    public SystemContainer createSystemContainer();

}
