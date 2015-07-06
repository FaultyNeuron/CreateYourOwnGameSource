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
        //setFrictionCoefficient
        if(settings.hasBounciness()){
            gameObject.setBounciness(settings.getBounciness());
        }
        if(settings.hasGravityFactor()){
            gameObject.setGravityFactor(settings.getGravityFactor());
        }
        if(settings.hasFrictionCoefficient()){
            gameObject.setFrictionCoefficient(settings.getFrictionCoefficient());
        }
        super.attach(gameObject, settings);
    }
}
