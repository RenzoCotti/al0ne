package com.al0ne.ConcreteEntities.Items.ConcreteItems.Food;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Drinkable;


public class Beer extends Drinkable {
    public Beer() {
        super("Beer", "A fresh beer, in a brown bottle", 0.5, Size.SMALL);
        setShortDescription("a bottle of beer");
    }
}
