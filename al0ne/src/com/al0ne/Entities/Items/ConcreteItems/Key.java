package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Entities.Behaviours.Player;
import com.al0ne.Entities.Items.Item;
import com.al0ne.Entities.Behaviours.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public class Key extends Item{
    public Key(String id, String name, String description, String shortDescription) {
        super(id, name, description, shortDescription, 0.1);
        addProperty("key");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }

    public Key(String id, String name) {
        super(id, name, "A plain looking key.", "key", 0.1);
        addProperty("key");
    }
}
