package com.al0ne.Entities.Enemies;

import com.al0ne.Behaviours.Enemy;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Entities.Items.ConcreteItems.Food.Apple;

/**
 * Created by BMW on 13/03/2017.
 */
public class Wolf extends Enemy {
    public Wolf() {
        super("wolf", "Wolf", "This wolf looks really ferocious and battle hardened.", "a fierce wolf");
        addItemLoot(new SilverCoin(), 20, 50);
        addItemLoot(new Apple(), 1, 100);
        addResistance("fists");
        setStats(10, 2, 40, 1, 40);
    }
}
