package com.al0ne.Items.Behaviours;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Weapon extends Item{
    protected int damage;
    public Weapon(String name, String description, double weight) {
        super(name, description, weight);
    }

    public int getDamage() {
        return damage;
    }
}
