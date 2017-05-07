package com.al0ne.Entities.Items.Behaviours.Weapons;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;

/**
 * Created by BMW on 07/05/2017.
 */
public class Mace extends Weapon{
    public Mace(String id, String name, String description, String damageType, int armorPen,
                 int damage, double weight, Size size, Material material) {
        super(id, name, description, damageType, armorPen, damage, weight, size, material);
    }

    public Mace(Material m) {
        super(Material.stringify(m)+"mace", "Mace", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" mace.", "blunt", 2, Math.max(m.getDamage()-2, 1),
                Math.max(m.getWeight()-0.8, 2), Size.NORMAL, m);
    }
}

