package com.al0ne.Items.Items;

import com.al0ne.Items.Item;
import com.al0ne.Entities.Player;
import com.al0ne.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public class Potion extends Item {
    public Potion() {
        super("healthp", "Health Potion", "A potion for dire moments.", 0.2);
        addProperty("consumable");
        addProperty("healing");
        addCommand("drink");
    }

    @Override
    public void used(Room currentRoom, Player player){
        player.modifyHealth(+20);
    }
}
