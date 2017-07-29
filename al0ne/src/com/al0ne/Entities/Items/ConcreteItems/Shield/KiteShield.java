package com.al0ne.Entities.Items.ConcreteItems.Shield;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Types.Wearable.Shield;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class KiteShield extends Shield{
    public KiteShield(String id, String name, String description,
                      double weight, int armor, int encumberment, Material material) {
        super(id, name, description, weight, armor, encumberment, Size.SMALL, material);
    }

    public KiteShield(Material m) {
        super(Material.stringify(m)+"kiteshield", "Kite Shield",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" shield, shaped like an elongated tear.", Math.max(m.getWeight(), 2),
                max(m.getToughness(), 1), 15+(int)(m.getWeight()*5), Size.LARGE, m);
    }
}
