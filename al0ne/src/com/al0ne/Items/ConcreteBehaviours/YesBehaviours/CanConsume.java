package com.al0ne.Items.ConcreteBehaviours.YesBehaviours;

import com.al0ne.Items.Behaviours.Consumable;

/**
 * Created by BMW on 02/02/2017.
 */
public class CanConsume implements Consumable{

    private String isConsumed;
    private int value;

    public CanConsume(String isConsumed, int value) {
        this.isConsumed = isConsumed;
        this.value=value;
    }

    public CanConsume(int value) {
        this.isConsumed="You consume it.";
        this.value=value;
    }

    @Override
    public boolean isConsumed() {
        System.out.println(isConsumed);
        //use value
        return true;
    }
}
