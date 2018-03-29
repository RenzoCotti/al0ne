package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.RangedWeapon;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.RangedWeapon;

/**
 * Created by BMW on 22/07/2017.
 */
public class LongBow extends RangedWeapon{
    public LongBow(String id, String name, String description, String damageType, int armorPen,
                   int damage, double weight, Size size, Material material, String ammoID) {
        super(id, name, description, damageType, armorPen, damage, weight, size, material, ammoID);
    }

    public LongBow() {
        //todo: add damage scaling also with arrows
        super("woodlongbow", "Longbow",
                "A fairly tall bow. It's pretty hard to pull its string.", "penetration",
                5, 3, 2, Size.LARGE, Material.WOOD, "arrow");
    }
}
