package kinderuni.level.builder;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.settings.levelSettings.objectSettings.LivingSettings;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class LivingObjectBuilder extends PhysicsObjectBuilder {
    public LivingObjectBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }
    
    public void attach(LivingObject livingObject, LivingSettings livingSettings){
        attach(livingObject, livingSettings, new LivingSettings());
    }

    public void attach(LivingObject livingObject, LivingSettings livingSettings, LivingSettings defaultSettings){
        int hp = livingSettings.hasHp()?livingSettings.getHp():defaultSettings.getHp();
        double walkingSpeed = livingSettings.hasWalkingSpeed()?livingSettings.getWalkingSpeed():defaultSettings.getWalkingSpeed();
        double jumpPower = livingSettings.hasJumpPower()?livingSettings.getJumpPower():defaultSettings.getJumpPower();
        livingObject.setHp(hp);
        livingObject.setWalkingSpeed(walkingSpeed);
        livingObject.setJumpPower(jumpPower);
        super.attach(livingObject, livingSettings, livingSettings);
    }
}
