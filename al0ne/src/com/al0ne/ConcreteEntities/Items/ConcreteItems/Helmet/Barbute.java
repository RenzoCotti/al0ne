package com.al0ne.ConcreteEntities.Items.ConcreteItems.Helmet;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Helmet;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Barbute extends Helmet {
    public Barbute(String id, String name, String description,
                   double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 5, Size.SMALL, material);
    }

    public Barbute(String id, String name, String description,
                   double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
    }

    public Barbute(Material m) {
        super(Material.stringify(m)+"barbute", "barbute",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" helmet with a " +
                        " T-shaped opening for the eyes and mouth.", Math.max(m.getWeight()-1, 1),
                max(m.getToughness()-2, 1), (int)m.getWeight(), Size.SMALL, m);
    }

}
