package com.al0ne.ConcreteEntities.Items.Types.Wearable;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;

/**
 * Created by BMW on 08/05/2017.
 */
public class HeadClothing extends Helmet{
    public HeadClothing(String id, String name, String description, double weight, Material material) {
        super(id, name, description, weight, 0, 0, Size.SMALL, material);
    }
}
