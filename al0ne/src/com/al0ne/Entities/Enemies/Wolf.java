package com.al0ne.Entities.Enemies;

import com.al0ne.Entities.Enemy;
import com.al0ne.Items.ConcreteItems.Food.Apple;
import com.al0ne.Items.ConcreteItems.Coin;

/**
 * Created by BMW on 13/03/2017.
 */
public class Wolf extends Enemy {
    public Wolf() {
        super("wolf", "wolf", "a fierce wolf", "This wolf looks really ferocious and battle hardened.", 10, 1);
        addItemLoot(new Coin(), 20);
        addItemLoot(new Apple(), 1);
        addResistance("fists");
    }
}
