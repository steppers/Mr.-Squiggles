package Stepperz.Core;

import robocode.ScannedRobotEvent;

import java.awt.*;

/**
 * Created by Ollie on 28/11/2014.
 */
public class OtherRobot {

    public static final double MAX_SPEED = 8;

    private ScannedRobotEvent latest;
    private RoboLog log;

    private Vec2 lastKnownPos;
    private String name;

    public double expectedEnergy = 100;

    public OtherRobot(ScannedRobotEvent latestEvent, Vec2 lastKnownPos){
        this.latest = latestEvent;
        this.name = latestEvent.getName();
        log = new RoboLog(600, latestEvent);    //Create a log of the last 600 ticks
        this.lastKnownPos = lastKnownPos;
    }

    public double hasShot(){
        double diff = 0;
        if(expectedEnergy > latest.getEnergy()){
            diff = expectedEnergy - latest.getEnergy();
            if(diff > 3)
                diff = 0;
        }
        expectedEnergy = latest.getEnergy();
        return diff;
    }

    public void addScannedEvent(ScannedRobotEvent e, Vec2 lastKnownPos){
        this.latest = e;
        log.addToLog(e);
        this.lastKnownPos = lastKnownPos;
    }

    public String getName(){
        return name;
    }

    public ScannedRobotEvent getLatestEvent(){
        return latest;
    }

    public Vec2 getLastKnownPos(){
        return lastKnownPos;
    }

    public boolean hasPastData(){
        return log.getSize() > 1 ? true : false;
    }

    public double getTurnRate(){
        double turnRate = (latest.getHeadingRadians() - log.getData(1).heading) / (latest.getTime() - log.getData(1).time);
        return turnRate;
    }

    public void onPaint(Graphics2D g) {
        g.setColor(new Color(1,0,0,0.5f));
        g.fillRect((int)lastKnownPos.x - 18, (int)lastKnownPos.y - 18, 36, 36);
    }

}
