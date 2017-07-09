package com.al0ne.Engine.Physics;

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
                return 0;
        }
        return 0;
    }
}
