package com.al0ne.Items.ConcreteItems.Food;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Entities.Behaviours.Player;
import com.al0ne.Entities.Behaviours.Room;

import static com.al0ne.Engine.Main.printToLog;

public class Mushroom extends Food {
    public Mushroom() {
        super("mushroom","Brown mushroom", "It has a very pungent smell", "brown mushroom", 0.2);
        addCommand("eat");
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        printToLog("The mushroom is poisonous!");
        player.modifyHealth(-5);
        return true;
    }
}
