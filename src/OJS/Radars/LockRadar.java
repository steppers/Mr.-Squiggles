package OJS.Radars;

import OJS.RobotState;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * Created by Ollie on 28/11/2014.
 */
public class LockRadar extends Radar{

    public LockRadar(RobotState state){
        super(state);
    }

    @Override
    public void execute() {
        if(state.currentTarget != null) {
            ScannedRobotEvent e = state.currentTarget.getRecentEvent();
            if(state.owner.getTime() - e.getTime() <= 2) {
                double radarTurn =
                        // Absolute bearing to target
                        state.owner.getHeadingRadians() + state.currentTarget.getRecentEvent().getBearingRadians()
                                // Subtract current radar heading to get turn required
                                - state.owner.getRadarHeadingRadians();

                this.rotation = (1.9 * Utils.normalRelativeAngle(radarTurn));
            }else{
                this.rotation = Double.POSITIVE_INFINITY;
                state.currentTarget = null;
            }
        }else{
            this.rotation = Double.POSITIVE_INFINITY;
        }
    }
}
