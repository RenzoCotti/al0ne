package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;

/**
 * Created by BMW on 09/03/2017.
 */
public class SilverCoin extends Coin{
    public SilverCoin() {
        super("scoin", "Silver coin","A very polished coin.",
                 0.01, Material.SILVER);
        this.value = 10;
    }

}
