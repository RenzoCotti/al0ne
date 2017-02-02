package com.al0ne.Items.Items;

import com.al0ne.Items.Behaviours.Weapon;
import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Knife extends Weapon {
    public Knife() {
        super("knife", "A rusty but sharp knife", 0.2);
        addProperty("sharp");
        damage=1;
    }
}
