package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.InteractableBehaviour;

/**
 * Created by BMW on 09/07/2017.
 */
public class KeyBehaviour extends InteractableBehaviour {

    private String doorUnlocked;
    public KeyBehaviour(String s) {
        super("key");
        this.doorUnlocked = s;
    }

    public String getDoorUnlocked() {
        return doorUnlocked;
    }
}
