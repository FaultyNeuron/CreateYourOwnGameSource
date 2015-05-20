package kinderuni.ui;

import kinderuni.*;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.components.ButtonComponent;

import java.util.Collection;
import java.util.Collections;

/**
* Created by Georg Plaz.
*/
class EditLevelListener implements ButtonComponent.OnClickListener{
    private LevelSettings levelSettings;
    private kinderuni.System system;
    private Screen screen;

    public EditLevelListener(LevelSettings levelSettings, kinderuni.System system, Screen screen) {
        this.levelSettings = levelSettings;
        this.system = system;
        this.screen = screen;
    }

    @Override
    public void onClick() {
        Util.pushLevelEditToScreen(levelSettings, system, screen);
    }
}
