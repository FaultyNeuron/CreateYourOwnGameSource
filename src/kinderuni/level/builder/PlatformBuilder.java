package kinderuni.level.builder;

import functionalJava.data.shape.box.Box;
import functionalJava.data.shape.box.FastAccessBox;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.*;
import kinderuni.System;
import kinderuni.gameLogic.objects.solid.MovingPlatform;
import kinderuni.gameLogic.objects.solid.Platform;
import kinderuni.settings.IdParametersSettings;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.settings.levelSettings.objectSettings.PlatformSettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class PlatformBuilder extends GameObjectBuilder {

    private double horizontalDistance = 50;
    private double verticalDistance = 100;


    public PlatformBuilder(System system, Random random) {
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

    private List<Platform> generatePlatforms(Random random, LevelSettings levelSettings) {

        return null;
    }

    public List<Platform> buildAll(LevelSettings levelSettings) {

        List<Box> existingBoxes = new LinkedList<Box>();
        List<Platform> createdPlatforms = new LinkedList<Platform>();
        DoubleTupel levelDimensions = levelSettings.getDimensions();
        //DoubleTupel center = dimensions.div(2);
        //Box levelBox = new FastAccessBox(center, dimensions);

        List<IdParametersSettings> platformsTypeIds = levelSettings.getPlatforms();
        kinderuni.System system = getSystem();
        List<PlatformSettings> platformSettingsList = new LinkedList<PlatformSettings>();
        for (IdParametersSettings idSettings : platformsTypeIds)
            for (int cnt = 0; cnt < idSettings.getCount(); cnt++)
                platformSettingsList.add(system.getSettings().getPlatformSettings(idSettings.getId()));



        for (PlatformSettings platformSettings : platformSettingsList){
            boolean hasFoundPosition = false;
            // create dummy box
            Box platformBox = new FastAccessBox(DoubleTupel.ONES, DoubleTupel.ONES);
            for (int run = 0; run < 10; run++) {
                double xRand = getRandX(levelDimensions, platformSettings);
                platformBox = boxBuilder(xRand, platformSettings, horizontalDistance, verticalDistance);

                hasFoundPosition = findPosition(platformBox, existingBoxes, levelDimensions);
                if (hasFoundPosition)
                    break;
            }
            if (hasFoundPosition) {
                existingBoxes.add(platformBox);
                Platform platform = build(platformSettings);
                platform.setCenter(platformBox.getCenter());
                createdPlatforms.add(platform);
            }

        }

        return createdPlatforms;
    }

    private double getRandX(DoubleTupel levelDimensions, PlatformSettings platformSettings) {
        return 0;
    }

    private Box boxBuilder(double xRand, PlatformSettings platformSettings, double horizontalDistance, double verticalDistance) {
        return null;
    }

    private boolean findPosition(Box platformBox, List<Box> existingBoxes, DoubleTupel levelDimensions) {

            boolean crash = false;
            for (Box existingBox : existingBoxes) {
                if (platformBox.collides(existingBox)) {
                    crash = true;
                    platformBox.move();
                    if collideCeil()
                            return false;
                }
            }
            if (crash)
                return findPosition()
            else
                return true;

        }
        return false;
    }

    private Box getBoundingBox(PlatformSettings platformSettings) {




        return null;
    }
}
