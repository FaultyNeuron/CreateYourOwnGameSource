package kinderuni.gameLogic.objects;

import functionalJava.data.Direction1D;
import functionalJava.data.tupel.DoubleTupel;
import kinderuni.gameLogic.objects.collectible.Collectible;
import kinderuni.graphics.GraphicsObject;
import kinderuni.gameLogic.GameWorld;

/**
 * Created by Georg Plaz.
 */
public class Enemy extends LivingObject {
    private int damage;
    private boolean jumper;
    private int jumpPause = 0;
    private double jumpHeight = Math.random()*3+0.5;
    private Collectible drop;

    public Enemy(DoubleTupel position, GraphicsObject graphicsObject, int damage) {
        this(position, graphicsObject, damage, Math.random() < 0.5);
    }

    public Enemy(DoubleTupel position, GraphicsObject graphicsObject, int damage, boolean jumper) {
        super(position, graphicsObject);
        this.damage = damage;
        this.jumper = jumper;
    }

    public Enemy(DoubleTupel position, GraphicsObject graphicsObject,
                 GameWorld gameWorld, int damage) {
        this(position, graphicsObject, gameWorld, damage, Math.random()<0.5);

    }
    public Enemy(DoubleTupel position, GraphicsObject graphicsObject,
                 GameWorld gameWorld, int damage, boolean jumper) {
        super(position, graphicsObject, gameWorld);
        this.damage = damage;
        this.jumper = jumper;
        gameWorld.add(this);
    }

    public int damageDealt(){
        return damage;
    }

    @Override
    public void update(int time) {
        DoubleTupel playerDelta = getWorld().getPlayer().getCenter().sub(getCenter());
        if(!jumper){
            if(playerDelta.getFirst()>0){
                walk(Direction1D.RIGHT, 1);
            }else{
                walk(Direction1D.LEFT, 1);
            }
//            move(playerDelta.toLength(1));
        }else if (!inAir() && jumpPause--==0){
            DoubleTupel jumpDir = new DoubleTupel(Math.signum(playerDelta.getFirst()), jumpHeight).toLength(5);
            accelerate(jumpDir);

            jumpPause = 50;
        }
        super.update(time);
    }

    @Override
    public void destroy() {
        super.destroy();
        getWorld().destroyEnemy(this);
        if(drop!=null){
            drop.setCenter(getCenter());
            drop.accelerate(0, 5);
            getWorld().add(drop);
        }
    }

    public void setDrop(Collectible drop) {
        this.drop = drop;
    }
}
