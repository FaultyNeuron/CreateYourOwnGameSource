package kinderuni.settings.levelSettings.objectSettings;

import kinderuni.gameLogic.objects.GameObject;

/**
 * Created by Georg Plaz.
 */
public class PhysicsObjectSettings extends GameObjectSettings{
    private Double bounciness;
    private Double gravity_factor;

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
}
