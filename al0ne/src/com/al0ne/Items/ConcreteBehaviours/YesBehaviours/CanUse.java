package com.al0ne.Items.ConcreteBehaviours.YesBehaviours;

import com.al0ne.Items.Behaviours.Usable;

/**
 * Created by BMW on 02/02/2017.
 */
public class CanUse implements Usable{
    private String isUsed;
    public CanUse(String isUsed) {
        this.isUsed=isUsed;
    }

    public CanUse() {
        this.isUsed="You use it.";
    }

    @Override
    public boolean isUsed() {
        System.out.println(isUsed);
        return true;
    }
}
