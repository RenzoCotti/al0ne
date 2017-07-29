package com.al0ne.Entities.Items.ConcreteItems.Armor;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Entities.Items.Types.Wearable.Armor;

/**
 * Created by BMW on 23/03/2017.
 */
public class LeatherArmour extends Armor{
    public LeatherArmour() {
        super("leatherarmour", "Leather Armor",
                "A roughly crafted leather armour. It's ok at what it does.",
                3.0, 2, 5, Material.LEATHER);
    }
}
