package OJS.Components;

import OJS.RobotState;

/**
 * Created by Ollie on 28/11/2014.
 */
public abstract class RobotComponent {

    protected RobotState state;

    public RobotComponent(RobotState state){
        this.state = state;
    }

    public abstract void execute();

}
