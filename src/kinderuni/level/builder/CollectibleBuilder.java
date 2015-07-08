package kinderuni.level.builder;

import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.GameObject;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.gameLogic.objects.collectible.effects.Effect;
import kinderuni.gameLogic.objects.collectible.effects.ReversibleEffect;
import kinderuni.gameLogic.objects.collectible.effects.TimeBasedReverser;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.settings.levelSettings.objectSettings.CollectibleSettings;
import kinderuni.settings.levelSettings.objectSettings.GameObjectSettings;

import java.lang.System;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class CollectibleBuilder extends PhysicsObjectBuilder{
    private EffectBuilder effectBuilder;

    public CollectibleBuilder(kinderuni.System system, long seed) {
        super(system, new Random(seed));
        this.effectBuilder = new EffectBuilder(system, getRandom());
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

    public List<Collectible> buildCoinsForPlatform(Platform platform) {

        int cnt = 3;
        //CollectibleSettings collectibleSettings = ; //Coins

        //return buildForPlatform(platform, cnt, collectibleSettings);
        return null;
    }

    public List<Collectible> buildForPlatform(Platform platform, int cnt, CollectibleSettings collectibleSettings) {
        List<Collectible> collectibleList = new LinkedList<>();
        DoubleTupel center = platform.getCenter();
        DoubleTupel dim = platform.getDimensions();
        double leftSide = center.getFirst() - dim.getFirst()/2;
        double yCollectible = center.getSecond() + dim.getSecond()/2 + build(collectibleSettings).getDimensions().getSecond()/2;
        double xDistance = dim.getFirst()/(cnt+1);

        for(int i = 0; i < cnt; i++){
            double xCollectible = leftSide + (i+1) * xDistance;

            Collectible collectible = build(collectibleSettings);
            collectible.setCenter(new DoubleTupel(xCollectible, yCollectible));

            collectibleList.add(collectible);
        }

        return collectibleList;
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
