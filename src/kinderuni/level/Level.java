package kinderuni.level;

import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.Goal;
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
    private int time = 0;
    private Goal goal;
    private State state;

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

        if(state == Level.State.IN_PROGRESS && getGameWorld().getPlayer()!=null){
            if(getGameWorld().getPlayer().getLives() <= 0){
                state = Level.State.LOST;
            }else if((goal!=null && goal.getBoundingBox().collides(getGameWorld().getPlayer().getBoundingBox())) ||
                    screen.skipLevelAndConsume()){
                state = Level.State.WON;
            }
        }
        gameWorld.update(time++);
    }

    public void paint(Painter painter) {
        gameWorld.paint(painter);
    }

    public void start(){
        if(!isRunning) {
            state = State.IN_PROGRESS;
            screen.setLevel(this);
            isRunning = true;
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while(isRunning) {
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

    public State getGameState() {
        return state;
    }

    public void put(Player player){
        player.setCenter(initPlayerPosition);
        getGameWorld().set(player);
    }

    public void set(Goal goal) {
        this.goal = goal;
        gameWorld.addObject(goal);
    }

    public void stop() {
        isRunning = false;
    }

    public enum State{
        IN_PROGRESS, WON, LOST
    }
}
