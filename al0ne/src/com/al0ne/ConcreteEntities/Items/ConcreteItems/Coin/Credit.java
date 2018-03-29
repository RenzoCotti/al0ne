package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;

/**
 * Created by BMW on 03/05/2017.
 */
public class Credit extends Coin{
    public Credit() {
        super("credit", "Credit","A chip containing some credits.",
                0.00, Material.PLASTIC);
        this.value = 1;
    }
}
