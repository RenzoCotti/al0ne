package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;

public class Wallet extends MoneyContainer{
    public Wallet() {
        super("wallet", "Leather wallet", "A wallet you can use to keep together" +
                "all of your money", 0.1, Material.LEATHER);
    }
}
