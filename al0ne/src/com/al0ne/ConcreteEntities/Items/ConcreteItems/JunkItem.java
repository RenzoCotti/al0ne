package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Abstract.Item;

/**
 * Created by BMW on 30/04/2017.
 */
public class JunkItem extends Item {
    public JunkItem(String name, String description, double weight, Size size) {
        super(name, name, description, weight, size, Material.UNDEFINED, 0);
    }

    public JunkItem(String name, String description, double weight, Material m) {
        super(name, name, description, weight, Size.SMALL, m, 0);
    }

    public JunkItem(String name, String description, double weight, Size size, Material material) {
        super(name, name, description, weight, size, material, 0);
    }
}
