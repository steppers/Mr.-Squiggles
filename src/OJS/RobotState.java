package OJS;

import robocode.RateControlRobot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/**
 * Created by Ollie on 28/11/2014.
 */
public class RobotState {

    public RateControlRobot owner;

    public ScannedRobot currentTarget;

    public RobotState(RateControlRobot owner){
        this.owner = owner;
    }

    public void onScannedRobot(ScannedRobotEvent e){
        if(currentTarget == null)
            currentTarget = new ScannedRobot(e);
        else if(currentTarget.getName().equals(e.getName())){
            currentTarget.addScannedEvent(e);
        }
    }

    public void onRobotDeath(RobotDeathEvent e) {
        if(e.getName().equals(currentTarget.getName())){
            currentTarget = null;
        }
    }

    public void clear(){
        currentTarget = null;
    }

}
