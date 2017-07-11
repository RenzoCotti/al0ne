package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 09/07/2017.
 */
public class IronBehaviour extends Behaviour {

    public IronBehaviour() {
        super("iron");
    }

    @Override
    public String isInteractedWith(Behaviour b) {
        String name = b.getName();

        switch (name){
            case "acid":
                printToLog("It gets corroded!");
                return "4";
            case "water":
                printToLog("It rusts and crumbles!");
                return "4";
        }
        return "0";
    }
}
