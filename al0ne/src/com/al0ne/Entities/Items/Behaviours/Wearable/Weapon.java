package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Entities.Items.Behaviours.Wearable.Wearable;

/**
 * Created by BMW on 02/02/2017.
 */
public class  Weapon extends Wearable {
    protected int damage;
    public int armorPenetration;
    protected String damageType;
    public Weapon(String id, String name, String description, String damageType, int armorPen,
                  int damage, double weight, Size size, Material material) {
        super(id, name, description, weight, size, material);
        this.damageType=damageType;
        this.armorPenetration=armorPen;
        this.damage=damage;
        this.part = "main hand";

    }


    public int getDamage() {
        return damage;
    }

    public String getDamageType(){
        return damageType;
    }

    public int getArmorPenetration() {
        return armorPenetration;
    }

    public void setType(String s){
        damageType=s;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
