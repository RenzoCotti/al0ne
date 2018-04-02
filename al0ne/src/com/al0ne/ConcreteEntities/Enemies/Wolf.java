package com.al0ne.ConcreteEntities.Enemies;

import com.al0ne.AbstractEntities.Abstract.Enemy;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.Coin;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Food.Apple;

/**
 * Created by BMW on 13/03/2017.
 */
public class Wolf extends Enemy {
    public Wolf() {
        super("Wolf", "This wolf looks really ferocious and battle hardened.", "a fierce wolf",
                10, 40, 40, 1,2);
        addItemLoot(new Coin(), 20, 50);
        addItemLoot(new Apple(), 1, 100);
        addResistance("fists");
    }
}
