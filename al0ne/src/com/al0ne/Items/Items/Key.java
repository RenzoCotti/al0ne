package com.al0ne.Items.Items;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Key extends Item{
    public Key(String name, String description) {
        super(name, description, 0.0);
        addProperty("key");
    }

    public Key(String name) {
        super(name, "A plain looking key.", 0.0);
        addProperty("key");
    }
}
