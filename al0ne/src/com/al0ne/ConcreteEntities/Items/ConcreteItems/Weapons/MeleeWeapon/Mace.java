package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;

/**
 * Created by BMW on 07/05/2017.
 */
public class Mace extends Weapon {

    public Mace(Material m) {
        super(Material.stringify(m)+"mace", "Mace", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" mace.", "blunt", Math.max(m.getDamage()-1, 1), Math.max(m.getDamage()-3, 1),
                Math.max(m.getWeight()-0.8, 2), Size.NORMAL, m);
    }

    public Mace(String name, String description, Material m) {
        super(Material.stringify(m)+name, name, description, "blunt", Math.max(m.getDamage()-1, 1),
                Math.max(m.getDamage()-3, 1), Math.max(m.getWeight()-0.8, 2), Size.NORMAL, m);
    }

    public Mace(String name, String description, int pen, int damage, double weight, Material m) {
        super(Material.stringify(m)+name, name, description, "blunt", pen, damage,
                weight, Size.NORMAL, m);
    }
}

