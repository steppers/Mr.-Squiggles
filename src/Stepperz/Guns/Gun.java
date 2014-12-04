package Stepperz.Guns;

import Stepperz.Core.Component;
import Stepperz.Core.RoboState;

import java.awt.*;

/**
 * Created by Ollie on 28/11/2014.
 */
public abstract class Gun extends Component{

    protected double rotation;

    public Gun(RoboState state) {
        super(state);
    }

    public double getRotation(){
        return rotation;
    }

    //TODO: Add compensation for gun movement time aswell
    protected double bulletTimeTo(double x, double y, double bv){
        double dist = Math.sqrt((x - state.owner.getX()) * (x - state.owner.getX()) + (y - state.owner.getY()) * (y - state.owner.getY()));
        return dist/bv;
    }

    protected double calcBulletVelocity(double power){
        return 20 - 3 * power;
    }

    @Override
    public void onPaint(Graphics2D g){}

}
