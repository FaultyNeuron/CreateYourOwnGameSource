package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.Player;

/**
* Created by Georg Plaz.
*/
public class SpeedPower extends ReversibleEffect {
    public static final String ID = "speed";
    private double factor;
    public SpeedPower(double factor) {
        this.factor = factor;
    }

    @Override
    public void activate(LivingObject target) {
        super.activate(target);
        target.setFlyingSpeed(target.getFlyingSpeed() * factor);
        target.setWalkingSpeed(target.getWalkingSpeed() * factor);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getTarget().setFlyingSpeed(getTarget().getFlyingSpeed() / factor);
        getTarget().setWalkingSpeed(getTarget().getWalkingSpeed() / factor);
    }

    @Override
    public String toString() {
        return "SpeedPower{" +
                "factor=" + factor +
                ", reverser=" + getReverser() +
                '}';
    }


}
