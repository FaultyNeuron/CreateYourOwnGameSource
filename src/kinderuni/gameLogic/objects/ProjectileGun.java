package kinderuni.gameLogic.objects;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.System;
import kinderuni.level.builder.ProjectileBuilder;
import kinderuni.settings.levelSettings.objectSettings.ProjectileSettings;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class ProjectileGun extends ProjectileBuilder{
    private GameObject holder;
//    private String projectileId;
    private int initialShootCoolDown = 0;
    private DoubleTupel shootDelta = DoubleTupel.ONES;
    private int shootCoolDown;
    private ProjectileSettings projectileSettings;

    public ProjectileGun(System system, ProjectileSettings projectileSettings) {
        super(system, new Random().nextLong());
        this.projectileSettings = projectileSettings;
        if(projectileSettings.hasCoolDown()){
            setInitialShootCoolDown(projectileSettings.getCoolDown());
        }
        if(projectileSettings.hasShootingDelta()){
            shootDelta = projectileSettings.getShootingDelta();
        }
    }

    public void update(int time){
        shootCoolDown--;
    }

    public void shoot(final Collection<? extends LivingObject> targets) {
        if(shootCoolDown<=0 && holder!=null) {
            Projectile projectile = new Projectile(){//getEffectBuilder().buildEffects(effectSettings), 1000) {
                @Override
                public Collection<? extends LivingObject> getTargets() {
                    return targets;
                }
            };
            attach(projectile, projectileSettings);
            projectile.setCenter(holder.getCenter());
            DoubleTupel correctedShootDelta = holder.getDirection()== HorizontalDirection.RIGHT?shootDelta:shootDelta.mult(-1, 1);
            projectile.setSpeed(correctedShootDelta);
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

    public void setShootDelta(DoubleTupel shootDelta) {
        this.shootDelta = shootDelta;
    }

    @Override
    public String toString() {
        return "ProjectileGun{" +
                "initialShootCoolDown=" + initialShootCoolDown +
                ", shootDelta=" + shootDelta +
                ", effect=" + projectileSettings.getEffects() +
                '}';
    }
}
