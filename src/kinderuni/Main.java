package kinderuni;

import kinderuni.desktop.DesktopSystem;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Screen;
import kinderuni.gameLogic.objects.Player;
import kinderuni.level.Level;
import kinderuni.level.LevelBuilder;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.PlayerSettings;

import java.io.FileNotFoundException;
import java.lang.System;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class Main {
    static Random random = new Random();
    public static void main(String... args) throws InterruptedException, FileNotFoundException {
        DesktopSystem system = new DesktopSystem();
        Screen screen = system.getScreen();
        InputRetriever inputRetriever = system.getInputRetriever();
        screen.start();
        int levelCounter = 0;
        LevelSettings[] levelSettings = new LevelSettings[]{system.getSettings().getLevelSettings("standard"), system.getSettings().getLevelSettings("ice"), system.getSettings().getLevelSettings("space")};
        PlayerSettings playerSettings = system.getSettings().getPlayerSettings();
        Player player = new Player(
                system.createGraphics(playerSettings.getGraphicsSettings()),
                playerSettings.getJumpPower(), playerSettings.getMoveSpeed(), playerSettings.getEnemyThrowbackPower(),
                playerSettings.getHp(), playerSettings.getLife()
        );

        do {
            Level level = LevelBuilder.build(levelSettings[levelCounter], screen.getScreenWidth(), inputRetriever, system);
            screen.add(level);
            player.stop();
            player.unStick();
            level.put(player);
            level.start();
            while(level.isRunning()){
                Thread.sleep(10);
                if(level.getGameState() == Level.State.WON){
                    System.out.println("won!");
                    break;
                }else if(level.getGameState() == Level.State.LOST){
                    System.out.println("lost!");
                    break;
                }
            }
            screen.remove(level);
            level.stop();
            levelCounter++;
        } while (player.getLifeCount()>0 && levelCounter<levelSettings.length);


    }
}
