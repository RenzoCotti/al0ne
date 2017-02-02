package com.al0ne.Items.ConcreteBehaviours.NoBehaviours;

import com.al0ne.Items.Behaviours.Sharp;

/**
 * Created by BMW on 02/02/2017.
 */
public class NotSharp implements Sharp {
    @Override
    public boolean isSharp() {
        return false;
    }
}

