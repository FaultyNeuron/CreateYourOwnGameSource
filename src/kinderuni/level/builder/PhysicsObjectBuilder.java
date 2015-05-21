package kinderuni.level.builder;

import kinderuni.gameLogic.objects.PhysicsObject;
import kinderuni.settings.levelSettings.objectSettings.PhysicsObjectSettings;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class PhysicsObjectBuilder extends GameObjectBuilder {
    public PhysicsObjectBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }

    public void attach(PhysicsObject gameObject, PhysicsObjectSettings settings) {
        attach(gameObject, settings, new PhysicsObjectSettings());
    }

    public void attach(PhysicsObject gameObject, PhysicsObjectSettings settings, PhysicsObjectSettings defaultSettings) {
        double bounciness = settings.hasBounciness()? settings.getBounciness():defaultSettings.getBounciness();
        double gravityFactor = settings.hasGravityFactor()? settings.getGravityFactor():defaultSettings.getGravityFactor();
        gameObject.setBounciness(bounciness);
        gameObject.setGravityFactor(gravityFactor);
//        gameObject.setBounciness();
        super.attach(gameObject, settings, defaultSettings);
    }
}
