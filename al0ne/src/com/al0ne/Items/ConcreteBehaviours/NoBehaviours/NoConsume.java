package com.al0ne.Items.ConcreteBehaviours.NoBehaviours;

import com.al0ne.Items.Behaviours.Consumable;

/**
 * Created by BMW on 02/02/2017.
 */
public class NoConsume implements Consumable{

    private String isConsumed;

    public NoConsume(String isConsumed) {
        this.isConsumed = isConsumed;
    }

    public NoConsume() {
        this.isConsumed="You can't consume it.";
    }

    @Override
    public boolean isConsumed() {
        System.out.println(isConsumed);
        return false;
    }
}
