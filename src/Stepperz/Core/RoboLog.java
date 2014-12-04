package Stepperz.Core;

import robocode.ScannedRobotEvent;

/**
 * Created by Ollie on 28/11/2014.
 *
 * This implements a circular stack in order to conserve memory.
 */
public class RoboLog {

    public class LogData {

        public double heading, velocity, energy;
        public long time;

        public LogData(ScannedRobotEvent e){
            this.heading = e.getHeadingRadians();
            this.velocity = e.getVelocity();
            this.time = e.getTime();
            this.energy = e.getEnergy();
        }
    }

    private LogData data[];
    private int logSize;
    private int index = 0;
    private int size = 1;

    public RoboLog(int logSize, ScannedRobotEvent firstEvent){
        data = new LogData[logSize];
        this.logSize = logSize;
        data[0] = new LogData(firstEvent);
        index++;
    }

    public void addToLog(ScannedRobotEvent e){
        data[index] = new LogData(e);
        if(size < logSize)
            size++;
        index++;
        if(index >= logSize)
            index = 0;
    }

    //Zero is top of stack.
    public LogData getData(int index){
        if(index < size){
            int i = this.index - index - 1;
            if(i < 0)
                return data[logSize - i];
            return data[i];
        }
        return null;
    }

    public int getSize(){
        return size;
    }

}
