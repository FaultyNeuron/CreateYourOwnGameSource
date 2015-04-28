package kinderuni.graphics;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public interface Screen {
    public DoubleTupel getScreenDimensions();

    public double getScreenWidth();

    public double getScreenHeight();

    public DoubleTupel getCenter();

    public void setCenter(DoubleTupel center);

    public Box getScreenArea();

    public abstract void render();

    public abstract void setLives(int lives);
    public abstract void setHp(int hp);

//    public void paint(Painter painter);

    public GameWorld getGameWorld();

    public void setGameWorld(GameWorld gameWorld);

    public boolean goRight();
    public boolean goLeft();
    public boolean jump();
}
