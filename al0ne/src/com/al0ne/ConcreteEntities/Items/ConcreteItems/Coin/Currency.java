package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Enums.Material;

/**
 * Created by BMW on 01/05/2017.
 */
public abstract class Currency extends Item{
    protected int value;
    public Currency(String id, String name, String description, double weight, Size s, Material material) {
        super(id, name, description, weight, s, material, 0);
    }
}
