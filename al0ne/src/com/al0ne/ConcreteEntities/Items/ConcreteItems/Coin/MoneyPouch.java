package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;

public class MoneyPouch extends MoneyContainer{
    public MoneyPouch() {
        super("moneypouch", "Money pouch", "A leather money pouch.",
                0.1, Material.LEATHER);
    }
}
