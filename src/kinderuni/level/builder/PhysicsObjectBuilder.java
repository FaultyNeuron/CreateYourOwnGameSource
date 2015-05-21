package kinderuni.level.builder;

import kinderuni.gameLogic.objects.PhysicsObject;
import kinderuni.settings.levelSettings.objectSettings.PhysicsObjectSettings;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class PhysicsObjectBuilder extends GameObjectBuilder {
    public static final PhysicsObjectSettings DEFAULT = new PhysicsObjectSettings();
    static{
        DEFAULT.setBounciness(0);
        DEFAULT.setGravityFactor(1);
    }

    public PhysicsObjectBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }

    public void attach(PhysicsObject gameObject) {
        attach(gameObject, DEFAULT);
    }

    public void attach(PhysicsObject gameObject, PhysicsObjectSettings settings) {
        attach(gameObject, settings, DEFAULT, false);
    }

    public void attach(PhysicsObject gameObject, PhysicsObjectSettings settings, PhysicsObjectSettings defaultSettings) {
        attach(gameObject, settings, defaultSettings, true);
    }

    public void attach(PhysicsObject gameObject, PhysicsObjectSettings settings, PhysicsObjectSettings defaultSettings, boolean keepDefault) {
        double bounciness = settings.hasBounciness()? settings.getBounciness():defaultSettings.getBounciness();
        double gravityFactor = settings.hasGravityFactor()? settings.getGravityFactor():defaultSettings.getGravityFactor();
        gameObject.setBounciness(bounciness);
        gameObject.setGravityFactor(gravityFactor);
//        gameObject.setBounciness();
        if(keepDefault){
            super.attach(gameObject, settings, defaultSettings);
        }else{
            super.attach(gameObject, settings);
        }
    }
}
