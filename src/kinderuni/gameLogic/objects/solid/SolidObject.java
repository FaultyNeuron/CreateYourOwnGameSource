package kinderuni.gameLogic.objects.solid;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.objects.AbstractGameObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class SolidObject extends AbstractGameObject {
    protected SolidObject(DoubleTupel position, GraphicsObject graphicsObject, GameWorld gameWorld) {
        super(position, graphicsObject, gameWorld);
        gameWorld.add(this);
    }

    protected SolidObject(DoubleTupel position, GraphicsObject graphicsObject) {
        super(position, graphicsObject);
    }

    @Override
    public void destroy() {
        super.destroy();
        getWorld().destroySolid(this);
    }

    @Override
    public void update(int time) {

    }


    @Override
    public void move(DoubleTupel delta) {
        super.move(delta);
    }


    @Override
    public boolean isSolid() {
        return true;
    }
}
