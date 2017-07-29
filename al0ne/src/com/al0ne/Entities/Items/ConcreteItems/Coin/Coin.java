package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Enums.Material;

/**
 * Created by BMW on 01/05/2017.
 */
public abstract class Coin extends Item{
    protected int value;
    public Coin(String id, String name, String description, double weight, Material material) {
        super(id, name, description, weight, Size.MICRO, material, 0);
    }
}
