package Stepperz.Waves;

import Stepperz.Core.OtherRobot;
import Stepperz.Core.Vec2;
import robocode.Bullet;

import java.awt.*;

/**
 * Created by Ollie on 29/11/2014.
 */
public class Wave {

    private Vec2 source;
    private Vec2 targetAtStart;
    private double radius;
    private double speed;
    public Bullet bullet;

    private double maxEscapeAngle;

    public Wave(Vec2 source, Vec2 targetAtStart, double power, Bullet bullet){
        this.source = source;
        this.targetAtStart = targetAtStart;
        this.speed = calcBulletSpeed(power);
        this.radius = speed;
        this.bullet = bullet;
        maxEscapeAngle = Math.asin(8.0/speed);
    }

    public Wave(Vec2 source, Vec2 targetAtStart, double power){
        this.source = source;
        this.targetAtStart = targetAtStart;
        this.speed = calcBulletSpeed(power);
        this.radius = speed;
        this.bullet = null;
    }

    public void update(){
        radius += speed;
    }

    public boolean isBiggerThanField(int longestSide){
        if(radius > longestSide)
            return true;
        return false;
    }

    private double calcBulletSpeed(double power){
        return 20 - 3 * power;
    }

    public boolean hasPassed(OtherRobot r){
        if(r.getLastKnownPos().sub(source).length() < radius)
            return true;
        return false;
    }

    public void onPaint(Graphics2D g) {
        g.setColor(new Color(1,1,1,0.5f));
        g.drawOval((int)(source.x-radius), (int)(source.y-radius), (int)radius*2, (int)radius*2);
    }

}
