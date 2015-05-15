package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class Coin extends Collectible {
    public Coin(GraphicsObject graphicsObject, DoubleTupel position) {
        super(graphicsObject, position);
    }

    @Override
    public void collect() {
        getWorld().getPlayer().addCoin();
        super.collect();
    }
}
