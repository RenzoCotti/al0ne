package com.al0ne.Items.Items;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Entities.Player;
import com.al0ne.Room;

public class Mushroom extends Food {
    public Mushroom() {
        super("mushroom","Brown mushroom", "It has a very pungent smell", 0.2);
        addProperty("food");
        value=1;
        addCommand("eat");
    }

    @Override
    public void used(Room currentRoom, Player player){
        System.out.println("The mushroom is poisonous!");
        player.modifyHealth(-5);
    }
}
