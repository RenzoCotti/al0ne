package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Entities.Items.Behaviours.Material;

/**
 * Created by BMW on 09/03/2017.
 */
public class SilverCoin extends Coin{
    public SilverCoin() {
        super("scoin", "A very polished coin.",
                 0.01, Material.SILVER);
        this.value = 10;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
