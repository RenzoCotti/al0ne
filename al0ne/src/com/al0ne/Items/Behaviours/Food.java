package com.al0ne.Items.Behaviours;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Room;

/**
 * Created by BMW on 11/02/2017.
 */
public abstract class Food extends Item {
    public Food(String id, String name, String description, String shortDescription, double weight) {
        super(id, name, description, shortDescription, weight);
    }

//    @Override
//    public boolean used(Room currentRoom, Player player) {
//        return true;
//    }
}
