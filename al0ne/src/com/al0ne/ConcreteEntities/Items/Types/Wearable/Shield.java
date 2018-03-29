package com.al0ne.ConcreteEntities.Items.Types.Wearable;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.ConcreteEntities.Items.Types.Protective;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Shield extends Protective{

    public Shield(String id, String name, String description,
                  double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
        this.part="off hand";
    }

    public Shield(String id, String name, String description,
                  double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 10, Size.NORMAL, material);
        this.part="off hand";
    }

    public Shield(Material m) {
        super(Material.stringify(m)+"shield", "Shield",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" shield.", Math.max(m.getWeight()-1, 1),
                max(m.getToughness()-1, 1), 10, Size.NORMAL, m);
        this.part = "off hand";
    }

}
