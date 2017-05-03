package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Item;
import com.al0ne.Engine.Enums.Size;
import com.al0ne.Entities.Items.Behaviours.Material;

/**
 * Created by BMW on 23/03/2017.
 */
public abstract class Wearable extends Item{

    protected String part;

    public Wearable(String id, String name, String description, double weight, Size size, Material material) {
        super(id, name, description, weight, size, material);
        this.type='w';
    }

    public String getPart() {
        return part;
    }


}
