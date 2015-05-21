package kinderuni.gameLogic.objects;

import functionalJava.data.HorizontalDirection;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.collectible.Collectible;

/**
 * Created by Georg Plaz.
 */
public class Enemy extends LivingObject {
    private int damage;
    private int jumpPause = 0;
    private int initialJumpPause;
    private Collectible drop;

    public Enemy(int damage, int jumpPause) {
        this.damage = damage;
        this.initialJumpPause = jumpPause;
    }

    public int damageDealt(){
        return damage;
    }

    @Override
    public void update(int time) {
        DoubleTupel playerDelta = getWorld().getPlayer().getCenter().sub(getCenter());
        HorizontalDirection direction;
        if(playerDelta.getFirst()>0){
            direction = HorizontalDirection.RIGHT;
        }else{
            direction = HorizontalDirection.LEFT;
        }
        walk(direction);
//            move(playerDelta.toLength(1));
        if (!inAir() && jumpPause--<=0){
            DoubleTupel jumpDir = direction.to2D().toVector().add(0, 1).toLength(getJumpPower());
            jump(jumpDir);

            jumpPause = initialJumpPause;
        }
        super.update(time);
    }

    @Override
    public void destroy() {
        super.destroy();
        getWorld().destroyEnemy(this);
        if(drop!=null){
            drop.setCenter(getCenter());
            drop.accelerate(0, drop.getDropAcceleration());
            getWorld().add(drop);
        }
    }

    public void setDrop(Collectible drop) {
        this.drop = drop;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "position=" + getCenter() +
                ", speed=" + getSpeed() +
                ", damage=" + damage +
                ", jumpPause=" + jumpPause +
                ", initialJumpPause=" + initialJumpPause +
                ", drop=" + drop +
                '}';
    }
}
