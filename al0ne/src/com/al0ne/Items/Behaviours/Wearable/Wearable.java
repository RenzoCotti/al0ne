package com.al0ne.Items.Behaviours.Wearable;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 23/03/2017.
 */
public abstract class Wearable extends Item{

    protected String part;

    public Wearable(String id, String name, String description, String shortDescription, double weight) {
        super(id, name, description, shortDescription, weight);
        this.type='w';
    }

    public String getPart() {
        return part;
    }


}