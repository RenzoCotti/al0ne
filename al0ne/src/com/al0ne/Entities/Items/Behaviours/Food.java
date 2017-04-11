package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Items.Item;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 11/02/2017.
 */
public class Food extends Item {
    public Food(String id, String name, String description, String shortDescription, double weight) {
        super(id, name, description, shortDescription, weight);
        addCommand("eat");
        addProperty("food");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return true;
    }
}
