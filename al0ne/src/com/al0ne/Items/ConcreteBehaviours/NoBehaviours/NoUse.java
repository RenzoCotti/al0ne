package com.al0ne.Items.ConcreteBehaviours.NoBehaviours;

import com.al0ne.Items.Behaviours.Usable;

/**
 * Created by BMW on 02/02/2017.
 */
public class NoUse implements Usable {
    private String isUsed;
    public NoUse(String isUsed) {
        this.isUsed=isUsed;
    }

    public NoUse() {
        this.isUsed="You use it.";
    }

    @Override
    public boolean isUsed() {
        System.out.println(isUsed);
        return false;
    }
}
