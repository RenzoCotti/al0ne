package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 03/05/2017.
 */
public class Credit extends Coin{
    public Credit() {
        super("credit", "Credit","A chip containing some credits.",
                0.00, Material.PLASTIC);
        this.value = 1;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
