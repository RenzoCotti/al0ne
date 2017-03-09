package com.al0ne.Items.Items;

import com.al0ne.Items.Behaviours.Food;

/**
 * Created by BMW on 18/02/2017.
 */
public class Beer extends Food {
    public Beer() {
        super("beer","Beer", "A fresh beer", 0.5);
        addProperty("food");
        addProperty("drink");
        value=1;
//        addProperty("usable");
    }
}
