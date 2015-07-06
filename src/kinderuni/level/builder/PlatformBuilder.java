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

    private double horizontalDistance;
    private double verticalDistance;


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
        // initialize values and lists
        List<Box> existingBoxes = new LinkedList<>();
        List<Platform> createdPlatforms = new LinkedList<>();
        DoubleTupel levelDimensions = levelSettings.getDimensions();
        List<IdParametersSettings> platformsTypeIds = levelSettings.getPlatforms();
        kinderuni.System system = getSystem();
        List<PlatformSettings> platformSettingsList = new LinkedList<>();
        for (IdParametersSettings idSettings : platformsTypeIds)
            for (int cnt = 0; cnt < idSettings.getCount(); cnt++)
                platformSettingsList.add(system.getSettings().getPlatformSettings(idSettings.getId()));

        // iterate over all platforms in list
        for (PlatformSettings platformSettings : platformSettingsList){
            double yFound = -1;
            double xRand = -1;
            // create dummy box
            Box platformBox = new FastAccessBox(DoubleTupel.ONES, DoubleTupel.ONES);
            // try 10 times to find a x position
            for (int run = 0; run < 10; run++) {
                xRand = getRandX(levelDimensions, platformSettings);
                platformBox = boxBuilder(xRand, platformSettings, horizontalDistance, verticalDistance);
                // find a y position (-1 -> not found)
                yFound = findPosition(platformBox, existingBoxes, levelDimensions) + verticalDistance;
                platformBox = platformBox.move(new DoubleTupel(0,yFound-verticalDistance));
                if (yFound != -1)
                    break;
            }
            if (yFound != -1) {
                // create platform and add box to existingBoxes
                existingBoxes.add(platformBox);
                Platform platform = build(platformSettings);
                platform.setCenter(new DoubleTupel(xRand, yFound));
                createdPlatforms.add(platform);
            }

        }

        return createdPlatforms;
    }

    private double getRandX(DoubleTupel levelDimensions, PlatformSettings platformSettings) {
        // random value between 0 and 1
        double rand = getRandom().nextDouble();
        double deltaX = platformSettings.getDeltaX();
        double rightBoundary = levelDimensions.getFirst();
        double platformWidth = platformSettings.getGraphicsSettings().getWidth();
        double left = Math.max(platformWidth/2, platformWidth/2 - deltaX);
        double right = rightBoundary - Math.max(platformWidth/2, platformWidth/2 + deltaX);

        return left + (right - left) * rand;
    }

    private Box boxBuilder(double xRand, PlatformSettings platformSettings, double horizontalDistance, double verticalDistance) {
        // create a box surrounding each platform, to keep the distance between them
        double deltaY = platformSettings.getDeltaY();
        double deltaX = platformSettings.getDeltaX();
        double nonMovingWidth = platformSettings.getGraphicsSettings().getWidth() + horizontalDistance;
        // box moving downwards can leave the level. Should it be avoided?
        double yCenter = verticalDistance/2 + deltaY/2;
        double width = nonMovingWidth + Math.abs(deltaX);
        double heigth = verticalDistance + Math.abs(deltaY);

        DoubleTupel center = new DoubleTupel(xRand, yCenter);
        DoubleTupel dimension = new DoubleTupel(width, heigth);
        return new FastAccessBox(center, dimension);
    }

    private double findPosition(Box platformBox, List<Box> existingBoxes, DoubleTupel levelDimensions) {

        if (platformBox.getUpper() > levelDimensions.getSecond()){
            return -1;}
        boolean crash = false;
        double moveUp = 0;
        for (Box existingBox : existingBoxes) {
            if (platformBox.collides(existingBox)) {
                crash = true;
                double move = existingBox.getUpper() - platformBox.getLower();
                moveUp += move;
                platformBox = platformBox.move(new DoubleTupel(0, move));
            }
        }
        if (crash) {
            return moveUp + findPosition(platformBox, existingBoxes, levelDimensions);
        }
        else
            return moveUp;
    }

    public void setHorizontalDistance(double horizontalDistance) {
        this.horizontalDistance = horizontalDistance;
    }

    public void setVerticalDistance(double verticalDistance) {
        this.verticalDistance = verticalDistance;
    }
}
