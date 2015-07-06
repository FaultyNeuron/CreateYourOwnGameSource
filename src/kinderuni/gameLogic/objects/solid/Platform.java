package kinderuni.gameLogic.objects.solid;

/**
 * Created by Georg Plaz.
 */
public class Platform extends SolidObject{
    private double friction;

    public Platform(double friction) {
        this.friction = friction;
    }

    @Override
    public void update(int time) {

    }

    @Override
    public double getFriction() {
        return friction;
    }
}
