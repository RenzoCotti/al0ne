package com.al0ne.Entities.Items.ConcreteItems.Weapon;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Behaviours.Wearable.RangedWeapon;

/**
 * Created by BMW on 22/07/2017.
 */
public class Pistol extends RangedWeapon{
    public Pistol(String id, String name, String description, int armorPen,
                  int damage, double weight, Size size, Material material, String ammoID, int magazineSize) {
        super(id, name, description, "penetration", armorPen,
                damage, weight, size, material, ammoID, magazineSize);
    }

    //todo: make ammunition determine armorpen & damage
    public Pistol(int damage, String ammoID, int magazineSize) {
        super(ammoID+"pistol", "Pistol ("+ammoID+")", "A handgun chambered in "+ammoID+".",
                "penetration", 6, damage, 0.6, Size.SMALL, Material.IRON, ammoID, magazineSize);
    }
}
