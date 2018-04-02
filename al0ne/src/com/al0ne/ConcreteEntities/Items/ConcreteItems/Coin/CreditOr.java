package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;

public class CreditOr extends MoneyContainer{
    public CreditOr() {
        super("credit-or", "Credit-Or", "A small chip which allows you to carry " +
                "any amount of money with you at all times", 0.1, Material.SILICA);
    }
}
