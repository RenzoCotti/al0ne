package com.al0ne.Items.Items;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Apple extends Food {
    public Apple() {
        super("apple","Apple", "A fresh apple", 0.1);
        addProperty("food");
        value=1;
//        addProperty("usable");
    }
}
