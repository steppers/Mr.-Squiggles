package Stepperz.Guns;

import Stepperz.Core.RoboState;
import Stepperz.Core.Vec2;
import Stepperz.Waves.Wave;
import robocode.Bullet;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ollie on 03/12/2014.
 */
public class PredictiveGun extends Gun{

    private ArrayList<Wave> waves;
    private int data[] = new int[100];

    public PredictiveGun(RoboState state) {
        super(state);
        waves = new ArrayList<Wave>();
    }

    @Override
    public void execute() {
        updateAndManageWaves();

        if(state.target == null)
            return;

        ScannedRobotEvent e = state.target.getLatestEvent();
        if(state.owner.getTime() - e.getTime() >= 2)
            return;

        double firePower = 2;

        double fieldWidth = state.owner.getBattleFieldWidth();
        double fieldHeight = state.owner.getBattleFieldHeight();

        double bv = calcBulletVelocity(firePower);

        double eV = e.getVelocity();
        double eVcosE = eV * Math.cos(e.getHeadingRadians());
        double eVsinE = eV * Math.sin(e.getHeadingRadians());
        double absBearing = state.owner.getHeadingRadians() + e.getBearingRadians();
        double ex = state.owner.getX() + (e.getDistance() * Math.sin(absBearing));
        double ey = state.owner.getY() + (e.getDistance() * Math.cos(absBearing));

        boolean useCircular = false;
        double deltaHeading = 0;
        if(state.target.hasPastData()) {
            useCircular = true;
            deltaHeading = state.target.getTurnRate();
        }

        double dt = 0.5;
        double x = ex, y = ey;
        if(useCircular) {
            while (true) {
                eVcosE = eV * Math.cos(e.getHeadingRadians() + dt * deltaHeading);
                eVsinE = eV * Math.sin(e.getHeadingRadians() + dt * deltaHeading);
                //Only iterate if on the battlefield
                if (x >= 0 && x < fieldWidth)
                    x += eVsinE * 0.5;
                if (y >= 0 && y < fieldHeight)
                    y += eVcosE * 0.5;
                double bTime = bulletTimeTo(x, y, bv);
                if (bTime < dt)
                    break;
                dt += 0.5;
            }
        }else{
            while (true) {
                //Only iterate if on the battlefield
                if (x >= 0 && x < fieldWidth)
                    x += eVsinE * 0.5;
                if (y >= 0 && y < fieldHeight)
                    y += eVcosE * 0.5;
                double bTime = bulletTimeTo(x, y, bv);
                if (bTime < dt)
                    break;
                dt += 0.5;
            }
        }

        double relX = x - state.owner.getX();
        double relY = y - state.owner.getY();
        double gunAngle = state.owner.getGunHeadingRadians();

        double futureBearing = Vec2.absAngle(relX, relY);

        rotation = futureBearing - gunAngle;

        Bullet b;
        if((b = state.owner.setFireBullet(firePower)) != null)
            waves.add(new Wave(new Vec2(state.ourPos.x, state.ourPos.y), state.target.getLastKnownPos(), firePower, b));
    }

    private void updateAndManageWaves(){
        ArrayList<Wave> removal = new ArrayList<Wave>();
        for(Wave w : waves){
            w.update();
            if(w.isBiggerThanField((int)state.owner.getBattleFieldWidth()))
                removal.add(w);
            if(w.hasPassed(state.target)) {
                //Add data here
                removal.add(w);
            }
        }

        for(Wave w : removal){
            waves.remove(w);
        }
    }

    @Override
    public void onPaint(Graphics2D g){
        for(Wave w : waves){
            w.onPaint(g);
        }
    }

}
