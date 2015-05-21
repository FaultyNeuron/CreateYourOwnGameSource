package kinderuni.settings.levelSettings.objectSettings;

import kinderuni.gameLogic.objects.GameObject;

/**
 * Created by Georg Plaz.
 */
public class PhysicsObjectSettings extends GameObjectSettings{
    private Double bounciness = 0.;
    private Double gravity_factor = 1.;

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
}
