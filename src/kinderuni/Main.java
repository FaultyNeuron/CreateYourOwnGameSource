package kinderuni;

import kinderuni.desktop.DesktopSystem;
import kinderuni.graphics.Screen;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.Player;
import kinderuni.level.Level;
import kinderuni.level.LevelBuilder;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.PlayerSettings;

import java.io.File;
import java.lang.System;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class Main {
    static Random random = new Random();
    public static void main(String... args) throws InterruptedException {
        DesktopSystem system = new DesktopSystem();
        Screen screen = system.createScreen();
        int levelCounter = 0;
        LevelSettings[] levelSettings = new LevelSettings[]{LevelSettings.STANDARD, LevelSettings.ICE, LevelSettings.SPACE};
        PlayerSettings playerSettings = PlayerSettings.DEFAULT;
        Player player = new Player(
                null,
//                system.createBoxGraphics(50, playerSettings.getHeight()),
                system.createGraphics(new File("C:/Users/Georg/Desktop/anim"), 70, 70),
                playerSettings.getJumpPower(),
                playerSettings.getMoveSpeed(),
                playerSettings.getLives(),
                playerSettings.getHp());
        do {
            Level level = LevelBuilder.build(levelSettings[levelCounter], screen, system);
            player.stop();
            player.unStick();
            level.put(player);
            level.start();
            while(level.isRunning()){
                Thread.sleep(10);
                if(level.getGameState() == Level.State.WON){
                    System.out.println("won!");
                    break; //todo next level
                }else if(level.getGameState() == Level.State.LOST){
                    System.out.println("lost!");
                    break;
                }
            }
            level.stop();
            levelCounter++;
        } while (player.getLives()>0 && levelCounter<levelSettings.length);


    }
}
