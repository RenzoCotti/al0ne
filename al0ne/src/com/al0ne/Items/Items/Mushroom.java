package com.al0ne.Items.Items;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Entities.Player;
import com.al0ne.Room;

public class Mushroom extends Food {
    public Mushroom() {
        super("mushroom","Brown mushroom", "It has a very pungent smell", "brown mushroom", 0.2);
        addProperty("food");
        addCommand("eat");
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        System.out.println("The mushroom is poisonous!");
        player.modifyHealth(-5);
        return true;
    }
}
