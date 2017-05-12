package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Protective;

/**
 * Created by BMW on 23/03/2017.
 */
public class ChainMail extends Armor {
    public ChainMail(String id, String name, String description,
                     double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 10, Size.LARGE, material);
    }

    public ChainMail(String id, String name, String description,
                     double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
    }

    public ChainMail(Material m) {
        super(Material.stringify(m)+"chainmail", "Chainmail",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" chainmail.", m.getToughness()+m.getWeight(),
                m.getToughness(), (int)m.getWeight()*5, Size.LARGE, m);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
