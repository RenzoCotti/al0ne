package com.al0ne.Entities.Items.Behaviours.Weapons;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;

/**
 * Created by BMW on 07/05/2017.
 */
public class Mace extends Weapon{
    public Mace(String id, String name, String description, int armorPen,
                 int damage, double weight, Material material) {
        super(id, name, description, "blunt", armorPen, damage, weight, Size.NORMAL, material);
    }

    public Mace(Material m) {
        super(Material.stringify(m)+"mace", "Mace", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" mace.", "blunt", Math.max(m.getDamage()-1, 1), Math.max(m.getDamage()-3, 1),
                Math.max(m.getWeight()-0.8, 2), Size.NORMAL, m);
    }
}

