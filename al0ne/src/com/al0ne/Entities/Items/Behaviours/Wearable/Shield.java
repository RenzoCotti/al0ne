package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Player;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Material;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public class Shield extends Protective{
    public Shield(String id, String name, String description, String shortDescription,
                  double weight, int armor, Material material) {
        super(id, name, description, shortDescription, weight, armor, Size.NORMAL, material);
        this.part="off hand";
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
