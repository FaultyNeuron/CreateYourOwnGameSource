package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;

/**
* Created by Georg Plaz.
*/
public class Bounciness extends ReversibleEffect {
    public static final String ID = "bounciness";
    private double factor;
    public Bounciness(double factor) {
        this.factor = factor;
    }

    @Override
    public void activate(LivingObject target) {
        super.activate(target);
        target.setBounciness(target.getBounciness() + factor);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getTarget().setBounciness(getTarget().getBounciness() - factor);
    }

    @Override
    public String toString() {
        return "SpeedPower{" +
                "factor=" + factor +
                ", reverser=" + getReverser() +
                '}';
    }


}
