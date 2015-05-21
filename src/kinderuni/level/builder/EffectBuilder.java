package kinderuni.level.builder;

import kinderuni.gameLogic.objects.ProjectileGun;
import kinderuni.gameLogic.objects.collectible.effects.*;
import kinderuni.settings.levelSettings.objectSettings.EffectSettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class EffectBuilder extends Builder{
    public EffectBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }

    public Effect buildEffects(List<EffectSettings> effectSettings){
        return buildEffects(effectSettings, null);
    }

    public Effect buildEffects(List<EffectSettings> effectSettings, ReversibleEffect.Reverser reverser){
        if(effectSettings.size() > 1) {
            List<Effect> effects = new LinkedList<>();
            for (EffectSettings effectSetting : effectSettings) {
                effects.add(buildEffect(effectSetting, reverser.copy()));
            }
            return new CombinedEffect(effects);
        }else if(effectSettings.size()==1){
            return buildEffect(effectSettings.get(0), reverser);
        }else{
            return null;
        }
    }

    public Effect buildEffect(EffectSettings effectSettings){
        return buildEffect(effectSettings, null);
    }

    public Effect buildEffect(EffectSettings effectSettings, ReversibleEffect.Reverser reverser){
        return buildEffect(effectSettings, EffectSettings.DEFAULT, reverser);
    }
    public Effect buildEffect(EffectSettings effectSettings, EffectSettings defaultSettings, ReversibleEffect.Reverser reverser){
        int value = effectSettings.hasValue()?effectSettings.getValue():defaultSettings.getValue();
        Effect effect;
        switch (effectSettings.getId()){
            case PlusHp.ID:
                effect = new PlusHp(value);
                break;
            case PlusLife.ID:
                effect = new PlusLife(value);
                break;
            case PlusCoins.ID:
                effect = new PlusCoins(value);
                break;
            default:
                effect = createReversibleEffect(effectSettings, defaultSettings, reverser);
                break;
        }
        return effect;
    }

    public ReversibleEffect createReversibleEffect(EffectSettings effectSettings){
        return createReversibleEffect(effectSettings, null);
    }

    public ReversibleEffect createReversibleEffect(EffectSettings effectSettings, ReversibleEffect.Reverser reverser){
        return createReversibleEffect(effectSettings, EffectSettings.DEFAULT, reverser);
    }
    public ReversibleEffect createReversibleEffect(EffectSettings effectSettings, EffectSettings defaultSettings, ReversibleEffect.Reverser reverser){
//        System.out.println("creating reversible with: "+reverser.getClass().getSimpleName());
        double factor = effectSettings.hasFactor()?effectSettings.getFactor():defaultSettings.getFactor();
        ReversibleEffect effect;
        switch (effectSettings.getId()){
            case SpeedPower.ID:
                effect = new SpeedPower(factor);
                break;
            case InvinciblePower.ID:
                effect = new InvinciblePower();
                break;
            case GravityChangePower.ID:
                effect = new GravityChangePower(factor);
                break;
            case AddGunEffect.ID:
                EffectSettings projectileSettings = new EffectSettings();
                projectileSettings.setId("plus_hp");
                projectileSettings.setValue(-1);
                ProjectileGun gun = new ProjectileGun(getSystem(), "black_hole", projectileSettings);
                gun.setInitialShootCoolDown(100);
                effect = new AddGunEffect(gun);
                break;
            default:
                throw new RuntimeException("no effect with id \""+effectSettings.getId()+"\" exists!");
        }
        if(effectSettings.hasDuration()){
            effect.setReverser(new TimeBasedReverser(effectSettings.getDuration()));
        }else if(reverser!=null) {
            effect.setReverser(reverser);
        }
        return effect;
    }
}
