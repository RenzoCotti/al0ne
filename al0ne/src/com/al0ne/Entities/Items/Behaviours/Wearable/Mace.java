package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;

/**
 * Created by BMW on 07/05/2017.
 */
public class Mace extends Weapon{
    public Mace(String id, String name, String description, String damageType,
                 int damage, double weight, Size size, Material material) {
        super(id, name, description, damageType, damage, weight, size, material);
    }

    public Mace(Material m) {
        super(Material.stringify(m)+"mace", "Mace", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" mace.", "blunt", Math.max(m.getDamage(), 2),
                Math.max(m.getWeight()-0.5, 2), Size.NORMAL, m);
    }
}

