package com.al0ne.ConcreteEntities.Items.Types.Wearable;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Enums.Material;

/**
 * Created by BMW on 23/03/2017.
 */
public abstract class Wearable extends Item{

    protected String part;

    public Wearable(String id, String name, String description, double weight, Size size, Material material) {
        super(id, name, description, weight, size, material, null);
    }

    public String getPart() {
        return part;
    }


}
