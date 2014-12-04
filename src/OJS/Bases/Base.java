package OJS.Bases;

import OJS.Components.RobotComponent;
import OJS.RobotState;

/**
 * Created by Ollie on 27/11/2014.
 */
public abstract class Base extends RobotComponent {

    protected double rotation = 0;
    protected double velocity = 0;

    public Base(RobotState state){
        super(state);
    }

    public double getRotation(){
        return rotation;
    }

    public double getVelocity(){
        return velocity;
    }
}
