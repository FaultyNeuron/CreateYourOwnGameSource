package kinderuni.gameLogic.objects.solid;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.GraphicsObject;
import kinderuni.gameLogic.objects.AbstractGameObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class SolidObject extends AbstractGameObject {
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
