package com.al0ne.Entities.Items.ConcreteItems.Shield;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Wearable.Shield;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class TowerShield extends Shield{
    public TowerShield(String id, String name, String description,
                       double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 20, Size.VLARGE, material);
    }

    public TowerShield(Material m) {
        super(Material.stringify(m)+"towershield", "Tower Shield",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" rectangular shield.", Math.max(m.getWeight()*2, 4),
                max(m.getToughness(), 2), 20+(int)(m.getWeight()*5), Size.VLARGE, m);
    }
}
