package com.al0ne.ConcreteEntities.Items.ConcreteItems.Shield;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Shield;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class RoundShield extends Shield{
    public RoundShield(String id, String name, String description,
                       double weight, int armor, int encumberment, Material material) {
        super(id, name, description, weight, armor, encumberment, Size.NORMAL, material);
    }

    public RoundShield(Material m) {
        super(Material.stringify(m)+"roundshield", "Round Shield",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" round shield.", Math.max(m.getWeight()-1, 1),
                max(m.getToughness()-1, 1), 10+(int)(m.getWeight()*5), Size.NORMAL, m);
    }
}
