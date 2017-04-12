package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Drinkable;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;


public class Beer extends Drinkable {
    public Beer() {
        super("beer","Beer", "A fresh beer, in a brown bottle", "bottle of beer", 0.5, Size.SMALL);
        addProperty("consumable");
        addCommand("drink");
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        player.modifyHealth(+1);
        return true;
    }
}
