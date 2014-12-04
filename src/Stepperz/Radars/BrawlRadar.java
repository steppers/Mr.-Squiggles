package Stepperz.Radars;

import Stepperz.Core.RoboState;
import Stepperz.Core.Vec2;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * Created by Ollie on 29/11/2014.
 */
public class BrawlRadar extends Radar {

    private boolean scanAll = true;

    public BrawlRadar(RoboState state){
        super(state);
    }

    @Override
    public void execute() {
        if(state.owner.getOthers() > state.others.size()) {
            this.rotation = Double.POSITIVE_INFINITY;
            return;
        }

        if (state.target != null) {
            ScannedRobotEvent e = state.target.getLatestEvent();
            if (state.owner.getTime() - e.getTime() <= 2) {
                double radarTurn = state.owner.getHeadingRadians() + e.getBearingRadians()
                        - state.owner.getRadarHeadingRadians();

                this.rotation = (2 * Utils.normalRelativeAngle(radarTurn));
            } else {
                this.rotation = Double.POSITIVE_INFINITY;
                state.target = null;
            }
        } else {
            this.rotation = Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e){
//        if(distanceBetween(state.target.getLastKnownPos(), new Vec2(state.owner.getX(), state.owner.getY()))
//                > distanceBetween(state.getOtherRobot(e.getName()).getLastKnownPos(), new Vec2(state.owner.getX(), state.owner.getY()))) {
//            state.target = state.getOtherRobot(e.getName());
//        }
    }

    private double distanceBetween(Vec2 p1, Vec2 p2){
        return p1.sub(p2).length();
    }
}
