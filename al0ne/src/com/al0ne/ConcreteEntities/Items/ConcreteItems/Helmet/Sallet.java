package com.al0ne.ConcreteEntities.Items.ConcreteItems.Helmet;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Helmet;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Sallet extends Helmet {
    public Sallet(String id, String name, String description,
                  double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 5, Size.SMALL, material);
    }

    public Sallet(String id, String name, String description,
                  double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
    }

    public Sallet(Material m) {
        super(Material.stringify(m) + "sallet", "Sallet",
                Utility.getArticle(Material.stringify(m)) + " "
                        + Material.stringify(m) + " helmet that protects the upper part of the head.",
                Math.max(m.getWeight() - 2, 0.5), max(m.getToughness() - 2, 1), (int) m.getWeight(), Size.SMALL, m);
    }
}
