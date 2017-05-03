package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Behaviours.Room;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Helmet extends Protective {
    public Helmet(String id, String name, String description,
                  double weight, int armor, Material material) {
        super(id, name, description, weight, armor, Size.SMALL, material);
        this.part="head";
    }

    public Helmet(Material m) {
        super(Material.stringify(m)+"helmet", "Helmet",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" helmet.", Math.max(m.getWeight()-2, 0.5), max(m.getToughness()-2, 1), Size.SMALL, m);
        this.part = "head";
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
