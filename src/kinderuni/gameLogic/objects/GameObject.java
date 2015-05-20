package kinderuni.gameLogic.objects;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.ui.graphics.Painter;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public interface GameObject {
    public GameWorld getWorld();
    public void setWorld(GameWorld world);

    public DoubleTupel getCenter();
    public void setCenter(DoubleTupel newPosition);

    public void move(DoubleTupel delta);
    public void moveTo(DoubleTupel delta);
    public DoubleTupel getDimensions();
    public Box getBoundingBox();

    public boolean isSolid();

    public void destroy();
    public boolean isDestroyed();
    public DoubleTupel getLastDelta();

    public void initUpdateCycle();
    public void update(int time);
    public void checkCollision();

    void stickToThis(GameObject toStick);
    void unStick(GameObject toUnStick);
    double getFriction();

    public GraphicsObject getGraphics();
    public void paint(Painter painter);

//    public void collidedWithPlayer(Player player, DoubleTupel objectDelta, Orientation collisionSide);
}
