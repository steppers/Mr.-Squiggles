package OJS;

/**
 * Created by Ollie on 27/11/2014.
 */
public class RobotLog {

    private double heading[];
    private double velocity[];
    private long time[];

    private int index = 0;

    private boolean hasData = false;

    public RobotLog(int logSize){
        heading = new double[logSize];
        velocity = new double[logSize];
        time = new long[logSize];
    }

    public void add(double bearing, double velocity, long time){
        this.heading[index] = bearing;
        this.velocity[index] = velocity;
        this.time[index] = time;
        index++;
        if(index == this.heading.length)
            index = 0;
        if(index > 1) hasData = true;
    }

    public double getHeading(int index){
        int i = this.index - index - 1;
        if(i < 0)
            i = heading.length + i;
        return heading[i];
    }

    public double getVelocity(int index){
        int i = this.index - index;
        if(i < 0)
            i = velocity.length + i;
        return velocity[i];
    }

    public long getTime(int index){
        int i = this.index - index;
        if(i < 0)
            i = time.length + i;
        return time[i];
    }

    public boolean hasData(){
        return hasData;
    }

}
