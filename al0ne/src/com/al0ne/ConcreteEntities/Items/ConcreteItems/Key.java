package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.Engine.Physics.Behaviours.KeyBehaviour;

/**
 * Created by BMW on 02/02/2017.
 */
public class Key extends Item{
    public Key(String name, String description, String door) {
        super(name, name, description, 0.1, Size.VSMALL, Material.IRON, null);
        addBehaviour(new KeyBehaviour(door));
    }

    public Key(String name, String door) {
        super(name, name, "A plain looking key.", 0.1, Size.VSMALL, Material.IRON, null);
        addBehaviour(new KeyBehaviour(door));
    }


}
