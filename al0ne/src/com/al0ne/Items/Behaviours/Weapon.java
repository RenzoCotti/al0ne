package com.al0ne.Items.Behaviours;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Weapon extends Item{
    protected int damage;
    protected String type;
    public Weapon(String id, String name, String description, String type, double weight) {
        super(id, name, description, weight);
        this.type=type;
    }

    public int getDamage() {
        return damage;
    }

    public String getType(){
        return type;
    }
}
