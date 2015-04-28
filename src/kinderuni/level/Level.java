package kinderuni.level;

import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.Player;
import kinderuni.graphics.Painter;
import kinderuni.graphics.Screen;

/**
 * Created by Georg Plaz.
 */
public class Level {
    private final Screen screen;
    private final double initialAirFriction;
    private final double initialGravity;
    private GameWorld gameWorld;
    private boolean isRunning = false;
    private Thread thread;
    private DoubleTupel initPlayerPosition;

    public Level(DoubleTupel dimensions, Screen screen, double initialAirFriction, double initialGravity, DoubleTupel initPlayerPosition) {
        this.screen = screen;
        this.initialAirFriction = initialAirFriction;
        this.initialGravity = initialGravity;
        this.initPlayerPosition = initPlayerPosition;
        gameWorld = new GameWorld(new ModifiableBox(dimensions.div(2), dimensions), screen.getScreenWidth(), this.initialAirFriction, this.initialGravity);
    }

    public void update() {
        Player player = gameWorld.getPlayer();
        if (player != null) {
            if (screen.goRight()) {
                player.moveRight();
            } else if (screen.goLeft()) {
                player.moveLeft();
            }
            if (screen.jump()) {
                player.jump();
            }
        }
        gameWorld.update();
    }

    public void paint(Painter painter) {
        gameWorld.paint(painter);
    }

    public void start(){
        screen.setLevel(this);
        if(!isRunning) {
            isRunning = true;
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while(true) {
                            update();
                            screen.setCenter(getGameWorld().getPlayer().getCenter());
                            screen.setLives(getGameWorld().getPlayer().getLives());
                            screen.setHp(getGameWorld().getPlayer().getHp());
                            screen.render();
                            Thread.sleep(10);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public GameWorld.State getGameState() {
        return gameWorld.getGameState();
    }

    public void put(Player player){
        player.setCenter(initPlayerPosition);
        getGameWorld().set(player);
    }
}
