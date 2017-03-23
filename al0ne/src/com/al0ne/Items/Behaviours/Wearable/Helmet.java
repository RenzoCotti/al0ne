package com.al0ne.Items.Behaviours.Wearable;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Behaviours.Protective;
import com.al0ne.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public class Helmet extends Protective {
    public Helmet(String id, String name, String description, String shortDescription, double weight, int armor) {
        super(id, name, description, shortDescription, weight, armor);
        this.part="head";
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }
}
