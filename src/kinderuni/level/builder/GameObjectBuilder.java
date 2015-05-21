package kinderuni.level.builder;

import functionalJava.data.shape.box.Box;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.GameObject;
import kinderuni.settings.levelSettings.objectSettings.DimensionsSettings;
import kinderuni.settings.levelSettings.objectSettings.GameObjectSettings;
import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class GameObjectBuilder {
    private kinderuni.System system;
    private Random random;
    private Box boundingForRandomPlacement;

    public GameObjectBuilder(kinderuni.System system, Random random) {
        this.system = system;
        this.random = random;
    }

    public void attach(GameObject gameObject, GameObjectSettings settings){
        attach(gameObject, settings, new GameObjectSettings()); //todo new object make constant
    }
    
    public void attach(GameObject gameObject, GameObjectSettings settings, GameObjectSettings defaultSettings){
        DoubleTupel position;
        if(settings.hasPosition()){
            position = settings.getPosition();
        }else if(defaultSettings.hasPosition()){
            position = defaultSettings.getPosition();
        }else{
            position = null;
        }
        if(position!=null){
            gameObject.setCenter(position);
        }else if(boundingForRandomPlacement!=null){
            gameObject.setCenter(createPoint(boundingForRandomPlacement));
        }
        DoubleTupel dimension = getDimensions(settings, defaultSettings);
        DoubleTupel graphicsDimensions = getDimensions(settings.getGraphicsSettings(), defaultSettings.getGraphicsSettings());
        if(graphicsDimensions==null){
            graphicsDimensions = dimension;
        }else if(dimension == null){
            dimension = graphicsDimensions;
        }
        if(dimension!=null){
            gameObject.setBounding(dimension);
        }else if(boundingForRandomPlacement!=null){
            gameObject.setBounding(new DoubleTupel(50, 50)); //todo: make constant
        }
        GraphicsSettings graphicsSettings = settings.hasGraphicsSettings()?settings.getGraphicsSettings():defaultSettings.getGraphicsSettings();
        if(graphicsSettings!= null && graphicsDimensions != null){ // don't create graphics if no dimensions specified
            gameObject.setGraphics(system.createGraphics(graphicsSettings, graphicsDimensions));
        }
    }

    public DoubleTupel createPoint(Box bounding) {
        double x = random.nextDouble() * bounding.getWidth() + bounding.getLeft();
        double y = random.nextDouble() * bounding.getHeight() + bounding.getLower();
        return new DoubleTupel(x, y);
    }

    public Collection<DoubleTupel> createPoints(Box bounding, int count) {
        Collection<DoubleTupel> points = new LinkedList<DoubleTupel>();
        for (int i = 0; i < count; i++) {
            points.add(createPoint(bounding));
        }
        return points;
    }

    public void setBoundingForRandomPlacement(Box boundingForRandomPlacement) {
        this.boundingForRandomPlacement = boundingForRandomPlacement;
    }

    public kinderuni.System getSystem() {
        return system;
    }
    
    public DoubleTupel getDimensions(DimensionsSettings settings, DimensionsSettings defaultSettings){
        if(settings!=null && settings.hasDimensions()){
            return settings.getDimensions();
        }else if(defaultSettings!=null && defaultSettings.hasDimensions()){
            return defaultSettings.getDimensions();
        }else{
            return null;
        }
    }

    public Random getRandom() {
        return random;
    }
}
