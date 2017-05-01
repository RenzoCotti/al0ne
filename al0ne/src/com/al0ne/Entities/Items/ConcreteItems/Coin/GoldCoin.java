package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 01/05/2017.
 */
public class GoldCoin extends Coin {
    public GoldCoin() {
        super("gcoin", "Gold Coin", "A shiny golden coin",
                "golden coin.", 0.02, Size.MICRO);
        this.value = 100;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}