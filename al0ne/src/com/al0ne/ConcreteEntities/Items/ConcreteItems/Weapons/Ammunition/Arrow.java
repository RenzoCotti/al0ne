package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.Ammunition;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Abstract.Item;

import static java.lang.Math.max;

/**
 * Created by BMW on 22/07/2017.
 */
public class Arrow extends Item{
    public Arrow(String name, String description, double weight, Size size, Material material, Integer price) {
        super("arrow", name, description, weight, size, material, price);
    }

    public Arrow(Material material) {
        super("arrow", "Arrow", "An arrow made of "+Material.stringify(material)+".",
                0.02*material.getWeight(), Size.SMALL, material, null);
    }
}
