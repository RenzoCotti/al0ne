package com.al0ne.Items.Items;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Knife extends Item{
    public Knife() {
        super("knife", "A rusty but sharp knife", 0.2);
        addProperty("sharp", 1);
    }
}
