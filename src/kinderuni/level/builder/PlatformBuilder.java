package kinderuni.level.builder;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.solid.MovingPlatform;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.settings.levelSettings.objectSettings.PlatformSettings;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class PlatformBuilder extends GameObjectBuilder {
    public PlatformBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }

    public Platform build(PlatformSettings platformSettings) {
        return build(platformSettings, PlatformSettings.DEFAULT, false);
    }

    public Platform build(PlatformSettings platformSettings, PlatformSettings defaultSettings) {
        return build(platformSettings, defaultSettings, true);
    }
    public Platform build(PlatformSettings platformSettings, PlatformSettings defaultSettings, boolean keepDefault) {
        double friction = platformSettings.hasFriction()?platformSettings.getFriction():defaultSettings.getFriction();
        double speed = platformSettings.hasSpeed()?platformSettings.getSpeed():defaultSettings.getSpeed();
        DoubleTupel delta = platformSettings.hasDelta()?platformSettings.getDelta():defaultSettings.getDelta();

        Platform toReturn = new MovingPlatform(friction, speed, delta);
        if(keepDefault){
            attach(toReturn, platformSettings, defaultSettings);
        }else{
            attach(toReturn, platformSettings);
        }
        return toReturn;
    }
}
