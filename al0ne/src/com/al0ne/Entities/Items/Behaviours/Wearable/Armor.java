package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Player;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public class Armor extends Protective {
    public Armor(String id, String name, String description, String shortDescription, double weight, int armor) {
        super(id, name, description, shortDescription, weight, armor, Size.LARGE);
        this.part = "armor";
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }
}
