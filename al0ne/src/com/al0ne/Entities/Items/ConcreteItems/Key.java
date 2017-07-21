package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Engine.Physics.Behaviours.KeyBehaviour;

/**
 * Created by BMW on 02/02/2017.
 */
public class Key extends Item{
    public Key(String name, String description, String door) {
        super(name, name, description, 0.1, Size.VSMALL, Material.IRON, null);
        addProperty(new KeyBehaviour(door));
    }

    public Key(String name, String door) {
        super(name, name, "A plain looking key.", 0.1, Size.VSMALL, Material.IRON, null);
        addProperty(new KeyBehaviour(door));
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }


}
