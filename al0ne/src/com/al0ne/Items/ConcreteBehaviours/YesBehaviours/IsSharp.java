package com.al0ne.Items.ConcreteBehaviours.YesBehaviours;

import com.al0ne.Items.Behaviours.Sharp;

/**
 * Created by BMW on 02/02/2017.
 */
public class IsSharp implements Sharp{
    private int sharpness;

    public IsSharp(int sharpness) {
        this.sharpness = sharpness;
    }

    @Override
    public boolean isSharp() {
        return true;
    }
}
