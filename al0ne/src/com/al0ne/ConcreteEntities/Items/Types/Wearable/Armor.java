package com.al0ne.ConcreteEntities.Items.Types.Wearable;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.ConcreteEntities.Items.Types.Protective;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class Armor extends Protective {
    public Armor(String name, String description, Material m) {
        super(name, name, description, Math.max(m.getWeight()*2, 3), Math.max(m.getToughness()-2, 0),
                (int) Math.max(m.getWeight()*10, 40), Size.LARGE, m);
        this.part = "body";
    }

    public Armor(String id, String name, String description,
                 double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
        this.part = "body";
    }

    public Armor(Material m) {
        super(Material.stringify(m)+"armor", "Armor",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" armor.", m.getToughness()+m.getWeight(),
                max(m.getToughness(), 1), (int)m.getWeight()*10, Size.LARGE, m);
        this.part = "body";
    }

}
