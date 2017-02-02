package com.al0ne.Items.Items;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Apple extends Item {
    public Apple() {
        super("apple", "A fresh apple", 0.1);
        addProperty("consumable");
    }
}
