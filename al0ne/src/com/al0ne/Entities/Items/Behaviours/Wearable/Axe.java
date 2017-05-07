package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;

/**
 * Created by BMW on 07/05/2017.
 */
public class Axe extends Weapon{
    public Axe(String id, String name, String description, String damageType,
                int damage, double weight, Size size, Material material) {
        super(id, name, description, damageType, damage, weight, size, material);
    }

    public Axe(Material m) {
        super(Material.stringify(m)+"axe", "Axe", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" axe.", "sharp", Math.max(m.getDamage(), 1),
                Math.max(m.getWeight()-2, 1), Size.NORMAL, m);
    }
}