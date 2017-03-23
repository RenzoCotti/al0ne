package com.al0ne.Items.ConcreteItems.Food;

import com.al0ne.Items.Behaviours.Drinkable;
import com.al0ne.Entities.Player;
import com.al0ne.Room;


public class Beer extends Drinkable {
    public Beer() {
        super("beer","Beer", "A fresh beer, in a brown bottle", "bottle of beer", 0.5);
        addProperty("consumable");
        addCommand("drink");
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        player.modifyHealth(+1);
        return true;
    }
}
