package Stepperz.Radars;

import Stepperz.Core.RoboState;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * Created by Ollie on 28/11/2014.
 */
public class TrackingRadar extends Radar{

    public TrackingRadar(RoboState state){
        super(state);
    }

    @Override
    public void execute() {
        if(state.target != null) {
            ScannedRobotEvent e = state.target.getLatestEvent();
            if(state.owner.getTime() - e.getTime() <= 2) {
                double radarTurn = state.owner.getHeadingRadians() + e.getBearingRadians()
                                - state.owner.getRadarHeadingRadians();

                this.rotation = (1.9 * Utils.normalRelativeAngle(radarTurn));
            }else{
                this.rotation = Double.POSITIVE_INFINITY;
                state.target = null;
            }
        }else{
            this.rotation = Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e){

    }
}
