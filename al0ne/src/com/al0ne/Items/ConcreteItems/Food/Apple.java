package com.al0ne.Items.ConcreteItems.Food;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Entities.Behaviours.Player;
import com.al0ne.Entities.Behaviours.Room;

public class Apple extends Food {
    public Apple() {
        super("apple","Apple", "A fresh apple. It's green", "green apple", 0.1);
        addCommand("eat");
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        player.modifyHealth(+2);
        return true;
    }
}
