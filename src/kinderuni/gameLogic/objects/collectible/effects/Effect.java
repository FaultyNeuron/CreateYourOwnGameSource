package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;
import kinderuni.ui.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public abstract class Effect {
    private Player player;
    private GraphicsObject graphics;

    public void activate(Player player){
        this.player = player;
    }
    public void update(){}

    public Player getPlayer() {
        return player;
    }

    public void setGraphics(GraphicsObject graphics) {
        this.graphics = graphics;
    }

    public GraphicsObject getGraphics() {
        return graphics;
    }

    public abstract Effect copy();
}
