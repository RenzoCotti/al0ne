package com.al0ne.Entities.Items.ConcreteItems.Weapons.MeleeWeapon;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;

/**
 * Created by BMW on 07/05/2017.
 */
public class Spear extends Weapon {
    public Spear(String name, String description, String damageType, int armorPen,
                 int damage, double weight, Material material) {
        super(name, name, description, damageType, armorPen, damage, weight, Size.NORMAL, material);
    }

    public Spear(Material m) {
        super(Material.stringify(m)+"spear", "Spear", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" spear.", "pierce", Math.max(m.getDamage()-2, 1), Math.max(m.getDamage()-2, 1),
                Math.max(m.getWeight()-1.2, 1.4), Size.NORMAL, m);
    }
}
