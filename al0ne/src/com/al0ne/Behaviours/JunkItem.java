package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;

/**
 * Created by BMW on 30/04/2017.
 */
public class JunkItem extends Item{
    public JunkItem(String name, String description, double weight, Size size) {
        super(name, name, description, weight, size);
    }

    public JunkItem(String name, String description, double weight, Size size, Material material) {
        super(name, name, description, weight, size, material);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
