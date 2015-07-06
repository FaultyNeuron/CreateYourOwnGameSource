package kinderuni.gameLogic.objects.solid;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.AbstractGameObject;

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
        super.update(time);
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
