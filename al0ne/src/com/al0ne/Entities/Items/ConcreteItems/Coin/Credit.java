package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;

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
