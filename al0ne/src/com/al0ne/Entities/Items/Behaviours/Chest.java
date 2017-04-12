package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.ConcreteItems.Coin;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.HolySword;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.Knife;

/**
 * Created by BMW on 12/04/2017.
 */
public class Chest extends Container{
    public Chest() {
        super("chest", "wooden chest", "A fairly large wooden chest", "a wooden chest", 10, Size.VLARGE, true);

        this.addItem(new Coin(), 100);
        this.addItem(new Knife(), 20);
    }


}
