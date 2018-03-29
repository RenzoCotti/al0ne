package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;

/**
 * Created by BMW on 07/05/2017.
 */
public class Knife extends Weapon {

    public Knife(Material m) {
        super(Material.stringify(m)+"dagger", "Dagger", Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" dagger.", "sharp", 0, Math.max(m.getDamage()-3, 1),
                Math.max(m.getWeight()-2.4, 0.5), Size.VSMALL, m);
    }

    public Knife(String name, String description, Material m) {
        super(Material.stringify(m)+name, name, description, "sharp",
                0, Math.max(m.getDamage()-3, 1),
                Math.max(m.getWeight()-2.4, 0.5), Size.VSMALL, m);
    }

    public Knife(String name, String description, int pen, int damage, double weight, Material m) {
        super(Material.stringify(m)+name, name, description, "sharp", pen, damage,
                weight, Size.VSMALL, m);
    }
}