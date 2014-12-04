package Stepperz.Radars;
import Stepperz.Core.Component;
import Stepperz.Core.RoboState;
import robocode.ScannedRobotEvent;

import java.awt.*;

/**
 * Created by Ollie on 28/11/2014.
 */
public abstract class Radar extends Component{

    protected double rotation;

    public Radar(RoboState state) {
        super(state);
    }

    public abstract void onScannedRobot(ScannedRobotEvent e);

    public double getRotation(){
        return rotation;
    }

    @Override
    public void onPaint(Graphics2D g){}
}
