package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Player;
import com.al0ne.Engine.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Material;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public class Armor extends Protective {
    public Armor(String id, String name, String description,
                 double weight, int armor, Material material) {
        super(id, name, description, weight, armor, Size.LARGE, material);
        this.part = "armor";
    }

    public Armor(Material m) {
        super(Material.stringify(m)+"armor", "Armor",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" armor.", 1+m.getValue(), m.getValue(), Size.LARGE, m);
        this.part = "armor";
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
