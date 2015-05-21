package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
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
    public void activate(LivingObject target) {
        super.activate(target);
        target.applyEffect(this);
    }

    @Override
    public void update(int time) {
        super.update(time);
        if(reverser!=null){
            reverser.update(time);
            if(reverser.reverse(time)){
                deActivate();
            }
        }
    }

    public void deActivate() {
        getTarget().removeEffect(this);
    }

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
        public void update(int time);
        public boolean reverse(int time);
        public Reverser copy();
    }
}
