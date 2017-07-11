package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

/**
 * Created by BMW on 09/07/2017.
 */
public class PotionBehaviour extends Behaviour {
    public PotionBehaviour() {
        super("potion");
    }

    @Override
    public String isInteractedWith(Behaviour b) {
        return "0";
    }
}