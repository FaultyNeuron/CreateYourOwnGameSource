package kinderuni.settings;

import com.google.gson.Gson;
import javafx.util.Pair;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.levelSettings.objectSettings.CollectibleSettings;
import kinderuni.settings.levelSettings.objectSettings.EnemySettings;
import kinderuni.settings.levelSettings.objectSettings.PlatformSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Georg Plaz.
 */
public class Settings {
    private static Gson gson = new Gson();
    private PlayerSettings playerSettings;
    private Map<String, LevelSettings> levelSettings = new LinkedHashMap<>();
    private Map<String, EnemySettings> enemySettings = new HashMap<>();
    private Map<String, CollectibleSettings> collectibleSettings = new HashMap<>();
    private Map<String, PlatformSettings> platformSettings = new HashMap<>();

    public PlayerSettings getPlayerSettings() {
        return playerSettings;
    }

    public Map<String, LevelSettings> getLevelSettings() {
        return levelSettings;
    }

    public static Settings read(File resources) throws FileNotFoundException {
        Settings toReturn = new Settings();
        toReturn.playerSettings = readObject(new File(resources, "player.json"), PlayerSettings.class);
        toReturn.levelSettings = toMap(resources, "levels", LevelSettings.class);
        for(Map.Entry<String, LevelSettings> levelSetting : toReturn.levelSettings.entrySet()){
            levelSetting.getValue().setLevelName(levelSetting.getKey());
        }
        toReturn.collectibleSettings = toMap(resources, "collectibles", CollectibleSettings.class);
        toReturn.enemySettings = toMap(resources, "enemies", EnemySettings.class);
        toReturn.platformSettings = toMap(resources, "platforms", PlatformSettings.class);
        return toReturn;
    }

    private static <A> Map<String, A> toMap(File resources, String subFolder, Class<A> classToParse) throws FileNotFoundException {
        Map<String, A> toReturn = new HashMap<>();
        for(File file : new File(resources, subFolder).listFiles()){
            String fileName = file.getName();
            toReturn.put(fileName.substring(0, fileName.length()-5), readObject(file, classToParse));
        }
        return toReturn;
    }

    private static <A> A readObject(File file, Class<A> classToParse) throws FileNotFoundException {
        return gson.fromJson(new FileReader(file), classToParse);
    }

    public EnemySettings getEnemySettings(String id) {
        return enemySettings.get(id);
    }

    public PlatformSettings getPlatformSettings(String id) {
        return platformSettings.get(id);
    }

    public CollectibleSettings getCollectibleSettings(String id) {
        return collectibleSettings.get(id);
    }

    public LevelSettings getLevelSettings(String id) {
        if(!levelSettings.containsKey(id)){
            throw new RuntimeException("level with id \""+id+"\" doesn't exist!");
        }
        return levelSettings.get(id);
    }
}
