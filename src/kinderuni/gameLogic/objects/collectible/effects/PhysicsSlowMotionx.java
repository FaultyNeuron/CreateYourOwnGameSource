package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;

/**
* Created by Georg Plaz.
*/
public class PhysicsSlowMotionx extends ReversibleEffect {
    public static final String ID = "slow_motion";
    private double factor;
    public PhysicsSlowMotionx(double factor) {
        this.factor = factor;
    }

    @Override
    public void activate(LivingObject target) {
        super.activate(target);
        target.setPhysicsMotionFactor(target.getPhysicsMotionFactor()*factor);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getTarget().setPhysicsMotionFactor(getTarget().getPhysicsMotionFactor() / factor);

    }

    @Override
    public String toString() {
        return "PhysicsSlowMotionx{" +
                "factor=" + factor +
                ", reverser=" + getReverser() +
                '}';
    }


}
