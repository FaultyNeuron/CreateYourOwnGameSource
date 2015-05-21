package kinderuni.ui;

import kinderuni.gameLogic.objects.Player;
import kinderuni.level.builder.LevelBuilder;
import kinderuni.level.LevelSequence;
import kinderuni.level.builder.PlayerBuilder;
import kinderuni.settings.PlayerSettings;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.components.Component;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class Util {
    public static void pushLevelsToScreen(kinderuni.System system, Screen screen){
        pushLevelsToScreen(system.getSettings().getLevelSettings().values(), system, screen);
    }

    public static void pushLevelsToScreen(Collection<LevelSettings> levelSettings, kinderuni.System system, final Screen screen){
        GraphicsComponent graphicsComponent = screen.createGraphicsComponent(screen.getCompSize());
        LevelSequence levels = setupLevelSequence(levelSettings, system, screen, graphicsComponent);
        graphicsComponent.add(levels);
        screen.push(graphicsComponent);
        graphicsComponent.start();
        levels.add(new LevelSequence.FinishedListener() {
            @Override
            public void finished() {
                screen.back();
            }
        });
        levels.start();

    }

    public static LevelSequence setupLevelSequence(Collection<LevelSettings> levelSettings, kinderuni.System system, Screen screen, Component forComp){
        PlayerBuilder playerBuilder = new PlayerBuilder(system, new Random());
        PlayerSettings playerSettings = system.getSettings().getPlayerSettings();
        Player player = playerBuilder.build(playerSettings);

        LevelBuilder levelBuilder = new LevelBuilder(screen.getCompSize().getFirst(), system, screen.createInputRetriever());
        LevelSequence levels = new LevelSequence(levelSettings, player, levelBuilder);
        return levels;
    }

    public static void pushLevelEditToScreen(LevelSettings levelSettings, kinderuni.System system, Screen screen) {

    }
}
