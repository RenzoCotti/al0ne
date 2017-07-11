package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

/**
 * Created by BMW on 09/07/2017.
 */
public class KeyBehaviour extends Behaviour {
    public KeyBehaviour() {
        super("key");
    }

    @Override
    public String isInteractedWith(Behaviour b) {
        return "0";
    }
}