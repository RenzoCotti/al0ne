package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Food;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;

public class Apple extends Food {
    public Apple() {
        super("apple","Apple", "A fresh apple. It's green", 0.1, Size.VSMALL, 4);
    }
}
