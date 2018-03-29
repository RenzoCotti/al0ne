package com.al0ne.ConcreteEntities.Items.Types.Wearable;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;

/**
 * Created by BMW on 08/05/2017.
 */
public class BodyClothing extends Armor{
    public BodyClothing(String id, String name, String description, double weight, Size s, Material material) {
        super(id, name, description, weight, 0, 0, s, material);
    }

    public BodyClothing(String name, String description, double weight, Material material) {
        super(name, name, description, weight, 0, 0, Size.NORMAL, material);
    }

    public BodyClothing(String name, String description) {
        super(name, name, description, 0.5, 0, 0, Size.NORMAL, Material.FABRIC);
    }
}
