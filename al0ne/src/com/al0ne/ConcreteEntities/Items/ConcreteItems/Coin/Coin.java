package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;

/**
 * Created by BMW on 01/05/2017.
 */
public class Coin extends Currency {
    public Coin() {
        super("coin", "Brass coin", "A fairly opaque coin.",
                0.01, Size.VSMALL, Material.BRASS);
        this.value = 1;
    }

}