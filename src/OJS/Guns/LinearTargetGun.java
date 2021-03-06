package OJS.Guns;

import OJS.RobotState;
import robocode.ScannedRobotEvent;

/**
 * Created by Ollie on 28/11/2014.
 */
public class LinearTargetGun extends Gun{

    public LinearTargetGun(RobotState state){
        super(state);
    }

    @Override
    public void execute() {
        if(state.currentTarget == null)
            return;
        ScannedRobotEvent e = state.currentTarget.getRecentEvent();

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
        if(state.currentTarget.hasPastData()) {
//            useCircular = true;
//            deltaHeading = e.getHeadingRadians() - state.currentTarget.getTurnRate();
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

        double futureBearing;
        if(relX > 0 && relY > 0)
            futureBearing = Math.atan(relX / relY);
        else if(relX > 0 && relY < 0)
            futureBearing = Math.PI/2 - Math.atan(relY / relX);
        else if(relX < 0 && relY < 0)
            futureBearing = Math.PI + Math.atan(relX / relY);
        else
            futureBearing = (3f/2)*Math.PI - Math.atan(relY / relX);

        rotation = futureBearing - gunAngle;
        state.owner.fire(firePower);
    }
}
