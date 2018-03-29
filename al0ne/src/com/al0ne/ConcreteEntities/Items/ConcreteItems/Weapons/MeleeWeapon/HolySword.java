package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;

/**
 * Created by BMW on 14/03/2017.
 */
public class HolySword extends Weapon{
    public HolySword() {
        super("holysword", "Holy sword", "A finely crafted silver sword.",
                "sharp", 2, 5, 1.3, Size.LARGE, Material.STEEL);
    }
}
