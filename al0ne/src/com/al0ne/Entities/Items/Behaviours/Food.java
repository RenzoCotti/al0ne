package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 11/02/2017.
 */
public class Food extends Item {
    public Food(String id, String name, String description, String shortDescription, double weight, Size size) {
        super(id, name, description, shortDescription, weight, size);
        addCommand("eat");
        addProperty("food");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return true;
    }
}
