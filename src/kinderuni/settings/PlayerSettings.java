package kinderuni.settings;

import kinderuni.gameLogic.objects.Player;

/**
 * Created by Georg Plaz.
 */
public class PlayerSettings {
    public static final PlayerSettings DEFAULT = createDefault();
    private double height;
    private double jumpPower;
    private double moveSpeed;
    private int lives;
    private int hp;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(double jumpPower) {
        this.jumpPower = jumpPower;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
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
        toReturn.setLives(3);
        toReturn.setHp(3);
        toReturn.setHeight(75);

        return toReturn;
    }
}
