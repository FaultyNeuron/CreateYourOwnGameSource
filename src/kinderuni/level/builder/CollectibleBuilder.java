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
    private kinderuni.System system;

    public CollectibleBuilder(kinderuni.System system, Random random) {
        super(system, random);
        this.system = system;
    }

    public Collectible build(CollectibleSettings collectibleSettings) {
        return build(collectibleSettings, DoubleTupel.ZEROS);
    }

    public Collectible build(CollectibleSettings collectibleSettings, DoubleTupel position) {
        ReversibleEffect.Reverser reverser = null;
        if(collectibleSettings.hasEffectDuration()){
            reverser = new TimeBasedReverser(collectibleSettings.getEffectDuration());
        }
        Effect effect = EffectBuilder.createEffects(collectibleSettings.getEffects(), reverser);
        System.out.println("created: "+effect);
        Collectible toReturn;
        if(effect!=null){
            toReturn = new Collectible(effect, collectibleSettings.getDropAcceleration());
        }else{
            toReturn = new Collectible(collectibleSettings.getDropAcceleration());
        }
        attach(toReturn, collectibleSettings);
        return toReturn;
    }
}
