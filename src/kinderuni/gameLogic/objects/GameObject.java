package kinderuni.gameLogic.objects;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.solid.SolidObject;
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

    SolidObject getStickingTo();

    public void initUpdateCycle();
    public void update(int time);
    public void checkCollision();

    void stickToThis(GameObject toStick);
    void unStick(GameObject toUnStick);

    void unStick();

    void stickToBase(SolidObject base);

    boolean hasSticking();

    boolean isSticking();

    double getFriction();

    public GraphicsObject getGraphics();
    public void paint(Painter painter);

    public void setGraphics(GraphicsObject graphics);

    void setBounding(DoubleTupel dimensions);

    boolean hasGraphics();

    public int getLastTimeMeasured();

    public HorizontalDirection getDirection();

//    public void collidedWithPlayer(Player player, DoubleTupel objectDelta, Orientation collisionSide);
}
