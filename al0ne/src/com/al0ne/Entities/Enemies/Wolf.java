package com.al0ne.Entities.Enemies;

import com.al0ne.Entities.Enemy;
import com.al0ne.Items.Items.Apple;
import com.al0ne.Items.Items.Coin;

/**
 * Created by BMW on 13/03/2017.
 */
public class Wolf extends Enemy {
    public Wolf() {
        super("wolf", "Wolf", "A fierce wolf", 10, 1);
        addItemLoot(new Coin(), 20);
        addItemLoot(new Apple(), 1);
        addResistance("fists");
    }
}
