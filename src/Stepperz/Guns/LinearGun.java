package Stepperz.Guns;

import Stepperz.Core.RoboState;
import Stepperz.Core.Vec2;
import Stepperz.Waves.Wave;
import robocode.Bullet;
import robocode.ScannedRobotEvent;

/**
 * Created by Ollie on 28/11/2014.
 */
public class LinearGun extends Gun {

    public LinearGun(RoboState state) {
        super(state);
    }

    @Override
    public void execute() {
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
            state.addWave(new Wave(new Vec2(state.ourPos.x, state.ourPos.y), state.target.getLastKnownPos(), firePower, b));
    }
}
