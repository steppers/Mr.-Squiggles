package OJS.Radars;

import OJS.Components.RobotComponent;
import OJS.RobotState;

/**
 * Created by Ollie on 27/11/2014.
 */
public abstract class Radar extends RobotComponent{

    protected double rotation;

    public Radar(RobotState state) {
        super(state);
    }

    public double getRotation() {
        return this.rotation;
    }

}
