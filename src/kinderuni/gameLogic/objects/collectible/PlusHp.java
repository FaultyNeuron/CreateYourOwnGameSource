package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.GameWorld;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class PlusHp extends Collectible {
    public PlusHp(GraphicsObject graphicsObject, DoubleTupel position) {
        super(graphicsObject, position);
    }

    @Override
    public void collect() {
        getWorld().getPlayer().heal(1);
        super.collect();
    }
}
