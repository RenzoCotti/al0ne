package com.al0ne.Behaviours;

import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Material;

/**
 * Created by BMW on 30/04/2017.
 */
public class JunkItem extends Item{
    public JunkItem(String name, String description, String shortDescription, double weight, Size size) {
        super(name, name, description, shortDescription, weight, size, Material.UNDEFINED);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
