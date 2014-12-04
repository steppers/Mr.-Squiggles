package OJS.Guns;

import OJS.Components.RobotComponent;
import OJS.RobotState;

/**
 * Created by Ollie on 27/11/2014.
 */
public abstract class Gun extends RobotComponent{

    protected double rotation = 0;

    public Gun(RobotState state){
        super(state);
    }

    public boolean shouldFire(){
        return true;
    }

    public double getPower(){
        return 2;
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

}
