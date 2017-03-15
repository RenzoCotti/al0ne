package com.al0ne.Items.Behaviours;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Weapon extends Item{
    protected int damage;
    protected String type;
    public Weapon(String id, String name, String description, String type, int damage, double weight) {
        super(id, name, description, weight);
        this.type=type;
        this.damage=damage;
    }

    public int getDamage() {
        return damage;
    }

    public String getType(){
        return type;
    }

    public void setType(String s){
        type=s;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
