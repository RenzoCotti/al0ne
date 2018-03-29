package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;

import static java.lang.Math.max;

/**
 * Created by BMW on 07/05/2017.
 */
public class Sword extends Weapon {

    public Sword(Material m) {
        super(Material.stringify(m)+"sword", "Sword", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" sword.", "sharp", 0, Math.max(m.getDamage(), 1),
                Math.max(m.getWeight()-1.3, 1.3), Size.LARGE, m);
    }

    public Sword(String name, String description, int damage, int pen, double weight, Material m) {
        super(Material.stringify(m)+name, name, description, "sharp",
                pen, damage,
                weight, Size.LARGE, m);
    }

    public Sword(String name, String description, Material m) {
        super(Material.stringify(m)+name, name, description, "sharp",
                0, Math.max(m.getDamage(), 1),
                Math.max(m.getWeight()-1.3, 1.3), Size.LARGE, m);
    }
}
