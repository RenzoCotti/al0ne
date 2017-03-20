package com.al0ne.Items.Props;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

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
    public boolean usedWith(Item item, Room currentRoom) {
        return false;
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        currentRoom.unlockDirection(ID);

        active=true;
        return true;
    }
}
