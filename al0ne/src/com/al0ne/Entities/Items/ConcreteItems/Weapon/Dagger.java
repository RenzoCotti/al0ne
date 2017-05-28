package com.al0ne.Entities.Items.ConcreteItems.Weapon;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;

/**
 * Created by BMW on 07/05/2017.
 */
public class Dagger extends Weapon {
    public Dagger(String name, String description, String damageType, int armorPen,
                 int damage, double weight, Size size, Material material) {
        super(name, name, description, damageType, armorPen, damage, weight, size, material);
    }

    public Dagger(Material m) {
        super(Material.stringify(m)+"dagger", "Dagger", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" dagger.", "sharp", 0, Math.max(m.getDamage()-3, 1),
                Math.max(m.getWeight()-2.4, 0.5), Size.VSMALL, m);
    }
}