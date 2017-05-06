package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Enums.Size;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Potion extends Item {
    public Potion(String id, String name, String longDescription) {
        super(id, name, longDescription, 0.3, Size.SMALL, Material.GLASS);
        addProperty("consumable");
        addCommand(Command.DRINK);
    }

}
