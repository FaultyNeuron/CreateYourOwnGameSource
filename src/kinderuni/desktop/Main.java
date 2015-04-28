package kinderuni.desktop;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.AnimationLogic;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.Screen;
import kinderuni.gameLogic.objects.Enemy;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.Goal;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.solid.MovingPlatform;
import kinderuni.gameLogic.objects.solid.Platform;

import java.lang.System;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class Main {
    static Random random = new Random();
    public static void main(String... args) throws InterruptedException {
        DesktopSystem system = new DesktopSystem();
        Screen screen = system.createScreen();
        Box levelBox = new ModifiableBox(DoubleTupel.ZEROS, new DoubleTupel(2000, 500));
        Box contentBox = levelBox.scaleTo(levelBox.getWidth(), levelBox.getHeight()-75);
        GameWorld world = new GameWorld(levelBox, screen);

        world.set(new Player(new DoubleTupel(levelBox.getLeft()+50, levelBox.getLower() + 50), system.createTextBoxGraphics(50, 50, "player")));
        world.set(new Goal(new DoubleTupel(levelBox.getRight() - 50, levelBox.getLower() + 100), system.createTextBoxGraphics(20, 20, "goal")));

        createPlatforms(world, contentBox, 20, system.createTextBoxGraphics(300, 25, "platform"));
//        world.add(new Platform(world.getPlayer().getCenter().add(100,0), system.createBoxGraphics(25, 200)));
//        world.add(new Platform(world.getPlayer().getCenter().add(-100,0), system.createBoxGraphics(25, 200)));
        createEnemies(world, contentBox.scale(.9, .9), 5, system.createTextBoxGraphics(50, 50, "enemy"));
        screen.setGameWorld(world);
        createFloor(world, levelBox, system);

//        AnimationLogic testAnimationLogic = new AnimationLogic(2, 1, AnimationLogic.LoopType.BACK_AND_FORTH, 5);
//        testAnimationLogic.start();
//        int lastFrame = -1;

        while(world.isRunning()){
            world.update();
            screen.setLives(world.getPlayer().getLives());
            screen.setHp(world.getPlayer().getHp());
            screen.render();
            Thread.sleep(10);
//            int currentFrame = testAnimationLogic.getCurrentFrame();
//            if(currentFrame!=lastFrame){
//                System.out.println("frame: "+currentFrame);
//                lastFrame = currentFrame;
//            }
        }
        if(world.getGameState() == GameWorld.State.WON){
            System.out.println("won!");
        }else if(world.getGameState() == GameWorld.State.LOST){
            System.out.println("lost!");
        }
    }

    public static void createEnemies(GameWorld world, Box bounding, int count, GraphicsObject graphicsObject){
        for(DoubleTupel tupel : create(bounding, count)) {
            world.add(new Enemy(tupel, graphicsObject, 1));
        }
    }

    public static void createFloor(GameWorld world, Box bounding, kinderuni.System system){
        double floor = bounding.getLower();
        double lastEnd = bounding.getLeft();
        while(lastEnd<bounding.getRight()){
            double width = Math.min(bounding.getRight() - lastEnd, random.nextDouble()*300+400);
            world.add(new Platform(new DoubleTupel(lastEnd+width/2, floor), system.createTextBoxGraphics(width, 25, "floor")));
            lastEnd += width+100;
        }
    }

    public static void createPlatforms(GameWorld world, Box bounding, int count, GraphicsObject graphicsObject){
        for(DoubleTupel tupel : create(bounding, count)) {
            if (random.nextBoolean()) {
                world.add(new Platform(tupel, graphicsObject));
            } else {
                world.add(new MovingPlatform(tupel, graphicsObject));
            }
        }
    }

    public static Collection<DoubleTupel> create(Box bounding, int count){
        Collection<DoubleTupel> tupels = new LinkedList<DoubleTupel>();
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * bounding.getWidth() + bounding.getLeft();
            double y = random.nextDouble() * bounding.getHeight() + bounding.getLower();
            tupels.add(new DoubleTupel(x, y));
        }
        return tupels;
    }
}
