package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;

/**
 * Created by BMW on 01/05/2017.
 */
public class GoldCoin extends Coin {
    public GoldCoin() {
        super("gcoin", "Gold coin","A shiny coin.",
                0.02, Material.GOLD);
        this.value = 100;
    }

}
