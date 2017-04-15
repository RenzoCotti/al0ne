package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 02/02/2017.
 */
public class Key extends Item{
    public Key(String id, String name, String description, String shortDescription) {
        super(id, name, description, shortDescription, 0.1, Size.VSMALL);
        addProperty("key");
    }

    public Key(String id, String name) {
        super(id, name, "A plain looking key.", "key", 0.1, Size.VSMALL);
        addProperty("key");
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }


}
