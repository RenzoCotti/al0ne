package com.al0ne.Items.Items;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public class Key extends Item{
    public Key(String id, String name, String description) {
        super(id, name, description, 0.0);
        addProperty("key");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }

    public Key(String id, String name) {
        super(id, name, "A plain looking key.", 0.0);
        addProperty("key");
    }
}
