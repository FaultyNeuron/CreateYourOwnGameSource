package kinderuni.gameLogic.objects;

import functionalJava.data.Direction2D;
import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.ModifiableBox;
import functionalJava.data.tupel.DoubleTupel;
import functionalJava.data.Axis;
import functionalJava.data.tupel.SymTupel;
import functionalJava.data.tupel.Tupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.graphics.Painter;
import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.solid.SolidObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Georg Plaz.
 */
public abstract class AbstractGameObject implements GameObject {
    private Set<GameObject> sticking;
    private GraphicsObject graphicsObject;
    private GameWorld gameWorld;
    private ModifiableBox boundingBox;
    private boolean isDestroyed = false;
    private SolidObject stickingTo;
    private DoubleTupel lastDelta = DoubleTupel.ZEROS;

    protected AbstractGameObject(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        this(position, graphicsObject);
        setWorld(gameWorld);
    }

    protected AbstractGameObject(DoubleTupel position, GraphicsObject graphicsObject) {
        this.graphicsObject = graphicsObject;
        boundingBox = new ModifiableBox(position, graphicsObject.getDimensions());
    }

    public SolidObject getStickingTo() {
        return stickingTo;
    }

    public void initUpdateCycle(){
        lastDelta = DoubleTupel.ZEROS;
    }

    public void update(int time){
    }

    @Override
    public void paint(Painter painter) {
        painter.paint(this);
    }

    @Override
    public void setCenter(DoubleTupel position) {
        boundingBox.setCenter(position);
    }

    @Override
    public DoubleTupel getCenter() {
        return boundingBox.getCenter();
    }

    @Override
    public DoubleTupel getDimensions() {
        return boundingBox.getDimensions();
    }

    public GameWorld getWorld() {
        return gameWorld;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        gameWorld.destroy(this);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void checkCollision() {
        //do nothing
    }

    @Override
    public GraphicsObject getGraphics() {
        return graphicsObject;
    }

    @Override
    public Box getBoundingBox() {
        return boundingBox;
    }

    public void move(DoubleTupel delta){
        if(sticking!=null){
            for(GameObject stick : sticking){
                stick.move(delta);
            }
        }
        boundingBox.setCenter(getCenter().add(delta));
        lastDelta = lastDelta.add(delta);
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public DoubleTupel getLastDelta() {
        return lastDelta;
    }

    public static <A extends GameObject> SymTupel<Tupel<A, Double>> firstCollideAfterMoving(Box newPosition, DoubleTupel moved, Collection<A> colliding){
        if(colliding.isEmpty()){
            return null;
        }
        Tupel<A, Double> firstX = null;
        Tupel<A, Double> firstY = null;
        double maxDeltaLengthX = 0;
        double maxDeltaLengthY = 0;
        for(A collidingObject : colliding){
            Tupel<DoubleTupel, Direction2D> collision = collideAfterMoving(newPosition, moved, collidingObject);
            Axis axis = collision.getSecond().toAxis();
            DoubleTupel delta = collision.getFirst();
            double deltaLength = delta.length();
            if(axis == Axis.VERTICAL && deltaLength > maxDeltaLengthX){
                firstY = new Tupel<A, Double>(collidingObject, delta.getSecond());
                maxDeltaLengthX = deltaLength;
            }else if(axis == Axis.HORIZONTAL && deltaLength > maxDeltaLengthY){
                firstX = new Tupel<A, Double>(collidingObject, delta.getFirst());
                maxDeltaLengthY = deltaLength;
            }
        }
        return new SymTupel<Tupel<A, Double>>(firstX, firstY);
    }

    public static Tupel<DoubleTupel, Direction2D> collideAfterMoving(Box newPosition, DoubleTupel moved, GameObject gameObject){
        Box collisionBox = gameObject.getBoundingBox();
        DoubleTupel relativeMovement = moved.sub(gameObject.getLastDelta());

        double deltaX;
        if(relativeMovement.getFirst() > 0) {
            deltaX = collisionBox.getLeft() - newPosition.getRight();
        }else{
            deltaX = collisionBox.getRight() - newPosition.getLeft();
        }
        double deltaY;
        if (relativeMovement.getSecond() > 0){
            deltaY = collisionBox.getLower() - newPosition.getUpper();
        }else{
            deltaY = collisionBox.getUpper() - newPosition.getLower();
        }
        DoubleTupel deltaTimes = new DoubleTupel(deltaX/relativeMovement.getFirst(), deltaY/relativeMovement.getSecond());

        Axis collisionAxis = Math.abs(deltaTimes.getFirst()) < Math.abs(deltaTimes.getSecond()) ? Axis.HORIZONTAL: Axis.VERTICAL;
        DoubleTupel delta = relativeMovement.mult(deltaTimes.get(collisionAxis));
        Direction2D collisionSide = Direction2D.getDirection(collisionAxis, relativeMovement.get(collisionAxis) > 0);

        return new Tupel<DoubleTupel, Direction2D>(delta, collisionSide);
    }

    @Override
    public void stickToThis(GameObject toStick){
        if(sticking==null){
            sticking=new HashSet<GameObject>();
        }
        sticking.add(toStick);
    }

    @Override
    public void unStickFromThis(GameObject toUnStick){
        sticking.remove(toUnStick);
        if(sticking.size()==0){
            sticking=null;
        }
    }

    public void unStick(){
        if(stickingTo!=null){
            stickingTo.unStickFromThis(this);
        }
        stickingTo = null;
    }

    public void stickToBase(SolidObject base){
        if(getStickingTo()!=base) {
            unStick();
            stickingTo = base;
            base.stickToThis(this);
        }
    }

    public boolean hasSticking(){
        return sticking!=null && !sticking.isEmpty();
    }

    public boolean isSticking(){
        return stickingTo!=null;
    }

    @Override
    public double getFriction() {
        return Double.POSITIVE_INFINITY;
    }

    public void setWorld(GameWorld world) {
        this.gameWorld = world;
    }
}
