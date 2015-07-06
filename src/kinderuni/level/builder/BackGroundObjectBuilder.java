package kinderuni.level.builder;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.System;
import kinderuni.gameLogic.objects.BackGroundObject;
import kinderuni.settings.IdParametersSettings;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.levelSettings.objectSettings.BackGroundObjectSettings;
import kinderuni.settings.levelSettings.objectSettings.BackGroundObjectSettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class BackGroundObjectBuilder extends GameObjectBuilder {
    public BackGroundObjectBuilder(System system, Random random) {
        super(system, random);
    }

    public BackGroundObject build(BackGroundObjectSettings backGroundObjectSettings) {
        return build(backGroundObjectSettings, BackGroundObjectSettings.DEFAULT, false);
    }

    public BackGroundObject build(BackGroundObjectSettings backGroundObjectSettings, BackGroundObjectSettings defaultSettings) {
        return build(backGroundObjectSettings, defaultSettings, true);
    }

    public BackGroundObject build(BackGroundObjectSettings backGroundObjectSettings, BackGroundObjectSettings defaultSettings, boolean keepDefault) {
        DoubleTupel rangeY = backGroundObjectSettings.getYRange();
        double distanceFactor = backGroundObjectSettings.hasDistanceFactor()?backGroundObjectSettings.getDistanceFactor():defaultSettings.getDistanceFactor();

        BackGroundObject toReturn = new BackGroundObject(distanceFactor);
        if(keepDefault){
            attach(toReturn, backGroundObjectSettings, defaultSettings);
        }else{
            attach(toReturn, backGroundObjectSettings);
        }
        double y = rangeY.getFirst() + getRandom().nextDouble()*(rangeY.getSecond()-rangeY.getFirst());
        DoubleTupel center = new DoubleTupel(toReturn.getCenter().getFirst(), y);
        toReturn.setCenter(center);
        return toReturn;
    }

    private List<BackGroundObject> generateBackGroundObjects(Random random, LevelSettings levelSettings) {
        return null;
    }


}
