package kinderuni.gameLogic.objects.collectible;

import functionalJava.data.tupel.Tupel;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class DropFactory {
    private System system;
    private double probabilitySummed = 0;
    private List<Tupel<Collectible, Double>> toClone = new LinkedList<>();
    private Random random;

    public DropFactory(Random random) {
        this.random = random;
    }

    public void addBluePrint(double likelyHood, Collectible collectible){
        toClone.add(new Tupel<Collectible, Double>(collectible, likelyHood));
        probabilitySummed +=likelyHood;
    }

    public Collectible create(){
        double randomNumber = random.nextDouble();
        double sum = 0;
        for(Tupel<Collectible, Double> collectible : toClone){
            sum+=collectible.getSecond();
            if(sum/Math.max(probabilitySummed, 1) > randomNumber){
                return collectible.getFirst().copy();
            }
        }
        return toClone.get(0).getFirst();
    }

    public boolean isEmpty() {
        return toClone.isEmpty();
    }
}
