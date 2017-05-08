package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 08/05/2017.
 */
public class Clothing extends Wearable{

    public Clothing(String id, String name, String description, double weight, Size size, Material material, String part) {
        super(id, name, description, weight, size, material);
        this.part = part;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
