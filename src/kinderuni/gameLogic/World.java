package kinderuni.gameLogic;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.ui.graphics.Painter;
import kinderuni.ui.GraphicsComponent;
import kinderuni.gameLogic.objects.*;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.*;

/**
 * Created by Georg Plaz.
 */
public class World {
    /** distance for the objects to move off screen and still be active*/
    private static final double ACTIVE_DISTANCE = 100;
    private double airFriction;
    private double gravity;
    private Box worldBounds;
    private Player player;
    private ModifiableBox activeBoundings;
    private final Set<GameObject> gameObjects = new LinkedHashSet<GameObject>();
    private final Set<SolidObject> solidObjects = new LinkedHashSet<SolidObject>();
    private final Set<Enemy> enemies = new LinkedHashSet<Enemy>();
    private final Set<Collectible> collectibles = new LinkedHashSet<Collectible>();
    private final Set<GameObject> backgroundObjects = new LinkedHashSet<GameObject>();

    private final Set<GameObject> gameObjectsToDestroy = new LinkedHashSet<GameObject>();
    private final Set<SolidObject> solidObjectsToDestroy = new LinkedHashSet<SolidObject>();
    private final Set<Enemy> enemiesToDestroy = new LinkedHashSet<Enemy>();
    private final Set<Collectible> collectiblesToDestroy = new LinkedHashSet<Collectible>();
    private final Set<GameObject> backgroundObjectsToDestroy = new LinkedHashSet<GameObject>();

    private final Set<GameObject> gameObjectsActive = new LinkedHashSet<GameObject>();
    private final Set<SolidObject> solidObjectsActive = new LinkedHashSet<SolidObject>();
    private final Set<Enemy> enemiesActive = new LinkedHashSet<Enemy>();
    private final Set<Collectible> collectiblesActive = new LinkedHashSet<Collectible>();

    public World(Box worldBounds, double screenWidth, double airFriction, double gravity) {
        this.worldBounds = worldBounds;
        this.airFriction = airFriction;
        this.gravity = gravity;
        this.activeBoundings = new FastAccessBox(DoubleTupel.ZEROS,
                new DoubleTupel(screenWidth + ACTIVE_DISTANCE, (screenWidth + ACTIVE_DISTANCE)*2));
    }

    public double getGravity() {
        return gravity;
    }

    public void update(int time){
//        renderScreen.setCenter(player.getCenter());
        if(player!=null){
            activeBoundings.setCenter(player.getCenter());
        }

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

        destroy(gameObjects, gameObjectsActive, gameObjectsToDestroy);
        destroy(solidObjects, solidObjectsActive, solidObjectsToDestroy);
        destroy(enemies, enemiesActive, enemiesToDestroy);
        destroy(collectibles, collectiblesActive, collectiblesToDestroy);
        destroy(backgroundObjects, null, backgroundObjectsToDestroy);
    }

    private <A> void destroy(Set<A> base, Set<A> baseActive, Set<A> toDestroy){
        if(toDestroy.size()>0) {
            synchronized (base) {
                base.removeAll(toDestroy);
            }
            if (baseActive != null) {
                synchronized (baseActive) {
                    baseActive.removeAll(toDestroy);
                }
            }
            toDestroy.clear();
        }
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
        painter.getRenderScreen().setRenderCenter(player.getCenter());
        synchronized (backgroundObjects){
            for(GameObject bgObject : backgroundObjects){
                if(!bgObject.isDestroyed()){
                    painter.paint(bgObject);
                }
            }
        }
        //synchronized (gameObjects){
        for(GameObject gameObject : new LinkedList<>(gameObjects)){
            if(!gameObject.isDestroyed()){
                painter.paint(gameObject);
            }
        }
    }

    public boolean isOnScreen(GameObject toTest, GraphicsComponent graphicsComponent){
        return isOnScreen(toTest.getBoundingBox(), graphicsComponent);
    }
    public boolean isActive(GameObject toTest){
        return isActive(toTest.getBoundingBox());
    }

    public boolean isOnScreen(Box box, GraphicsComponent graphicsComponent){
        return box.collides(graphicsComponent.getScreenArea());
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

    public void add(BackGroundObject toAdd){
        backgroundObjects.add(toAdd);
        //addObject(toAdd);
        toAdd.setWorld(this);
    }

    public void addEnemies(List<Enemy> toAdd){
        for(Enemy enemy : toAdd){
            add(enemy);
        }
    }

    public void addSolids(List<? extends SolidObject> toAdd){
        for(SolidObject solid : toAdd){
            add(solid);
        }
    }

    public void add(SolidObject toAdd){
        solidObjects.add(toAdd);
        addObject(toAdd);
    }

    public void add(Collectible toAdd){
        collectibles.add(toAdd);
        addObject(toAdd);
    }

    public void addObject(GameObject toAdd){
        gameObjects.add(toAdd);
        toAdd.setWorld(this);
    }

    public void set(Player player) {
        this.player = player;
        addObject(player);
    }

//    public void set(Goal goal) {
//        this.goal = goal;
//        addObject(goal);
//    }

    public double getAirFriction() {
        return airFriction;
    }

    public Set<Enemy> getEnemies() {
        return enemies;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
}
