package com.al0ne.ConcreteEntities.Items.ConcreteItems.Food;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Food;

public class Apple extends Food {
    public Apple() {
        super("Apple", "A fresh apple. It's green", 0.1, Size.VSMALL, 4);
    }
}
