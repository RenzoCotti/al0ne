package com.al0ne.ConcreteEntities.Items.ConcreteItems.Armor;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Armor;

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
                        +Material.stringify(m)+" chainmail.", m.getToughness()+m.getWeight()*1.2,
                Math.max(m.getToughness(), 1), (int)m.getWeight()*5, Size.LARGE, m);
    }
}
