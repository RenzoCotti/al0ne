package com.al0ne.Engine.Physics;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 09/07/2017.
 */
public class Iron extends Behaviour{

    public Iron() {
        super("iron");
    }

    @Override
    public int isInteractedWith(Behaviour b) {
        String name = b.getName();

        switch (name){
            case "acid":
                printToLog("It gets corroded!");
                return 4;
            case "water":
                printToLog("It rusts and crumbles!");
                return 4;
        }
        return 0;
    }
}
