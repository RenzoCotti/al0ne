package com.al0ne.Items.Items;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Player;
import com.al0ne.Room;

public class Apple extends Food {
    public Apple() {
        super("apple","Apple", "A fresh apple", 0.1);
        addProperty("food");
        value=1;
        addCommand("eat");
    }

    @Override
    public void used(Room currentRoom, Player player){
        player.modifyHealth(-10);
    }
}
