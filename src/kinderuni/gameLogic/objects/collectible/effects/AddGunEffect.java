package kinderuni.gameLogic.objects.collectible.effects;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.ProjectileGun;

/**
 * Created by Georg Plaz.
 */
public class AddGunEffect extends ReversibleEffect{
    public static final String ID = "add_gun";
    //    private Effect projectileEffect;
    private ProjectileGun givenGun;

    public AddGunEffect(ProjectileGun gunToGive) {
        this.givenGun = gunToGive;
    }

    @Override
    public void activate(LivingObject target) {
        super.activate(target);
        target.setGun(givenGun);
    }

    @Override
    public void deActivate() {
        super.deActivate();
        getTarget().removeGun(givenGun);
    }
}
