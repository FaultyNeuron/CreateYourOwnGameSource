package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.GameWorld;
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
    public void activate(Player player) {
        super.activate(player);
        multGravity(player.getWorld(), factor);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        multGravity(getPlayer().getWorld(), 1/factor);
    }

    @Override
    public GravityChangePower copy() {
        GravityChangePower toReturn = new GravityChangePower(factor);
        if(hasReverser()){
            toReturn.setReverser(getReverser().copy());
        }
        return toReturn;
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
