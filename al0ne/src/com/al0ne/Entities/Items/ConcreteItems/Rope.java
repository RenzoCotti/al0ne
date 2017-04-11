package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Entities.Behaviours.Player;
import com.al0ne.Entities.Items.Item;
import com.al0ne.Entities.Behaviours.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public class Rope extends Item{
    public Rope() {
        super("rope", "Rope", "11m of sturdy rope.", "rope", 1.0);
        addProperty("cuttable");
        addCommand("climb");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }
}
