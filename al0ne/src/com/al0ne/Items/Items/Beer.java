package com.al0ne.Items.Items;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Entities.Player;
import com.al0ne.Room;


public class Beer extends Food {
    public Beer() {
        super("beer","Beer", "A fresh beer, in a brown bottle", "bottle of beer", 0.5);
        addProperty("consumable");
        addCommand("drink");
//        addProperty("usable");
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        player.modifyHealth(+1);
        return true;
    }
}
