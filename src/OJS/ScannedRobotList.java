package OJS;

import robocode.ScannedRobotEvent;

import java.util.ArrayList;

/**
 * Created by Ollie on 27/11/2014.
 */
public class ScannedRobotList {

    ArrayList<ScannedRobot> scannedBots;

    public ScannedRobotList(){
        scannedBots = new ArrayList<ScannedRobot>();
    }

    public ScannedRobot getScannedRobot(ScannedRobotEvent e){
        ScannedRobot robot = null;
        boolean alreadyScanned = false;
        for(ScannedRobot r : scannedBots){
            if(r.getName().equals(e.getName())){
                r.addScannedEvent(e);
                robot = r;
                alreadyScanned = true;
                break;
            }
        }
        if(!alreadyScanned) {
            robot = new ScannedRobot(e);
            robot.addScannedEvent(e);
            scannedBots.add(robot);
        }
        return robot;
    }

}
