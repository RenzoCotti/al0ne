package com.al0ne.ConcreteEntities.Items.ConcreteItems.Armor;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Armor;

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
}
