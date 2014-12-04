package Stepperz.Core;

import java.awt.*;

/**
 * Created by Ollie on 28/11/2014.
 */
public abstract class Component {

    protected RoboState state;

    public Component(RoboState state){
        this.state = state;
    }

    public abstract void execute();

    public abstract void onPaint(Graphics2D g);


}
