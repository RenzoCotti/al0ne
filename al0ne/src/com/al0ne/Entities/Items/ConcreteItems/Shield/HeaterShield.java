package com.al0ne.Entities.Items.ConcreteItems.Shield;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Wearable.Shield;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class HeaterShield extends Shield{
    public HeaterShield(String id, String name, String description,
                        double weight, int armor, int encumberment, Material material) {
        super(id, name, description, weight, armor, encumberment, Size.SMALL, material);
    }

    public HeaterShield(Material m) {
        super(Material.stringify(m)+"heater", "Heater Shield",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" shield shaped like an oval, " +
                        "except the top portion of it is flat.", Math.max(m.getWeight()-1.5, 1),
                max(m.getToughness()-1, 1), 10+(int)(m.getWeight()*5), Size.NORMAL, m);
    }
}
