package com.al0ne.Entities.Items.ConcreteItems.Weapon;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;

import static java.lang.Math.max;

/**
 * Created by BMW on 07/05/2017.
 */
public class Sword extends Weapon {
    public Sword(String id, String name, String description, String damageType,
                 int damage, int armorPen, double weight, Size size, Material material) {
        super(id, name, description, damageType, armorPen, damage, weight, size, material);
    }

    public Sword(Material m) {
        super(Material.stringify(m)+"sword", "Sword", Utility.getArticle(Material.stringify(m))+" "
                +Material.stringify(m)+" sword.", "sharp", 0, Math.max(m.getDamage(), 1),
                Math.max(m.getWeight()-1.3, 1.3), Size.LARGE, m);
    }
}
