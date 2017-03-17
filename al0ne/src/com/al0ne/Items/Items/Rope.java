package com.al0ne.Items.Items;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public class Rope extends Item{
    public Rope() {
        super("rope", "Rope", "11m of sturdy rope.", 1.0);
        addProperty("cuttable");
        addCommand("climb");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }
}
