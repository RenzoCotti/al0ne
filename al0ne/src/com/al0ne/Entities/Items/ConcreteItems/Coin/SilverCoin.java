package com.al0ne.Entities.Items.ConcreteItems.Coin;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 09/03/2017.
 */
public class SilverCoin extends Coin{
    public SilverCoin() {
        super("scoin", "Silver Coin", "A shiny silver coin.",
                "silver coin.", 0.01, Size.MICRO);
        this.value = 10;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
