package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Items.Item;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 09/03/2017.
 */
public class Coin extends Item{
    public Coin() {
        super("coin", "Coin", "A shiny golden coin", "golden coin.", 0.01);
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }
}
