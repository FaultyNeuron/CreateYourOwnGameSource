package kinderuni.level.builder;

import kinderuni.gameLogic.objects.Projectile;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.DropBuilder;
import kinderuni.settings.levelSettings.objectSettings.ProjectileSettings;

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
    public void attach(Projectile projectile, ProjectileSettings settings) {
        super.attach(projectile, settings);
    }
}
