package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 01/05/2017.
 */
public class BrassCoin extends Coin {
    public BrassCoin() {
        super("bcoin", "Brass Coin", "A fairly opaque brass coin",
                "brass coin.", 0.01, Size.MICRO);
        this.value = 1;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}