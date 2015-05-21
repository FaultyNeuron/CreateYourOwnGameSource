package kinderuni.level.builder;

import kinderuni.gameLogic.objects.Projectile;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropBuilder;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class ProjectileBuilder extends CollectibleBuilder {
    public ProjectileBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }

//    public Projectile build(ProjectileSettings projectileSettings) {
//        return build(projectileSettings, ProjectileSettings.DEFAULT, false);
//    }
//
//    public Projectile build(ProjectileSettings projectileSettings, ProjectileSettings defaultSettings) {
//        return build(projectileSettings, defaultSettings, true);
//    }
    public void attach(Projectile projectile) {
        boolean keepDefault = false;
//        int damage = projectileSettings.hasDamage()?projectileSettings.getDamage():defaultSettings.getDamage();
//        int jumpPause = projectileSettings.hasJumpPause()?projectileSettings.getJumpPause():defaultSettings.getJumpPause();
//
//        Projectile toReturn = new Projectile(damage, jumpPause);
//        if (dropBuilder !=null && !dropBuilder.isEmpty()) {
//            Collectible drop = dropBuilder.create();
//            toReturn.setDrop(drop);
//        }
        if(keepDefault){
//            attach(toReturn, projectileSettings, defaultSettings);
        }else{
            super.attach(projectile);
        }
    }
}
