package com.al0ne.Items.Items;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Potion extends Item {
    public Potion() {
        super("health potion", "A potion for dire moments.", 0.2);
        addProperty("consumable", 20);
    }
}
