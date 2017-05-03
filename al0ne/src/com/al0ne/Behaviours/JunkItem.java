package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Size;

/**
 * Created by BMW on 30/04/2017.
 */
public class JunkItem extends Item{
    public JunkItem(String name, String description, double weight, Size size) {
        super(name, name, description, weight, size);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
