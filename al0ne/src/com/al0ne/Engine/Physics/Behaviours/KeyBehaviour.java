package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

/**
 * Created by BMW on 09/07/2017.
 */
public class KeyBehaviour extends Behaviour {

    private String doorUnlocked;
    public KeyBehaviour(String s) {
        super("key");
        this.doorUnlocked = s;
    }

    public String getDoorUnlocked() {
        return doorUnlocked;
    }
}
