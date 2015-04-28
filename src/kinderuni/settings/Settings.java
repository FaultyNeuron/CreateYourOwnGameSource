package kinderuni.settings;

import kinderuni.settings.levelSettings.LevelSettings;

import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class Settings {
    private PlayerSettings playerSettings;
    private List<LevelSettings> levelSettings;

    public PlayerSettings getPlayerSettings() {
        return playerSettings;
    }

    public List<LevelSettings> getLevelSettings() {
        return levelSettings;
    }
}
