package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.GameWorld;
import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.Player;

/**
* Created by Georg Plaz.
*/
public class GravityChangePower extends ReversibleEffect {
    public static final String ID = "gravity";
    private double factor;
    public GravityChangePower(double factor) {
        this.factor = factor;
    }

    @Override
    public void activate(LivingObject target) {
        super.activate(target);
        target.setGravityFactor(target.getGravityFactor()*factor);
//        multGravity(target.getWorld(), factor);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getTarget().setGravityFactor(getTarget().getGravityFactor() / factor);
    }

    private void multGravity(GameWorld gameWorld, double factor){
        gameWorld.setGravity(factor*gameWorld.getGravity());
    }

    @Override
    public String toString() {
        return "GravitySwitchPower{" +
                "factor=" + factor +
                ", reverser=" + getReverser() +
                '}';
    }
}
