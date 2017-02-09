package com.al0ne.Items.Items;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Potion extends Item {
    private int healing;
    public Potion() {
        super("healthp", "Health Potion", "A potion for dire moments.", 0.2);
        addProperty("consumable");
        addProperty("healing");
        healing = 20;
    }
}
