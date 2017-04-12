package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 02/02/2017.
 */
public class Weapon extends Wearable {
    protected int damage;
    protected String damageType;
    public Weapon(String id, String name, String description, String shortDescription, String damageType, int damage, double weight, Size size) {
        super(id, name, description, shortDescription, weight, size);
        this.damageType=damageType;
        this.damage=damage;
        this.part = "main hand";
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
