package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Material;

/**
 * Created by BMW on 02/02/2017.
 */
public class Key extends Item{
    public Key(String id, String name, String description) {
        super(id, name, description, 0.1, Size.VSMALL, Material.IRON);
        addProperty("key");
    }

    public Key(String id, String name) {
        super(id, name, "A plain looking key.", 0.1, Size.VSMALL, Material.IRON);
        addProperty("key");
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }


}
