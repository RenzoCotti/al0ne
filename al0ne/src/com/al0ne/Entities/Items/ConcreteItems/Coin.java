package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 09/03/2017.
 */
public class Coin extends Item{
    public Coin() {
        super("coin", "Coin", "A shiny golden coin", "golden coin.", 0.01, Size.MICRO);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
