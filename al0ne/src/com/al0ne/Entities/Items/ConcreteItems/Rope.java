package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Enums.Material;

/**
 * Created by BMW on 02/02/2017.
 */
public class Rope extends Item{
    public Rope() {
        super("rope", "Rope", "11m of sturdy rope.", 1.0, Size.NORMAL, Material.FIBRE, null);
//        addProperty("cuttable");
//        addProperty("climb");
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
