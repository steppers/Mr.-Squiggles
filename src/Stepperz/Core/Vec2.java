package Stepperz.Core;

/**
 * Created by Ollie on 28/11/2014.
 */
public class Vec2 {

    public static double PI_OVER_2 = Math.PI / 2;
    public static double PI_3_OVER_2 = 3 * Math.PI / 2;

    public double x, y;

    public Vec2(){
        x = 0;
        y = 0;
    }

    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 other){
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vec2 sub(Vec2 other){
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vec2 mul(double s){
        this.x *= s;
        this.y *= s;
        return this;
    }

    public double length(){
        return Math.sqrt((x*x) + (y*y));
    }

    public Vec2 normalise(){
        double l = length();
        x = x / l;
        y = y / l;
        return this;
    }

    public Vec2 rotate(double angle){
        double r = absAngle() + angle;
        x = Math.sin(r);
        y = Math.cos(r);
        return this;
    }

    public double absAngle(){
        double angle = 0;
        if(x > 0 && y > 0)
            angle = Math.atan(x/y);
        else if(x > 0 && y < 0)
            angle = PI_OVER_2 - Math.atan(y/x);
        else if(x < 0 && y < 0)
            angle = Math.PI + Math.atan(x/y);
        else if(x < 0 && y > 0)
            angle = PI_3_OVER_2 - Math.atan(y/x);
        return angle;
    }

    public static double absAngle(double x, double y){
        double angle = 0;
        if(x > 0 && y > 0)
            angle = Math.atan(x/y);
        else if(x > 0 && y < 0)
            angle = PI_OVER_2 - Math.atan(y/x);
        else if(x < 0 && y < 0)
            angle = Math.PI + Math.atan(x/y);
        else
            angle = PI_3_OVER_2 - Math.atan(y/x);
        return angle;
    }

}
