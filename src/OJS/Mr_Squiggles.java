package OJS;
import OJS.Bases.Base;
import OJS.Guns.Gun;
import OJS.Guns.LinearTargetGun;
import OJS.Radars.LockRadar;
import OJS.Radars.Radar;
import robocode.*;

import java.awt.Color;

/**
 * Mr_Squiggles - a robot by Ollie Steptoe
 */

public class Mr_Squiggles extends RateControlRobot
{
    RobotState state;

    Base base;
    Gun gun;
    Radar radar;

    public static enum STRATEGY{
        ONE_V_ONE,
        BRAWL
    }

    public Mr_Squiggles(){
        super();
        state = new RobotState(this);

        setStrategy(STRATEGY.ONE_V_ONE);
    }

    public void setStrategy(STRATEGY s){
        switch(s){
            case ONE_V_ONE:
                gun = new LinearTargetGun(state);
                radar = new LockRadar(state);
                break;
            case BRAWL:
                gun = new LinearTargetGun(state);
                radar = new LockRadar(state);
                break;
        }
    }

    @Override
    public void run() {
        setColors(Color.black,Color.yellow,Color.black); // body,gun,radar

        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);

        int turnCounter = 0;
        setTurnRate(6);
        while (true) {

            radar.execute();
            gun.execute();
            //base.execute();

            setRadarRotationRateRadians(radar.getRotation());
            setGunRotationRateRadians(gun.getRotation());

            if (turnCounter % 40 == 0) {
                setVelocityRate(6);
            }
            if (turnCounter % 40 == 20) {
                setVelocityRate(-6);
            }
            turnCounter++;
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        state.onScannedRobot(e);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        setVelocityRate(-getVelocityRate());
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        setVelocityRate(-getVelocityRate());
    }

    @Override
    public void onRobotDeath(RobotDeathEvent e) {
        state.onRobotDeath(e);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent e){
        state.clear();
    }
}
