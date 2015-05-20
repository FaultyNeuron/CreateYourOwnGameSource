package kinderuni.ui;

import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.components.ButtonComponent;

import java.util.Collection;

/**
 * Created by Georg Plaz.
 */
public class LevelOverview extends Container{
    public LevelOverview(final kinderuni.System system, final Screen screen, Collection<LevelSettings> values){
        super(screen);
        add(screen.createText("Wähle ein Level zum Spielen/Bearbeiten aus:"));
        breakLine();
        for(LevelSettings levelSettings : values){
            add(screen.createText("level " + levelSettings.getLevelName()));
            ButtonComponent playButton = screen.createButton("spielen");
            playButton.addListener(new PlayLevelListener(levelSettings, system, screen));
            add(playButton);
            ButtonComponent editButton = screen.createButton("bearbeiten");
            editButton.addListener(new EditLevelListener(levelSettings, system, screen));
            add(editButton);
            breakLine();
        }
        ButtonComponent createLevelButton = screen.createButton("erstellen");
        createLevelButton.addListener(new EditLevelListener(null, system, screen));
        add(createLevelButton);
    }
}