package kinderuni.gameLogic.objects;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.*;
import kinderuni.System;
import kinderuni.level.builder.ProjectileBuilder;
import kinderuni.settings.levelSettings.objectSettings.EffectSettings;
import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class ProjectileGun extends ProjectileBuilder{
    private GameObject holder;
    private String projectileId;
    private int initialShootCoolDown;
    private int shootCoolDown;
    private EffectSettings effectSettings;

    public ProjectileGun(System system, String projectileId, EffectSettings effectSettings) {
        super(system, new Random());
        this.projectileId = projectileId;
        this.effectSettings = effectSettings;
    }

    public void update(int time){
        shootCoolDown--;
    }

    public void shoot(final Collection<? extends LivingObject> targets) {
        Projectile projectile = new Projectile(getEffectBuilder().buildEffect(effectSettings)) {
            @Override
            public Collection<? extends LivingObject> getTargets() {
                return targets;
            }
        };

        if(shootCoolDown<0 && holder!=null) {
            attach(projectile);
            projectile.setCenter(holder.getCenter());
            projectile.setSpeed(holder.getDirection().toVector().add(0, 1).toLength(5));
            projectile.setGraphics(getSystem().createGraphics(projectileId, 20, 20));
            projectile.setBounciness(0.5);
            projectile.setBounding(new DoubleTupel(20, 20));
            holder.getWorld().addObject(projectile);
            shootCoolDown = initialShootCoolDown;
        }
    }

    public void setInitialShootCoolDown(int initialShootCoolDown) {
        this.initialShootCoolDown = initialShootCoolDown;
    }

    public void setHolder(GameObject holder) {
        this.holder = holder;
    }
}
