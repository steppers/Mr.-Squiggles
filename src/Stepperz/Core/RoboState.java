package Stepperz.Core;

import Stepperz.Waves.Wave;
import robocode.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ollie on 28/11/2014.
 */
public class RoboState {

    public RateControlRobot owner;
    public Vec2 ourPos;

    public OtherRobot target;

    public HashMap<String, OtherRobot> others;

    private ArrayList<Wave> waves;

    public RoboState(RateControlRobot owner){
        this.owner = owner;
        others = new HashMap<String, OtherRobot>();
        waves = new ArrayList<Wave>();
        ourPos = new Vec2();
    }

    public OtherRobot getOtherRobot(String name){
        return others.get(name);
    }

    public void addWave(Wave w){
        waves.add(w);
    }

    public void update(){
        ourPos.x = owner.getX();
        ourPos.y = owner.getY();
        ArrayList<Wave> removal = new ArrayList<Wave>();
        for(Wave w : waves){
            w.update();
            if(w.isBiggerThanField((int)owner.getBattleFieldWidth()))
                removal.add(w);
        }

        for(Wave w : removal){
            waves.remove(w);
        }
    }

    //Returns whether we have scanned the robot previously
    public boolean onHitByBullet(HitByBulletEvent e){
        others.get(e.getName()).expectedEnergy += e.getPower()*3;
        if(!others.containsKey(e.getName()))
            return false;
        return true;
    }

    public void onScannedRobot(ScannedRobotEvent e){
        double absBearing = owner.getHeadingRadians() + e.getBearingRadians();
        double ex = owner.getX() + (e.getDistance() * Math.sin(absBearing));
        double ey = owner.getY() + (e.getDistance() * Math.cos(absBearing));

        if(!others.containsKey(e.getName()))
            others.put(e.getName(), new OtherRobot(e, new Vec2(ex, ey)));
        else
            others.get(e.getName()).addScannedEvent(e, new Vec2(ex, ey));

        if(target == null) {
            target = others.get(e.getName());
        }

        double d = 0;
        if((d = others.get(e.getName()).hasShot()) != 0){
            addWave(new Wave(others.get(e.getName()).getLastKnownPos(), new Vec2(), d));
        }
    }

    public void onRobotDeath(RobotDeathEvent e) {
        if(others.containsKey(e.getName()))
            others.remove(e.getName());
        if(e.getName().equals(target.getName())){
            target = null;
        }
    }

    public void onPaint(Graphics2D g) {
        for(Wave w : waves){
            w.onPaint(g);
        }
        for(OtherRobot r : others.values()){
            r.onPaint(g);
        }
    }

    public void onBulletHit(BulletHitEvent e) {
        if(others.containsKey(e.getBullet().getName()))
            others.get(e.getBullet().getName()).expectedEnergy += e.getBullet().getPower()*3;

        ArrayList<Wave> removal = new ArrayList<Wave>();
        for(Wave w : waves){
            if(w.bullet.equals(e.getBullet()))
                removal.add(w);
        }
        for(Wave w : removal){
            waves.remove(w);
        }
    }
}
