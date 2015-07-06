package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.Tupel;
import kinderuni.level.builder.CollectibleBuilder;
import kinderuni.settings.levelSettings.objectSettings.CollectibleSettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class DropBuilder extends CollectibleBuilder{
    private double probabilitySummed = 0;
//    private List<Tupel<Collectible, Double>> toClone = new LinkedList<>(); //todo store collectible settings, instead of actual object which are copied..
    private List<Tupel<CollectibleSettings, Double>> toBuild = new LinkedList<>();

    public DropBuilder(kinderuni.System system, long seed) {
        super(system, seed);
    }

//    public void addBluePrint(double likelyHood, Collectible collectible){
    public void addBluePrint(double likelyHood, CollectibleSettings collectible){
        toBuild.add(new Tupel<CollectibleSettings, Double>(collectible, likelyHood));
        probabilitySummed +=likelyHood;
    }

    public Collectible create(){
        double randomNumber = getRandom().nextDouble();
        double sum = 0;
        for(Tupel<CollectibleSettings, Double> collectible : toBuild){
            sum+=collectible.getSecond();
            if(sum/Math.max(probabilitySummed, 1) > randomNumber){
                return build(collectible.getFirst());
            }
        }
        return build(toBuild.get(0).getFirst());
    }

    public boolean isEmpty() {
        return toBuild.isEmpty();
    }
}
