package com.al0ne.Entities.Items.Behaviours.Wearable;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;

/**
 * Created by BMW on 08/05/2017.
 */
public class BodyClothing extends Armor{
    public BodyClothing(String id, String name, String description, double weight, Size s, Material material) {
        super(id, name, description, weight, 0, s, material);
    }
}
