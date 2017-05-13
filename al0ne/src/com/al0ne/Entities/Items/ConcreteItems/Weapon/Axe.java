package com.al0ne.Entities.Items.ConcreteItems.Weapon;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;

/**
 * Created by BMW on 07/05/2017.
 */
public class Axe extends Weapon {
    public Axe(String id, String name, String description, String damageType, int armorPen,
                int damage, double weight, Size size, Material material) {
        super(id, name, description, damageType, armorPen, damage, weight, size, material);
    }

    public Axe(Material m) {
        super(Material.stringify(m)+"axe", "Axe", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" axe.", "sharp", 1, Math.max(m.getDamage()-1, 1),
                Math.max(m.getWeight()-1.7, 1.1), Size.NORMAL, m);
    }
}