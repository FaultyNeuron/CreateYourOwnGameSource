package kinderuni.level.builder;

import kinderuni.gameLogic.objects.collectible.effects.*;
import kinderuni.settings.levelSettings.objectSettings.EffectSettings;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class EffectBuilder {
    public static Effect createEffects(List<EffectSettings> effectSettings){
        return createEffects(effectSettings, null);
    }

    public static Effect createEffects(List<EffectSettings> effectSettings, ReversibleEffect.Reverser reverser){
        if(effectSettings.size() > 1) {
            List<Effect> effects = new LinkedList<>();
            for (EffectSettings effectSetting : effectSettings) {
                effects.add(createEffect(effectSetting, reverser.copy()));
            }
            return new CombinedEffect(effects);
        }else if(effectSettings.size()==1){
            return createEffect(effectSettings.get(0), reverser);
        }else{
            return null;
        }
    }

    public static Effect createEffect(EffectSettings effectSettings){
        return createEffect(effectSettings, null);
    }

    public static Effect createEffect(EffectSettings effectSettings, ReversibleEffect.Reverser reverser){
        Effect effect;
        switch (effectSettings.getId()){
            case PlusHp.ID:
                effect = new PlusHp(effectSettings.getValue());
                break;
            case PlusLife.ID:
                effect = new PlusLife(effectSettings.getValue());
                break;
            case PlusCoins.ID:
                effect = new PlusCoins(effectSettings.getValue());
                break;
            default:
                effect = createReversibleEffect(effectSettings, reverser);
                break;
        }
        return effect;
    }

    public static ReversibleEffect createReversibleEffect(EffectSettings effectSettings){
        return createReversibleEffect(effectSettings, null);
    }

    public static ReversibleEffect createReversibleEffect(EffectSettings effectSettings, ReversibleEffect.Reverser reverser){
//        System.out.println("creating reversible with: "+reverser.getClass().getSimpleName());
        ReversibleEffect effect;
        switch (effectSettings.getId()){
            case SpeedPower.ID:
                effect = new SpeedPower(effectSettings.getFactor());
                break;
            case InvinciblePower.ID:
                effect = new InvinciblePower();
                break;
            case GravityChangePower.ID:
                effect = new GravityChangePower(effectSettings.getFactor());
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
