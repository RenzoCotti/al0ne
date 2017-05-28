package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Behaviours.Drinkable;


public class Beer extends Drinkable {
    public Beer() {
        super("Beer", "A fresh beer, in a brown bottle", 0.5, Size.SMALL);
        setShortDescription("a bottle of beer");
    }
}
