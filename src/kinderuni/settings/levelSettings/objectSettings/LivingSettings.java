package kinderuni.settings.levelSettings.objectSettings;

/**
 * Created by Georg Plaz.
 */
public class LivingSettings extends PhysicsObjectSettings {
    private Integer hp;
    private Double walking_speed;
    private Double flying_speed;
    private Double jump_power;

    public int getHp() {
        return hp;
    }

    public boolean hasHp() {
        return hp!=null;
    }

    public double getWalkingSpeed() {
        return walking_speed;
    }

    public void setWalkingSpeed(double walkingSpeed) {
        this.walking_speed = walkingSpeed;
    }

    public boolean hasWalkingSpeed() {
        return walking_speed != null;
    }

    public double getJumpPower() {
        return jump_power;
    }

    public void setJumpPower(double jumpPower) {
        this.jump_power = jumpPower;
    }

    public boolean hasJumpPower() {
        return jump_power !=null;
    }

}
