package com.al0ne.Items.ConcreteItems.Weapon;

import com.al0ne.Items.Behaviours.Wearable.Weapon;

/**
 * Created by BMW on 02/02/2017.
 */
public class Knife extends Weapon {
    public Knife() {
        super("knife", "Knife", "A rusty but sharp knife", "a small knife", "sharp", 2, 0.2);
        addProperty("sharp");
    }
}
