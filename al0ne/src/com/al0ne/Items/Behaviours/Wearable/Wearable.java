package com.al0ne.Items.Behaviours.Wearable;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public abstract class Wearable extends Item{

    protected String part;

    public Wearable(String id, String name, String description, String shortDescription, double weight) {
        super(id, name, description, shortDescription, weight);
    }

    public String getPart() {
        return part;
    }


}
