package com.al0ne.Items.Behaviours.Wearable;

import com.al0ne.Entities.Behaviours.Player;
import com.al0ne.Items.Behaviours.Protective;
import com.al0ne.Entities.Behaviours.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public class Shield extends Protective{
    public Shield(String id, String name, String description, String shortDescription, double weight, int armor) {
        super(id, name, description, shortDescription, weight, armor);
        this.part="off hand";
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }
}