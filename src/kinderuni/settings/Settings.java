package kinderuni.settings;

import com.google.gson.Gson;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.levelSettings.objectSettings.BackGroundObjectSettings;
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
    private Map<String, BackGroundObjectSettings> backGroundObjectsSettings;

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
        toReturn.backGroundObjectsSettings = toMap(resources, "back_ground_objects", BackGroundObjectSettings.class);
        return toReturn;
    }

    private static <A> Map<String, A> toMap(File resources, String subFolder, Class<A> classToParse) throws FileNotFoundException {
        Map<String, A> toReturn = new LinkedHashMap<>();
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

    public boolean hasEnemySettings(String id){
        return enemySettings.containsKey(id);
    }

    public boolean hasBackGroundObjectsSettings(String id){
        return backGroundObjectsSettings.containsKey(id);
    }

    public PlatformSettings getPlatformSettings(String id) {
        return platformSettings.get(id);
    }

    public boolean hasPlatformSettings(String id){
        return platformSettings.containsKey(id);
    }

    public CollectibleSettings getCollectibleSettings(String id) {
        return collectibleSettings.get(id);
    }

    public boolean hasCollectibleSettings(String id){
        return collectibleSettings.containsKey(id);
    }

    public LevelSettings getLevelSettings(String id) {
        return levelSettings.get(id);
    }

    public boolean hasLevelSettings(String id){
        return levelSettings.containsKey(id);
    }

    public BackGroundObjectSettings getBackGroundSettings(String id) {
        return backGroundObjectsSettings.get(id);
    }
}
