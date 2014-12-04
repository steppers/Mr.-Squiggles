package OJS;

/**
 * Created by Ollie on 27/11/2014.
 */
public class MovementPattern {

    private double headingDelta[];
    private double velocityDelta[];
    private long timeDelta[];

    public MovementPattern(int length){
        headingDelta = new double[length];
        velocityDelta = new double[length];
        timeDelta = new long[length];
    }

    public float match(MovementPattern p){
        if(p.getLength() != headingDelta.length){
            System.err.println("ERR: Pattern matcher needs same sized patterns!");
            return 0;
        }

        float m = 0;
        for(int i = 0; i < getLength(); i++){
            m += Math.abs(headingDelta[i] - p.getDeltaHeading(i)) + Math.abs(velocityDelta[i] - p.getDeltaVelocity(i)) + Math.abs(timeDelta[i] - p.getDeltaTime(i));
        }

        return m/getLength();
    }

    public void setData(double[] dh, double[] dv, long[] dt){
        this.headingDelta = dh;
        this.velocityDelta = dv;
        this.timeDelta = dt;
    }

    public int getLength(){
        return headingDelta.length;
    }

    public double getDeltaHeading(int index){
        return headingDelta[index];
    }

    public double getDeltaVelocity(int index){
        return velocityDelta[index];
    }

    public double getDeltaTime(int index){
        return timeDelta[index];
    }

}
