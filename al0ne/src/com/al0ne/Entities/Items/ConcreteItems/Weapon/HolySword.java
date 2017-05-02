package com.al0ne.Entities.Items.ConcreteItems.Weapon;

import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Material;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;

/**
 * Created by BMW on 14/03/2017.
 */
public class HolySword extends Weapon{
    public HolySword() {
        super("holysword", "Holy sword", "A finely crafted silver sword.",
                "holy sword", "sharp", 5, 1.3, Size.LARGE, Material.STEEL);
    }
}
