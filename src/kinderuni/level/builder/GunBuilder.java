package kinderuni.level.builder;

import kinderuni.System;
import kinderuni.gameLogic.objects.ProjectileGun;
import kinderuni.settings.levelSettings.objectSettings.ProjectileSettings;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class GunBuilder extends ProjectileBuilder{
    public GunBuilder(System system, Random random) {
        super(system, random);
    }

    public ProjectileGun build(ProjectileSettings projectileSettings){
//        EffectSettings projectileSettings = new EffectSettings();
//        projectileSettings.setId("plus_hp");
//        projectileSettings.setValue(-1);
        ProjectileGun gun = new ProjectileGun(getSystem(), projectileSettings);
        if(projectileSettings.hasCoolDown()){
            gun.setInitialShootCoolDown(projectileSettings.getCoolDown());
        }
        if(projectileSettings.hasShootingDelta()){
            gun.setShootDelta(projectileSettings.getShootingDelta());
        }
        return gun;
//        gun.set(projectileSettings.getCoolDown());
    }
}
