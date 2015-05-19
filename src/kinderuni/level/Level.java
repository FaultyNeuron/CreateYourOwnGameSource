package kinderuni.level;

import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.desktop.Info;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.Goal;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.ReversibleEffect;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.InputRetriever;
import kinderuni.graphics.Paintable;
import kinderuni.graphics.Painter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class Level implements Paintable {
//    private Screen screen;
    private InputRetriever inputRetriever;
    private double initialAirFriction;
    private double initialGravity;
    private GameWorld gameWorld;
    private boolean isRunning = false;
    private Thread thread;
    private DoubleTupel initPlayerPosition;
    private long time = 0;
    private Goal goal;
    private State state;
    private String name;
    private DoubleTupel dimensions;
    private GraphicsObject heartGraphics;
    private GraphicsObject playerGraphics;

    public Level(String name, DoubleTupel dimensions,
                 double screenWidth, InputRetriever inputRetriever,
                 double initialAirFriction, double gravity,
                 DoubleTupel initPlayerPosition, kinderuni.System system) {
        heartGraphics = system.createGraphics("heart", 25, 25);
        playerGraphics = system.createGraphics("player", 50, 50);
        this.name = name;
//        this.screen = screen;
        this.inputRetriever = inputRetriever;
        this.initialAirFriction = initialAirFriction;
        this.initialGravity = gravity;
        this.initPlayerPosition = initPlayerPosition;
        gameWorld = new GameWorld(new ModifiableBox(dimensions.div(2), dimensions), screenWidth, this.initialAirFriction, this.initialGravity);
    }

    public void update() {
        Player player = gameWorld.getPlayer();
        if (player != null) {
            if (inputRetriever.goRight()) {
                player.moveRight();
            } else if (inputRetriever.goLeft()) {
                player.moveLeft();
            }
            if (inputRetriever.jump()) {
                player.jump();
            }
        }

        if(state == Level.State.IN_PROGRESS && getGameWorld().getPlayer()!=null){
            if(getGameWorld().getPlayer().getLifeCount() <= 0){
                state = Level.State.LOST;
            }else if((goal!=null && goal.getBoundingBox().collides(getGameWorld().getPlayer().getBoundingBox())) ||
                    inputRetriever.skipLevelAndConsume()){
                state = Level.State.WON;
            }
        }
        gameWorld.update((int)time++);
    }

    public void paint(Painter painter) {
        DoubleTupel heartDim = heartGraphics.getDimensions();
        double iconDelta = heartDim.max();
        gameWorld.paint(painter);
        double left = -painter.getRenderScreen().getScreenWidth()/2;
        double top = painter.getRenderScreen().getScreenHeight()/2;
        for (int i = 0; i < getGameWorld().getPlayer().getHp(); i++) {
            painter.paint(heartGraphics, new DoubleTupel(left + iconDelta + i*(heartDim.getFirst()+iconDelta), top-iconDelta));
        }
        List<ReversibleEffect> effects = getGameWorld().getPlayer().getActiveEffects();
        double pos = left + iconDelta;
        for (Effect effect : effects) {
            GraphicsObject graphic = effect.getGraphics();
            if (graphic != null){
                painter.paint(graphic, new DoubleTupel(pos, top - iconDelta*2 - heartDim.getSecond()));
                pos += graphic.getDimensions().getFirst() + iconDelta;
            }
        }
    }

    @Override
    public List<Info> getInfos() {
        List<Info> toReturn = new LinkedList<>();
        toReturn.add(new Info("level", name));
        toReturn.add(new Info("lives", String.valueOf(getGameWorld().getPlayer().getLifeCount())));
        toReturn.add(new Info("coins", String.valueOf(getGameWorld().getPlayer().getCoins())));
        return toReturn;
    }

    public void start(){
        if(!isRunning) {
            state = State.IN_PROGRESS;
//            screen.setLevel(this);
            isRunning = true;
            thread = new Thread() {
                @Override
                public void run() {
                    while(isRunning) {
                        try {
                            update();
//                            screen.setCenter(getGameWorld().getPlayer().getCenter());
//                            screen.setLives(getGameWorld().getPlayer().getLives());
//                            screen.setHp(getGameWorld().getPlayer().getHp());
//                            screen.setLevelName(Level.this.getName());
//                            screen.render();
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
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
        player.setSpawnPoint(initPlayerPosition);
        getGameWorld().set(player);
    }

    public void set(Goal goal) {
        this.goal = goal;
        gameWorld.addObject(goal);
    }

    public void stop() {
        isRunning = false;
        thread.interrupt();
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public enum State{
        IN_PROGRESS, WON, LOST
    }
}
