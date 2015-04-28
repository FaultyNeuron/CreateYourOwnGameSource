package kinderuni.gameLogic;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.Painter;
import kinderuni.graphics.Screen;
import kinderuni.gameLogic.objects.*;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.*;

/**
 * Created by Georg Plaz.
 */
public class GameWorld {
    private static final double activeDistance = 100;
//    private double airResistance = 0.99;
    private double airFriction = 0.01;
    private double gravity = 0.1;
    private Box worldBounds;
    private Goal goal;
    private Player player;
    private Screen renderScreen;
    private ModifiableBox activeBoundings;
    private State gameState;
    private int time=0;

    private final Set<GameObject> gameObjects = new LinkedHashSet<GameObject>();
    private final Set<SolidObject> solidObjects = new LinkedHashSet<SolidObject>();
    private final Set<Enemy> enemies = new LinkedHashSet<Enemy>();
    private final Set<Collectible> collectibles = new LinkedHashSet<Collectible>();

    private final Set<GameObject> gameObjectsToDestroy = new LinkedHashSet<GameObject>();
    private final Set<SolidObject> solidObjectsToDestroy = new LinkedHashSet<SolidObject>();
    private final Set<Enemy> enemiesToDestroy = new LinkedHashSet<Enemy>();
    private final Set<Collectible> collectiblesToDestroy = new LinkedHashSet<Collectible>();

    private final Set<GameObject> gameObjectsActive = new LinkedHashSet<GameObject>();
    private final Set<SolidObject> solidObjectsActive = new LinkedHashSet<SolidObject>();
    private final Set<Enemy> enemiesActive = new LinkedHashSet<Enemy>();
    private final Set<Collectible> collectiblesActive = new LinkedHashSet<Collectible>();

    public GameWorld(Box worldBounds, Screen renderScreen) {
        this.worldBounds = worldBounds;
        this.renderScreen = renderScreen;
        this.activeBoundings = new FastAccessBox(renderScreen.getCenter(),
                new DoubleTupel(renderScreen.getScreenWidth()+activeDistance, Double.POSITIVE_INFINITY));
        gameState = State.RUNNING;
    }

    public double getGravity() {
        return gravity;
    }

    public void update(){
        renderScreen.setCenter(player.getCenter());
        activeBoundings.setCenter(renderScreen.getCenter());

        testActive(gameObjects, gameObjectsActive);
        testActive(solidObjects, solidObjectsActive);
        testActive(enemies, enemiesActive);
        testActive(collectibles, collectiblesActive);
//        testActive(player, gameObjectsActive);

        for(GameObject gameObject : gameObjectsActive){
            if(!gameObject.isDestroyed()){
                gameObject.initUpdateCycle();
            }
        }

        if(player!=null) {
            if (renderScreen.goRight()) {
                player.moveRight();
            } else if (renderScreen.goLeft()) {
                player.moveLeft();
            }
            if (renderScreen.jump()) {
                player.jump();
            }
        }

        for(GameObject gameObject : gameObjectsActive){
            if(!gameObject.isDestroyed()){
                gameObject.update(time);
            }
        }

        for(GameObject gameObject : gameObjectsActive){
            if(!gameObject.isDestroyed()){
                gameObject.checkCollision();
            }
        }

        gameObjects.removeAll(gameObjectsToDestroy);
        synchronized (gameObjectsActive){
            gameObjectsActive.removeAll(gameObjectsToDestroy);
        }
        solidObjects.removeAll(solidObjectsToDestroy);
        solidObjectsActive.removeAll(solidObjectsToDestroy);
        enemies.removeAll(enemiesToDestroy);
        enemiesActive.removeAll(enemiesToDestroy);
        collectibles.removeAll(collectiblesToDestroy);
        collectiblesActive.removeAll(collectiblesToDestroy);

        if(player!=null && gameState == State.RUNNING) {
            if (player.isDestroyed()) {
                gameState = State.LOST;
            } else if (goal!=null && player.getBoundingBox().collides(goal.getBoundingBox())) {
                gameState = State.WON;
            }
        }
        time++;
    }


    public <A extends GameObject> Set<A> collidesWith(Box toTest, Collection<A> others){
        Set<A> toReturn = null;
        for(A other : others){
            if(toTest.collides(other.getBoundingBox())){
                if(toReturn == null){
                    toReturn = new HashSet<A>();
                }
                toReturn.add(other);
            }
        }
        if(toReturn==null){
            return Collections.emptySet();
        }
        return toReturn;
    }

//    public boolean collides(GameObject first, GameObject second){
//        return collides(first.getBoundingBox(), second.getBoundingBox());
//    }
//
//    public boolean collides(Box first, Box second){
//        return first.collides(second);
//    }

    public Set<SolidObject> getSolidObjectsActive() {
        return solidObjectsActive;
    }

    public Set<Enemy> getEnemiesActive() {
        return enemiesActive;
    }

    public Set<Collectible> getCollectiblesActive() {
        return collectiblesActive;
    }

    public void destroy(GameObject toDestroy) {
        synchronized (gameObjectsActive){
            gameObjectsToDestroy.add(toDestroy);
        }
    }

    public void destroySolid(SolidObject toDestroy) {
        solidObjectsToDestroy.add(toDestroy);
    }

    public void destroyEnemy(Enemy toDestroy) {
        enemiesToDestroy.add(toDestroy);
    }

    public void destroyCollectable(Collectible toDestroy) {
        collectiblesToDestroy.add(toDestroy);
    }

    public void paint(Painter painter){
        synchronized (gameObjectsActive){
            for(GameObject gameObject : gameObjectsActive){
                if(isOnScreen(gameObject) && !gameObject.isDestroyed()){
                    gameObject.paint(painter);
                }
            }
        }
    }

    public boolean isOnScreen(GameObject toTest){
        return isOnScreen(toTest.getBoundingBox());
    }
    public boolean isActive(GameObject toTest){
        return isActive(toTest.getBoundingBox());
    }

    public boolean isOnScreen(Box box){
        return box.collides(renderScreen.getScreenArea());
    }
    public boolean isActive(Box box){
        return box.collides(activeBoundings);
    }

    private <A extends GameObject> void testActive(Collection<A> toTest, Collection<A> activeCollection){
        for(A gameObject : toTest){
            testActive(gameObject, activeCollection);
        }
    }

    private <A extends GameObject> void testActive(A gameObject, Collection<A> activeCollection){
        if(gameObject!=null) {
            synchronized (gameObjectsActive) {
                if (!gameObject.isDestroyed() && isActive(gameObject)) {
                    activeCollection.add(gameObject);
                } else {
                    activeCollection.remove(gameObject);
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Box getBounds() {
        return worldBounds;
    }

//    public void createPlayer(DoubleTupel position, GraphicsObject graphicsObject) {
//        set(new Player(graphicsObject, position, this));
//    }
//
//    public void createPlatform(DoubleTupel position, GraphicsObject graphicsObject) {
//        add(new Platform(graphicsObject, position, this));
//    }
//    public void createMovingPlatform(DoubleTupel position, GraphicsObject graphicsObject) {
//        add(new MovingPlatform(graphicsObject, position, this));
//    }

    public void add(Enemy toAdd){
        enemies.add(toAdd);
        addObject(toAdd);
    }

    public void add(SolidObject toAdd){
        solidObjects.add(toAdd);
        addObject(toAdd);
    }

    private void addObject(GameObject toAdd){
        gameObjects.add(toAdd);
        toAdd.setWorld(this);
    }

    public void set(Player player) {
        this.player = player;
        addObject(player);
    }

    public void set(Goal goal) {
        this.goal = goal;
        addObject(goal);
    }

    public double getAirFriction() {
        return airFriction;
    }

    public State getGameState() {
        return gameState;
    }
    public boolean isRunning(){
        return gameState == State.RUNNING;
    }
    public enum State{
        RUNNING, WON, LOST
    }
}
