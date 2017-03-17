package com.al0ne.Items.Behaviours;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Weapon extends Item{
    protected int damage;
    protected String damageType;
    public Weapon(String id, String name, String description, String damageType, int damage, double weight) {
        super(id, name, description, weight);
        this.damageType=damageType;
        this.damage=damage;
    }

    public int getDamage() {
        return damage;
    }

    public String getDamageType(){
        return damageType;
    }

    public void setType(String s){
        damageType=s;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }
}
