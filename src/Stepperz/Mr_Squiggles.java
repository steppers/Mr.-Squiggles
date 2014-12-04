package Stepperz;

import Stepperz.Bases.Base;
import Stepperz.Bases.CrazyBase;
import Stepperz.Bases.OrbitBase;
import Stepperz.Bases.PersuingBase;
import Stepperz.Core.RoboState;
import Stepperz.Guns.Gun;
import Stepperz.Guns.LinearGun;
import Stepperz.Radars.BrawlRadar;
import Stepperz.Radars.Radar;
import Stepperz.Radars.TrackingRadar;
import robocode.*;

import java.awt.*;

/**
 * Created by Ollie on 28/11/2014.
 */
public class Mr_Squiggles extends  RateControlRobot{

    RoboState state;

    Base base;
    Gun gun;
    Radar radar;

    STRATEGY strategy;

    public static enum STRATEGY{
        ONE_V_ONE,
        BRAWL
    }

    public void setStrategy(STRATEGY s){
        switch(s){
            case ONE_V_ONE:
                radar = new TrackingRadar(state);
                base = new OrbitBase(state);
                gun = new LinearGun(state);
                break;
            case BRAWL:
                radar = new BrawlRadar(state);
                base = new CrazyBase(state);
                gun = new LinearGun(state);
                break;
        }
        strategy = s;
    }

    @Override
    public void run() {
        state = new RoboState(this);

        setColors(Color.black,Color.pink,Color.pink); // body,gun,radar

        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        if(getOthers() > 1)
            setStrategy(STRATEGY.BRAWL);
        else
            setStrategy(STRATEGY.ONE_V_ONE);

        while (true) {

            if(getOthers() == 1 && strategy != STRATEGY.ONE_V_ONE)
                setStrategy(STRATEGY.ONE_V_ONE);

            state.update();

            //Update components
            radar.execute();
            setRadarRotationRateRadians(radar.getRotation());

            gun.execute();
            setGunRotationRateRadians(gun.getRotation());

            base.execute();
            setVelocityRate(base.getVelocity());
            setTurnRateRadians(base.getRotation());

            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        state.onScannedRobot(e);
        radar.onScannedRobot(e);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        base.onHitByBullet(e);
    }

    @Override
    public void onBulletHit(BulletHitEvent e){
        state.onBulletHit(e);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        base.onHitWall(e);
    }

    @Override
    public void onHitRobot(HitRobotEvent e){
        base.onHitRobot(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent e) {
        state.onRobotDeath(e);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent e){

    }

    public void onPaint(Graphics2D g) {
        state.onPaint(g);
    }
}
