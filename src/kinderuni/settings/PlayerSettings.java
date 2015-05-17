package kinderuni.settings;

import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;

/**
 * Created by Georg Plaz.
 */
public class PlayerSettings {
    public static final PlayerSettings DEFAULT = createDefault();
    private GraphicsSettings graphics;
    private double height;
    private double jump_power;
    private double move_speed;
    private double enemy_throwback_power;

    private int life;
    private int hp;


    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getJumpPower() {
        return jump_power;
    }

    public void setJumpPower(double jumpPower) {
        this.jump_power = jumpPower;
    }

    public double getMoveSpeed() {
        return move_speed;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.move_speed = moveSpeed;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public static PlayerSettings createDefault(){
        PlayerSettings toReturn = new PlayerSettings();
        toReturn.setHeight(75);
        toReturn.setJumpPower(7);
        toReturn.setMoveSpeed(3);
        toReturn.setLife(3);
        toReturn.setHp(3);

        return toReturn;
    }

    public GraphicsSettings getGraphicsSettings() {
        return graphics;
    }

    public double getEnemyThrowbackPower() {
        return enemy_throwback_power;
    }
}
