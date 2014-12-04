package Stepperz.Bases;

import Stepperz.Core.RoboState;
import Stepperz.Core.Vec2;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;

import java.util.Random;

/**
 * Created by Ollie on 28/11/2014.
 */
public class CrazyBase extends Base {

    private Vec2 front;
    private Vec2 back;
    private Random rand;

    private int turnCounter = 0;

    private boolean isAvoidingWall = false;
    private int i;

    public CrazyBase(RoboState state) {
        super(state);
        rand = new Random();
    }

    @Override
    public void execute() {
        rotation = 6;
        if (turnCounter % 40 == 0) {
            velocity = 6;
        }
        if (turnCounter % 40 == 20) {
            velocity = -6;
        }
        turnCounter++;
        wallSmoothing();
    }

    private void wallSmoothing() {
        if(!isAvoidingWall)
            i = rand.nextInt(2);

        isAvoidingWall = false;
        double r = 0;

        while(true) {
            if(velocity > 0){
                front = new Vec2(0, 1).rotate(state.owner.getHeadingRadians()+rotation + r).mul(150).add(state.ourPos);
                if (i == 0) {
                    if (isOutsideWall(front)) {
                        r += 0.02;
                        if(r > Vec2.PI_OVER_2) {
                            r = 0;
                            i = 1;
                        }
                        isAvoidingWall = true;
                    }else
                        break;
                } else {
                    if (isOutsideWall(front)) {
                        r -= 0.02;
                        if(r < -Vec2.PI_OVER_2) {
                            r = 0;
                            i = 0;
                        }
                        isAvoidingWall = true;
                    }else
                        break;
                }
            }else{
                back = new Vec2(0, 1).rotate(state.owner.getHeadingRadians()+rotation+Math.PI + r).mul(150).add(state.ourPos);
                if (i == 0) {
                    if (isOutsideWall(back)) {
                        r += 0.02;
                        if(r > Vec2.PI_OVER_2) {
                            r = 0;
                            i = 1;
                        }
                        isAvoidingWall = true;
                    }else
                        break;
                } else {
                    if (isOutsideWall(back)) {
                        r -= 0.02;
                        if(r < -Vec2.PI_OVER_2) {
                            r = 0;
                            i = 0;
                        }
                        isAvoidingWall = true;
                    }else
                        break;
                }
            }
        }
        rotation += r;
    }

    private boolean isOutsideWall(Vec2 point){
        if(!(point.x > 20 && point.x <= state.owner.getBattleFieldWidth()-20))
            return true;
        if(!(point.y > 20 && point.y <= state.owner.getBattleFieldHeight()-20))
            return true;
        return false;
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        velocity = -velocity;
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        velocity = -velocity;
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        velocity = -velocity;
    }
}
