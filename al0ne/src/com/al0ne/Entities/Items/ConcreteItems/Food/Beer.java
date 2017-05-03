package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Engine.Enums.Size;
import com.al0ne.Entities.Items.Behaviours.Drinkable;


public class Beer extends Drinkable {
    public Beer() {
        super("beer","Beer", "A fresh beer, in a brown bottle", 0.5, Size.SMALL);
    }
}
