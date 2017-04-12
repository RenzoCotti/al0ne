package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Item;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Potion extends Item {
    public Potion(String id, String name, String longDescription, String shortDescription) {
        super(id, name, longDescription, shortDescription, 0.3, Size.SMALL);
        addProperty("consumable");
        addCommand("drink");
    }

}
