package kinderuni.gameLogic.objects.collectible.effects;

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
    public void activate(Player player) {
        super.activate(player);
        player.setFlyingSpeed(player.getFlyingSpeed() * factor);
        player.setWalkingSpeed(player.getWalkingSpeed() * factor);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getPlayer().setFlyingSpeed(getPlayer().getFlyingSpeed() / factor);
        getPlayer().setWalkingSpeed(getPlayer().getWalkingSpeed() * factor);
    }

    @Override
    public SpeedPower copy() {
        SpeedPower toReturn = new SpeedPower(factor);
        if(hasReverser()){
            toReturn.setReverser(getReverser().copy());
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return "SpeedPower{" +
                "factor=" + factor +
                ", reverser=" + getReverser() +
                '}';
    }


}
