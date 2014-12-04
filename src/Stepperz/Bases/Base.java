package Stepperz.Bases;

import Stepperz.Core.Component;
import Stepperz.Core.RoboState;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;

import java.awt.*;

/**
 * Created by Ollie on 28/11/2014.
 */
public abstract class Base extends Component {

    protected double rotation;
    protected double velocity;

    public Base(RoboState state){
        super(state);
    }

    public abstract void onHitWall(HitWallEvent e);

    public abstract void onHitByBullet(HitByBulletEvent e);

    public double getRotation(){
        return rotation;
    }

    public double getVelocity(){
        return velocity;
    }

    public abstract void onHitRobot(HitRobotEvent e);

    @Override
    public void onPaint(Graphics2D g){}


}
