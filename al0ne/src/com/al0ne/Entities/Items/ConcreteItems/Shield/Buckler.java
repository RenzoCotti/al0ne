package com.al0ne.Entities.Items.ConcreteItems.Shield;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Types.Wearable.Shield;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Buckler extends Shield{
    public Buckler(String id, String name, String description,
                   double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 0, Size.SMALL, material);
    }

    public Buckler(Material m) {
        super(Material.stringify(m)+"buckler", "Buckler",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" bulky round shield, fairly light.", Math.max(m.getWeight()-2, 1),
                max(m.getToughness()-2, 1), (int)(m.getWeight()*2), Size.SMALL, m);
        this.part = "off hand";
    }
}
