package kinderuni.gameLogic.objects;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.ui.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class Goal extends AbstractGameObject {
    public Goal() {
    }

    @Override
    public void update(int time) {

    }

    @Override
    public void setGraphics(GraphicsObject graphics) {
        super.setGraphics(graphics);
        graphics.blink(10, 0.8);
    }
}
