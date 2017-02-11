package com.al0ne.Items.Behaviours;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 11/02/2017.
 */
public abstract class Food extends Item {
    protected int value;
    public Food(String id, String name, String description, double weight) {
        super(id, name, description, weight);
    }

    public int getValue() {
        return value;
    }

    public void used(){
        System.out.println("Yum! you eat the "+name+"; +"+value+" food.");
    }
}
