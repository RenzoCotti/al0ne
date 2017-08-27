package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

/**
 * Created by BMW on 09/07/2017.
 */
public class LockedDoorBehaviour extends Behaviour {

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
