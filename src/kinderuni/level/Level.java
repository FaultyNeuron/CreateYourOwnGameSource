package kinderuni.level;

import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.desktop.Info;
import kinderuni.gameLogic.World;
import kinderuni.gameLogic.objects.Goal;
import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.ReversibleEffect;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.ui.graphics.InputRetriever;
import kinderuni.ui.graphics.Paintable;
import kinderuni.ui.graphics.Painter;

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
    private World world;
//    private boolean isRunning = false;
//    private Thread thread;
    private DoubleTupel initPlayerPosition;
    private long time = 0;
    private Goal goal;
    private State state;
    private String name;
    private DoubleTupel dimensions;
    private GraphicsObject heartGraphics;
    private GraphicsObject playerGraphics;
    private Effect activeEffect;
    private List<LevelStateListener> levelStateListeners = new LinkedList<>();

    public Level(String name, DoubleTupel dimensions,
                 double screenWidth, InputRetriever inputRetriever,
                 double initialAirFriction, double gravity,
                 DoubleTupel initPlayerPosition, kinderuni.System system,
                 Effect activeEffect) {
        this.activeEffect = activeEffect; // todo: add listeners
        heartGraphics = system.createGraphics("heart", 25, 25);
        playerGraphics = system.createGraphics("player", 50, 50);
        this.name = name;
        changeState(State.IN_PROGRESS);

//        this.screen = screen;
        this.inputRetriever = inputRetriever;
        this.initialAirFriction = initialAirFriction;
        this.initialGravity = gravity;
        this.initPlayerPosition = initPlayerPosition;
        world = new World(new ModifiableBox(dimensions.div(2), dimensions), screenWidth, this.initialAirFriction, this.initialGravity);
        if(activeEffect!=null){
            addLevelStateListener(new LevelStateListener() {
                @Override
                public void stateChanged(State newState, State oldState) {
                }
            });
        }
    }

    public void update(int time) {
        this.time = time;
        Player player = world.getPlayer();
        if (player != null) {
            if (inputRetriever.right()) {
                player.moveRight();
            } else if (inputRetriever.left()) {
                player.moveLeft();
            }
            if (inputRetriever.up()) {
                player.jump();
            }

            if (inputRetriever.action()) {
                player.shoot();
            }
        }

        if(state == Level.State.IN_PROGRESS && getWorld().getPlayer()!=null){
            if(getWorld().getPlayer().getLifeCount() <= 0){
                changeState(Level.State.LOST);
            }else if((goal!=null && goal.getBoundingBox().collides(getWorld().getPlayer().getBoundingBox())) ||
                    inputRetriever.skipLevelAndConsume()){
                System.out.println("skipping level");
                changeState(Level.State.WON);
            }
        }
        world.update(time);
    }

    private void changeState(State newState){
        if(newState!=state) {
            State oldState = state;
            state = newState;
            for (LevelStateListener listener : levelStateListeners) {
                listener.stateChanged(newState, oldState);
            }
        }
    }

    public void paint(Painter painter) {
        DoubleTupel heartDim = heartGraphics.getDimensions();
        double iconDelta = heartDim.max();
        world.paint(painter);
        double left = -painter.getRenderScreen().getCompSize().getFirst()/2;
        double top = painter.getRenderScreen().getCompSize().getSecond()/2;
        for (int i = 0; i < getWorld().getPlayer().getHp(); i++) {
            painter.paint(heartGraphics, new DoubleTupel(left + iconDelta + i*(heartDim.getFirst()+iconDelta), top-iconDelta));
        }
        List<ReversibleEffect> effects = getWorld().getPlayer().getActiveEffects();
        double pos = left + iconDelta;
        for (Effect effect : new LinkedList<>(effects)) {
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
        if(world !=null && world.getPlayer()!=null) {
            toReturn.add(new Info("life", String.valueOf(getWorld().getPlayer().getLifeCount())));
            toReturn.add(new Info("coins", String.valueOf(getWorld().getPlayer().getCoins())));
        }
        return toReturn;
    }

//    public void start(){
//        if(!isRunning) {
////            screen.setLevel(this);
//            isRunning = true;
//            thread = new Thread() {
//                @Override
//                public void run() {
//                    while(isRunning) {
//                        try {
//                            update();
////                            screen.setCenter(getGameWorld().getPlayer().getCenter());
////                            screen.setLives(getGameWorld().getPlayer().getLives());
////                            screen.setHp(getGameWorld().getPlayer().getHp());
////                            screen.setLevelName(Level.this.getName());
////                            screen.render();
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                        }
//                    }
//                }
//            };
//            thread.start();
//        }
//    }

    public World getWorld() {
        return world;
    }

//    public boolean isRunning() {
//        return isRunning;
//    }

    public State getGameState() {
        return state;
    }

    public void put(Player player){
        if(activeEffect != null){
            activeEffect.activate(player);
        }
        player.setCenter(initPlayerPosition);
        player.setSpawnPoint(initPlayerPosition);
        getWorld().set(player);
    }

    public void set(Goal goal) {
        this.goal = goal;
        world.addObject(goal);
    }

//    public void stop() {
//        isRunning = false;
////        thread.interrupt();
//        changeState(State.STOPPED);
//    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean tracksTime() {
        return true;
    }

    public enum State{
        IN_PROGRESS, WON, LOST, STOPPED
    }

    public void addLevelStateListener(LevelStateListener listener){
        levelStateListeners.add(listener);
    }

    public interface LevelStateListener{
        public abstract void stateChanged(State newState, State oldState);
    }
}
