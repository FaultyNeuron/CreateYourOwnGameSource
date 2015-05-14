package functionalJava.data.shape.box;

import functionalJava.data.tupel.DoubleTupel;

/**
 * Created by Georg Plaz.
 */
public class FastAccessBox extends ModifiableBox {
    private DoubleTupel leftLower;
    private DoubleTupel rightLower;
    private DoubleTupel leftUpper;
    private DoubleTupel rightUpper;

    public FastAccessBox(DoubleTupel center, DoubleTupel dimensions) {
        super(center, dimensions);
        init();
    }

    private void init(){
        leftLower = super.getLeftLower();
        rightLower = super.getRightLower();
        leftUpper = super.getLeftUpper();
        rightUpper = super.getRightUpper();
    }

    @Override
    public void setCenter(DoubleTupel newCenter) {
        super.setCenter(newCenter);
        init();
    }

    @Override
    public DoubleTupel getLeftLower() {
        return leftLower;
    }

    @Override
    public DoubleTupel getRightLower() {
        return rightLower;
    }

    @Override
    public DoubleTupel getLeftUpper() {
        return leftUpper;
    }

    @Override
    public DoubleTupel getRightUpper() {
        return rightUpper;
    }

    @Override
    public double getLeft() {
        return leftLower.getFirst();
    }

    @Override
    public double getLower() {
        return leftLower.getSecond();
    }

    @Override
    public double getRight() {
        return rightUpper.getFirst();
    }

    @Override
    public double getUpper() {
        return rightUpper.getSecond();
    }
}
