package kinderuni.level.builder;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.ReversibleEffect;
import kinderuni.gameLogic.objects.collectible.effects.TimeBasedReverser;
import kinderuni.settings.levelSettings.objectSettings.CollectibleSettings;

import java.lang.System;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class CollectibleBuilder extends PhysicsObjectBuilder{
    private EffectBuilder effectBuilder;

    public CollectibleBuilder(kinderuni.System system, Random random) {
        super(system, random);
        this.effectBuilder = new EffectBuilder(system, random);
    }

    public Collectible build(CollectibleSettings collectibleSettings) {
        return build(collectibleSettings, CollectibleSettings.DEFAULT, false);
    }

    public Collectible build(CollectibleSettings settings, CollectibleSettings defaultSettings) {
        return build(settings, defaultSettings, true);
    }

    public Collectible build(CollectibleSettings settings, CollectibleSettings defaultSettings, boolean keepDefault) {
        double dropAcceleration = settings.hasDropAcceleration()?settings.getDropAcceleration():defaultSettings.getDropAcceleration();
        ReversibleEffect.Reverser reverser = null;
        if(settings.hasEffectDuration()){
            reverser = new TimeBasedReverser(settings.getEffectDuration());
        }
        Effect effect = effectBuilder.buildEffects(settings.getEffects(), reverser);
        System.out.println("created: "+effect);
        Collectible toReturn;
        if(effect!=null){
            toReturn = new Collectible(effect, dropAcceleration);
        }else{
            toReturn = new Collectible(dropAcceleration);
        }
        if(keepDefault){
            attach(toReturn, settings, defaultSettings);
        }else{
            attach(toReturn, settings);
        }
        return toReturn;
    }

    public EffectBuilder getEffectBuilder() {
        return effectBuilder;
    }
}
