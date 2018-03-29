package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;

/**
 * Created by BMW on 07/05/2017.
 */
public class Spear extends Weapon {

    public Spear(Material m) {
        super(Material.stringify(m)+"spear", "Spear", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" spear.", "pierce", Math.max(m.getDamage()-2, 1), Math.max(m.getDamage()-2, 1),
                Math.max(m.getWeight()-1.2, 1.4), Size.NORMAL, m);
    }

    public Spear(String name, String description, int damage, int pen, double weight, Material m) {
        super(Material.stringify(m)+name, name, description, "pierce", pen, damage,
                weight, Size.NORMAL, m);
    }

    public Spear(String name, String description, Material m) {
        super(Material.stringify(m)+name, name, description, "pierce",
                Math.max(m.getDamage()-2, 1), Math.max(m.getDamage()-2, 1),
                Math.max(m.getWeight()-1.2, 1.4), Size.NORMAL, m);
    }
}

