package OJS;

import robocode.ScannedRobotEvent;

/**
 * Created by Ollie on 27/11/2014.
 */
public class ScannedRobot {

    private RobotLog log;
    private ScannedRobotEvent recentEvent;

    public ScannedRobot(ScannedRobotEvent e){
        log = new RobotLog(1000);
        addScannedEvent(e);
    }

    public void addScannedEvent(ScannedRobotEvent e){
        this.recentEvent = e;
        log.add(e.getHeadingRadians(), e.getVelocity(), e.getTime());
    }

    public String getName(){
        return recentEvent.getName();
    }

    public ScannedRobotEvent getRecentEvent(){
        return recentEvent;
    }

    public boolean hasPastData(){
        return log.hasData();
    }

    public double getTurnRate(){
        return (recentEvent.getHeading() - log.getHeading(1)) / (recentEvent.getTime() - log.getTime(1));
    }

}
