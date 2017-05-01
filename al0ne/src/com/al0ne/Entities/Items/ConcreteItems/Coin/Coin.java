package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Item;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 01/05/2017.
 */
public abstract class Coin extends Item{
    protected int value;
    public Coin(String id, String name, String description, String shortDescription, double weight, Size size) {
        super(id, name, description, shortDescription, weight, size);
    }
}