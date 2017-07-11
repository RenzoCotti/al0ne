package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 09/07/2017.
 */
public class WaterBehaviour extends Behaviour {


    public WaterBehaviour() {
        super("water");
    }

    @Override
    public String isInteractedWith(Behaviour b) {

        if(b.getName().equals("iron")){
            printToLog("The iron gets corroded!");
            return "45";
        }
        return "0";
    }
}
