package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;

/**
 * Created by BMW on 07/05/2017.
 */
public class Axe extends Weapon {

    public Axe(Material m) {
        super(Material.stringify(m)+"axe", "Axe", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" axe.", "sharp", 1, Math.max(m.getDamage()-1, 1),
                Math.max(m.getWeight()-1.7, 1.1), Size.NORMAL, m);
    }

    public Axe(String name, String description, Material m) {
        super(Material.stringify(m)+name, name, description, "sharp",
                1, Math.max(m.getDamage()-1, 1),
                Math.max(m.getWeight()-1.7, 1.1), Size.NORMAL, m);
    }

    public Axe(String name, String description, int pen, int damage, double weight, Material m) {
        super(Material.stringify(m)+name, name, description, "sharp", pen, damage,
                weight, Size.NORMAL, m);
    }
}