package kinderuni.settings.levelSettings.objectSettings;

import functionalJava.data.tupel.DoubleTupel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class ProjectileSettings extends CollectibleSettings {
    public static final ProjectileSettings DEFAULT = new ProjectileSettings();

    static{
        DEFAULT.cool_down = 0;
        DEFAULT.shooting_x = 1.;
        DEFAULT.shooting_y = 1.;
    }

    private Integer cool_down;
    private Double shooting_x;
    private Double shooting_y;
//    private Integer duration;

    public Integer getCoolDown() {
        return cool_down;
    }

    public Double getShootingX() {
        return shooting_x;
    }

    public void setShootingX(Double shooting_x) {
        this.shooting_x = shooting_x;
    }

    public Double getShootingY() {
        return shooting_y;
    }

    public void setShootingY(Double shooting_y) {
        this.shooting_y = shooting_y;
    }

    public boolean hasShootingDelta() {
        return shooting_x!=null && shooting_y!=null;
    }

    public DoubleTupel getShootingDelta() {
        return new DoubleTupel(shooting_x, shooting_y);
    }

    public boolean hasCoolDown(){
        return cool_down!=null;
    }

    public class Drop{
        private String id;
        private double probability;

        public String getId() {
            return id;
        }

        public double getProbability() {
            return probability;
        }
    }
}
