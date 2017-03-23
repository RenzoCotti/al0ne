package com.al0ne.Items.Behaviours;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public class Drinkable extends Item{
    public Drinkable(String id, String name, String description, String shortDescription, double weight) {
        super(id, name, description, shortDescription, weight);
        addCommand("drink");
        addProperty("food");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return true;
    }
}
