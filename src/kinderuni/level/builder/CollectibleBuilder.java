package kinderuni.level.builder;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.GameObject;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.ReversibleEffect;
import kinderuni.gameLogic.objects.collectible.effects.TimeBasedReverser;
import kinderuni.settings.levelSettings.objectSettings.CollectibleSettings;
import kinderuni.settings.levelSettings.objectSettings.GameObjectSettings;

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

    public Collectible build(CollectibleSettings settings) {
        Collectible toReturn = new Collectible();
        attach(toReturn, settings);

//        ReversibleEffect.Reverser reverser = null;
//        if(settings.hasEffectDuration()){
//            reverser = new TimeBasedReverser(settings.getEffectDuration());
//        }
//        Effect effect = effectBuilder.buildEffects(settings.getEffects(), reverser);
//        if(effect!=null){
//            toReturn = new Collectible(effect, dropAcceleration);
//        }else{
//            toReturn = new Collectible(dropAcceleration);
//        }
//        attach(toReturn, settings);
        return toReturn;
    }

    public void attach(Collectible collectible, CollectibleSettings settings) {
        if(settings.hasDropAcceleration()){
            collectible.setDropAcceleration(settings.getDropAcceleration());
        }
        ReversibleEffect.Reverser reverser = null;
        if(settings.hasEffectDuration()){
            reverser = new TimeBasedReverser(settings.getEffectDuration());
        }
        if (settings.hasEffects()) {
            Effect effect = effectBuilder.buildEffects(settings.getEffects(), reverser);
            if(effect!=null){
                collectible.setEffect(effect);
            }
        }

        super.attach(collectible, settings);
    }

    public EffectBuilder getEffectBuilder() {
        return effectBuilder;
    }
}
