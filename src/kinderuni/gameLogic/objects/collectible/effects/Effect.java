package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.ui.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public abstract class Effect {
    private LivingObject target;
    private GraphicsObject graphics;

    public void activate(LivingObject target){
        this.target = target;
    }
    public void update(int time){}

    public LivingObject getTarget() {
        return target;
    }

    public void setGraphics(GraphicsObject graphics) {
        this.graphics = graphics;
    }

    public GraphicsObject getGraphics() {
        return graphics;
    }
}
