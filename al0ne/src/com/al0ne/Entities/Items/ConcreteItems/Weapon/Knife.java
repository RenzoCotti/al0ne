package com.al0ne.Entities.Items.ConcreteItems.Weapon;

import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Material;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;

/**
 * Created by BMW on 02/02/2017.
 */
public class Knife extends Weapon {
    public Knife() {
        super("knife", "Knife", "A rusty but sharp knife",
                "a small knife", "sharp", 2, 0.2, Size.SMALL, Material.IRON);
        addProperty("sharp");
    }
}
