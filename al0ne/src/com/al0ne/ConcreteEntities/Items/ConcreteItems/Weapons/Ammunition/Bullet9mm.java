package com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.Ammunition;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Abstract.Item;

/**
 * Created by BMW on 22/07/2017.
 */
public class Bullet9mm extends Item{
    public Bullet9mm(String name, String description, double weight, Size size, Material material, Integer price) {
        super("9mm", name, description, weight, size, material, price);
    }

    public Bullet9mm() {
        super("9mm", "9mm bullet", "A bullet made of brass and lead.",
                0.01, Size.MICRO, Material.BRASS, null);
    }

}
