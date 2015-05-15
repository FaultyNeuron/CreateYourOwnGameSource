package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class PlusLife extends Collectible {
    public PlusLife(GraphicsObject graphicsObject, DoubleTupel position) {
        super(graphicsObject, position);
    }

    @Override
    public void collect() {
        getWorld().getPlayer().addLife(1);
        super.collect();
    }
}
