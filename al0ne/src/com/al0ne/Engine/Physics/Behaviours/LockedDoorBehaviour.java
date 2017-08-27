package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.InteractableBehaviour;

/**
 * Created by BMW on 09/07/2017.
 */
public class LockedDoorBehaviour extends InteractableBehaviour {

    private String doorName;
    private String direction;
    public LockedDoorBehaviour(String doorName, String direction) {
        super("lockeddoor");
        this.doorName = doorName;
        this.direction = direction;
    }

    public String getDoorName() {
        return doorName;
    }

    public String getDirection() {
        return direction;
    }
}
