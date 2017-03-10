package com.al0ne.Items.Props;

import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public class Door extends Prop {

    public Door() {
        super("door", "Door", "A sturdy wooden door");
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
    public boolean used(Room currentRoom){
        currentRoom.unlockDirection(id);

        active=true;
        return true;
    }
}
