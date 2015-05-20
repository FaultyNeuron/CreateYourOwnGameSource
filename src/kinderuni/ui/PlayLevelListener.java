package kinderuni.ui;

import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.components.ButtonComponent;

import java.util.Collection;
import java.util.Collections;

/**
* Created by Georg Plaz.
*/
class PlayLevelListener implements ButtonComponent.OnClickListener{
    private Collection<LevelSettings> levelSettings;
    private kinderuni.System system;
    private Screen screen;

    PlayLevelListener(LevelSettings levelSettings, kinderuni.System system, Screen screen) {
        this(Collections.singleton(levelSettings), system, screen);
    }
    private PlayLevelListener(Collection<LevelSettings> levelSettings, kinderuni.System system, Screen screen) {
        this.levelSettings = levelSettings;
        this.system = system;
        this.screen = screen;
    }

    @Override
    public void onClick() {
        Util.pushLevelsToScreen(levelSettings, system, screen);
    }
}
