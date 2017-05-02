package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Material;

/**
 * Created by BMW on 01/05/2017.
 */
public class GoldCoin extends Coin {
    public GoldCoin() {
        super("gcoin", "A shiny coin.",
                0.02, Material.GOLD);
        this.value = 100;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
