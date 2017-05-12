package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Protective;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class PlateArmor extends Armor {
    public PlateArmor(String id, String name, String description,
                      double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 15,  Size.LARGE, material);
    }

    public PlateArmor(String id, String name, String description,
                      double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
    }

    public PlateArmor(Material m) {
        super(Material.stringify(m)+"platearmor", "Plate Armor",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" plate armor.", m.getToughness()+m.getWeight()*2,
                m.getToughness()+1, (int)m.getWeight()*15, Size.LARGE, m);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
