package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Player;
import com.al0ne.Engine.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Material;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Behaviours.Room;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Shield extends Protective{
    public Shield(String id, String name, String description,
                  double weight, int armor, Material material) {
        super(id, name, description, weight, armor, Size.NORMAL, material);
        this.part="off hand";
    }

    public Shield(Material m) {
        super(Material.stringify(m)+"shield", "Shield",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" shield.", 1+m.getValue(), max(m.getValue()-1, 0), Size.NORMAL, m);
        this.part = "off hand";
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
