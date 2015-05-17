package kinderuni.gameLogic.objects;

import functionalJava.data.Direction1D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.graphics.GraphicsObject;

/**
 * Created by Georg Plaz.
 */
public class Enemy extends LivingObject {
    private int hp;
    private int damage;
    private int jumpPause = 0;
    private int initialJumpPause;
    private double walkingSpeed;
    private double jumpPower;
    private Collectible drop;

    public Enemy(DoubleTupel position, GraphicsObject graphicsObject, int hp, int damage,
                 double walkingSpeed, double jumpPower, int jumpPause) {
        super(position, graphicsObject, hp);
        this.damage = damage;
        this.walkingSpeed = walkingSpeed;
        this.jumpPower = jumpPower;
        this.initialJumpPause = jumpPause;
    }

    public int damageDealt(){
        return damage;
    }

    @Override
    public void update(int time) {
        DoubleTupel playerDelta = getWorld().getPlayer().getCenter().sub(getCenter());
        Direction1D direction;
        if(playerDelta.getFirst()>0){
            direction = Direction1D.RIGHT;
        }else{
            direction = Direction1D.LEFT;
        }
        walk(direction, walkingSpeed);
//            move(playerDelta.toLength(1));
        if (!inAir() && jumpPause--==0){
            DoubleTupel jumpDir = direction.to2D().toVector().add(0, 1).toLength(jumpPower);
            accelerate(jumpDir);

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
}
