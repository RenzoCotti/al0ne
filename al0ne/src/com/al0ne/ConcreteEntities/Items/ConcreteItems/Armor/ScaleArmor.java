package com.al0ne.ConcreteEntities.Items.ConcreteItems.Armor;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Armor;

/**
 * Created by BMW on 23/03/2017.
 */
public class ScaleArmor extends Armor {
    public ScaleArmor(String id, String name, String description,
                      double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 15, Size.LARGE, material);
    }

    public ScaleArmor(String id, String name, String description,
                      double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
    }

    public ScaleArmor(Material m) {
        super(Material.stringify(m)+"scalearmor", "Scale Armor",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" scale armor.", m.getToughness()+m.getWeight()*1.5,
                Math.max(m.getToughness(), 1), (int)m.getWeight()*10, Size.LARGE, m);
    }

}
