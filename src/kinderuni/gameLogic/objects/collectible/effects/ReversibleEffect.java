package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public abstract class ReversibleEffect extends Effect{
    private Reverser reverser;
    public ReversibleEffect(){
    }

    public ReversibleEffect(Reverser reverser){
        this.reverser = reverser;
    }

    @Override
    public void activate(Player player) {
        super.activate(player);
        player.applyEffect(this);
    }

    @Override
    public void update() {
        super.update();
        if(reverser!=null){
            reverser.update();
            if(reverser.reverse()){
                deActivate();
            }
        }
    }

    public void deActivate() {
        getPlayer().removeEffect(this);
    }

    @Override
    public abstract ReversibleEffect copy();

    public Reverser getReverser(){
        return reverser;
    }

    public boolean hasReverser(){
        return reverser!=null;
    }

    public void setReverser(Reverser reverser) {
        this.reverser = reverser;
    }

    public interface Reverser {
        public void update();
        public boolean reverse();
        public Reverser copy();
    }
}
