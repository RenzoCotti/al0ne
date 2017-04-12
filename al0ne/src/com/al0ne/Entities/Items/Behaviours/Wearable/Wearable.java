package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Item;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 23/03/2017.
 */
public abstract class Wearable extends Item{

    protected String part;

    public Wearable(String id, String name, String description, String shortDescription, double weight, Size size) {
        super(id, name, description, shortDescription, weight, size);
        this.type='w';
    }

    public String getPart() {
        return part;
    }


}
