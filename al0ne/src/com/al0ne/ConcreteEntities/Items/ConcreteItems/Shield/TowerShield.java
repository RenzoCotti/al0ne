package com.al0ne.ConcreteEntities.Items.ConcreteItems.Shield;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Shield;

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
