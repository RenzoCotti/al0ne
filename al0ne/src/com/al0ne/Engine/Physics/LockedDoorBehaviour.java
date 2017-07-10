package com.al0ne.Engine.Physics;

/**
 * Created by BMW on 09/07/2017.
 */
public class LockedDoorBehaviour extends Behaviour{
    public LockedDoorBehaviour() {
        super("lockeddoor");
    }

    @Override
    public int isInteractedWith(Behaviour b) {
        return 0;
    }
}
