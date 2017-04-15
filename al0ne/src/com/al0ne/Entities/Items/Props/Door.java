package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

public class Door extends Prop {

    public Door(String id, String name) {
        super(id, name, "a sturdy door", "a sturdy door", "The door is now open.");
        addCommand("open");
    }

    public Door(String id, String name, String description, String after) {
        super(id, name, description, after);
        addCommand("open");
    }

    @Override
    public boolean usedWith(Item item, Room currentRoom, Player player) {
        return false;
    }

    @Override
    public int used(Room currentRoom, Player player){
        currentRoom.unlockDirection(ID);

        active=true;
        return 1;
    }
}
