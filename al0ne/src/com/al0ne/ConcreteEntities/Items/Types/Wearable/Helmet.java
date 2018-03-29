package com.al0ne.ConcreteEntities.Items.Types.Wearable;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.ConcreteEntities.Items.Types.Protective;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Helmet extends Protective {
    public Helmet(String id, String name, String description,
                  double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 5, Size.SMALL, material);
        this.part="head";
    }

    public Helmet(String id, String name, String description,
                  double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
        this.part="head";
    }

    public Helmet(Material m) {
        super(Material.stringify(m)+"helmet", "Helmet",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" helmet.", Math.max(m.getWeight()-2, 0.5),
                max(m.getToughness()-2, 1), (int)m.getWeight(), Size.SMALL, m);
        this.part = "head";
    }
}
