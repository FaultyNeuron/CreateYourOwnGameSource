package kinderuni;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.desktop.DesktopSystem;
import kinderuni.gameLogic.objects.Enemy;
import kinderuni.gameLogic.objects.collectible.*;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Screen;
import kinderuni.gameLogic.objects.Player;
import kinderuni.level.Level;
import kinderuni.level.LevelBuilder;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.PlayerSettings;

import java.lang.System;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class Main {
    static Random random = new Random();
    public static void main(String... args) throws InterruptedException {
        DesktopSystem system = new DesktopSystem();
        Screen screen = system.getScreen();
        InputRetriever inputRetriever = system.getInputRetriever();
        screen.start();
        int levelCounter = 0;
        LevelSettings[] levelSettings = new LevelSettings[]{LevelSettings.STANDARD, LevelSettings.ICE, LevelSettings.SPACE};
        PlayerSettings playerSettings = PlayerSettings.DEFAULT;
        Player player = new Player(
                null,
//                system.createBoxGraphics(50, playerSettings.getHeight()),
                system.createGraphics("player", "jpg", 70, 70),
                playerSettings.getJumpPower(),
                playerSettings.getMoveSpeed(),
                playerSettings.getLives(),
                playerSettings.getHp());
        do {
            Level level = LevelBuilder.build(levelSettings[levelCounter], screen.getScreenWidth(), inputRetriever, system);
            screen.add(level);
            player.stop();
            player.unStick();
            level.put(player);
//            for (int i = 0; i < 50; i++) {
//                level.getGameWorld().add(new GravitySwitch(system.createGraphics("star", "png", 30, 30), player.getCenter().add(100+400*i, 300)));
//            }
            for(Enemy enemy : level.getGameWorld().getEnemies()){
                double random = Math.random();
                if(random > 0.5){
                    enemy.setDrop(new Coin(system.createGraphics("coin", "png", 30, 30), DoubleTupel.ZEROS));
                }else if(random > 0.3){
                    enemy.setDrop(new PlusHp(system.createGraphics("heart", "png", 30, 30), DoubleTupel.ZEROS));
                }else if(random > 0.2){
                    enemy.setDrop(new PlusLife(system.createGraphics("live", "png", 30, 30), DoubleTupel.ZEROS));
                }else if(random > 0.2){
                    enemy.setDrop(new Invincible(system.createGraphics("star", "png", 30, 30), DoubleTupel.ZEROS));
                }else{
                    enemy.setDrop(new PlusSpeed(system.createGraphics("bolt", "png", 30, 30), DoubleTupel.ZEROS));
                }
            }
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
        } while (player.getLifes()>0 && levelCounter<levelSettings.length);


    }
}
