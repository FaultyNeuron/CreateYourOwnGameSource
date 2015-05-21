package kinderuni.level.builder;

import kinderuni.gameLogic.objects.LivingObject;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.settings.levelSettings.objectSettings.EffectSettings;
import kinderuni.settings.levelSettings.objectSettings.LivingSettings;

import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class LivingObjectBuilder extends PhysicsObjectBuilder {
    public EffectBuilder effectBuilder;
    public LivingObjectBuilder(kinderuni.System system, Random random) {
        super(system, random);
        effectBuilder = new EffectBuilder(system, random);
    }
    
    public void attach(LivingObject livingObject, LivingSettings livingSettings){
        attach(livingObject, livingSettings, LivingSettings.DEFAULT, false);
    }

    public void attach(LivingObject livingObject, LivingSettings livingSettings, LivingSettings defaultSettings){
        attach(livingObject, livingSettings, defaultSettings, true);
    }
    public void attach(LivingObject livingObject, LivingSettings settings, LivingSettings defaultSettings, boolean keepDefault){
        int hp = settings.hasHp()?settings.getHp():defaultSettings.getHp();
        double walkingSpeed = settings.hasWalkingSpeed()?settings.getWalkingSpeed():defaultSettings.getWalkingSpeed();
        double flyingSpeed = settings.hasFlyingSpeed()?settings.getFlyingSpeed():defaultSettings.getFlyingSpeed();
        double jumpPower = settings.hasJumpPower()?settings.getJumpPower():defaultSettings.getJumpPower();
        List<EffectSettings> activeEffects = settings.hasActiveEffects()?settings.getActiveEffects():defaultSettings.getActiveEffects();
        livingObject.setHp(hp);
        livingObject.setWalkingSpeed(walkingSpeed);
        livingObject.setJumpPower(jumpPower);
        livingObject.setFlyingSpeed(flyingSpeed);
        for(EffectSettings effectSettings : activeEffects){
            Effect effect = effectBuilder.buildEffect(effectSettings);
            effect.activate(livingObject);
        }
        if(keepDefault){
            super.attach(livingObject, settings, defaultSettings);
        }else{
            super.attach(livingObject, settings);
        }
    }
}
