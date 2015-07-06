package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class PhysicsObjectSettings extends GameObjectSettings{
    private Double bounciness;
    private Double gravity_factor;
    private Double friction_coefficient;

    public Double getGravityFactor() {
        return gravity_factor;
    }
    public double getBounciness() {
        return bounciness;
    }

    public boolean hasBounciness() {
        return bounciness!=null;
    }

    public boolean hasGravityFactor() {
        return gravity_factor!=null;
    }

    public void setBounciness(double bounciness) {
        this.bounciness = bounciness;
    }

    public void setGravityFactor(double gravityFactor) {
        this.gravity_factor = gravityFactor;
    }

    public double getFrictionCoefficient() {
        return friction_coefficient;
    }

    public void setFrictionCoefficient(Double frictionCoefficient) {
        this.friction_coefficient = frictionCoefficient;
    }

    public boolean hasFrictionCoefficient(){
        return friction_coefficient!=null;
    }
}
