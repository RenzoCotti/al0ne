package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;

/**
 * Created by BMW on 03/05/2017.
 */
public class Credit extends Currency{
    public Credit() {
        super("credit", "Credit","A chip containing some credits.",
                0.00, Size.UNDEFINED, Material.PLASTIC);
        this.value = 1;
    }
}
